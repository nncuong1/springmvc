package com.nnc.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nnc.dao.AuthorDao;
import com.nnc.dao.ProductDao;
import com.nnc.entity.Author;
import com.nnc.entity.Product;
import com.nnc.util.Paging;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao<Product> productDao;

	@Autowired
	private AuthorDao<Author> authorDao;
	
	@Autowired
	private RecommendService recommendService;

	private static final Logger log = Logger.getLogger(ProductServiceImpl.class);

	public void saveProduct(Product product) {
		if (product.getAuthorId() != null && !product.getAuthorId().isEmpty()) {
			for (String authorId : product.getAuthorId()) {
				log.info("Author id : "+authorId);	
//				Author a = new Author();
//				a.setId(Integer.parseInt(authorId));
//				product.addAuthor(a);
				product.addAuthor(authorDao.findById(Author.class,Integer.parseInt(authorId)));
			}
		}
		product.setActiveFlag(1);
		product.setCreateDate(new Date());
		product.setUpdateDate(new Date());
		if (product.getDescription() == null || product.getDescription().isEmpty()) {
			product.setDescription(null);
		}
		if (!product.getMultipartFile().getOriginalFilename().isEmpty()) {
			String fileName = System.currentTimeMillis() + "_" + product.getMultipartFile().getOriginalFilename();
			try {
				processUploadFile(product.getMultipartFile(), fileName);

			} catch (IllegalStateException e) {
				e.printStackTrace();
				log.error("Error insert product : " + e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Error insert product : " + e);
			}
			product.setImgUrl(fileName);
		}
		productDao.save(product);
	}

	public void updateProduct(Product product) {

		if (!product.getMultipartFile().getOriginalFilename().isEmpty()) {
			try {
				String fileName = System.currentTimeMillis() + "_" + product.getMultipartFile().getOriginalFilename();
				processUploadFile(product.getMultipartFile(), fileName);
				product.setImgUrl(fileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (product.getAuthorId() != null && !product.getAuthorId().isEmpty()) {
			Product oldProduct = findById(product.getId());
			log.info("Old id author : "+oldProduct.getAuthorId());
			if(product.getAuthorId().containsAll(oldProduct.getAuthorId()) && !oldProduct.getAuthorId().isEmpty()) {
				log.info("true or false : "+product.getAuthorId().containsAll(oldProduct.getAuthorId()));
				product.setAuthors(oldProduct.getAuthors());
				if(product.getAuthorId().size()>oldProduct.getAuthorId().size()) {
					List<String> newId = new ArrayList<>(CollectionUtils.subtract(product.getAuthorId(), oldProduct.getAuthorId()));
					for(String id : newId) {
						product.addAuthor(authorDao.findById(Author.class, Integer.parseInt(id)));
					}
				}
			}else {
				for (String authorId : product.getAuthorId()) {
					product.addAuthor(authorDao.findById(Author.class, Integer.parseInt(authorId)));
				}
			}
		}
		product.setUpdateDate(new Date());
		log.info(product.getAuthorId());
		log.info(product.getAuthors());
		productDao.update(product);
	}

	public void deleteProduct(Product product) {
		product.setActiveFlag(0);
		product.setUpdateDate(new Date());
		productDao.update(product);
	}

	public List<Product> findProduct(String property, Object value) {
		return productDao.findByProperty(property, value);
	}

	public List<Product> getAllProduct(String keyword, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (keyword != null) {
			queryStr.append(" and model.title like :title or model.description like :description");
			mapParams.put("title", "%"+keyword+"%");
			mapParams.put("description", "%"+keyword+"%");
		}

		return productDao.findAll(queryStr.toString(), mapParams, paging);
	}

	public Product findById(int id) {
		List<Integer> authorId = authorDao.getIdAuthorByBook(id);
		Product product = productDao.findById(Product.class, id);
		
		if (authorId != null && !authorId.isEmpty()) {
			for (Integer a : authorId) {
				product.getAuthorId().add(String.valueOf(a));
			}
		}
		return product;
	}

	@Override
	public List<Product> findProductByProperty(String property, Object value) {
		return productDao.findByProperty(property, value);
	}

	private void processUploadFile(MultipartFile multipartFile, String fileName)
			throws IllegalStateException, IOException {
		String path = "G:\\data\\uploadInventory\\product\\";
		if (!multipartFile.getOriginalFilename().isEmpty()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
				log.info("create dir : " + path);
			}
			File file = new File(path, fileName);
			multipartFile.transferTo(file);
		}
	}

	@Override
	public List<Product> findBookByAuthorId(int id) {
		return productDao.findBookByAuthorId(id);
	}

	@Override
	public List<Product> findBookByCategoryId(int id,Paging paging) {
		return productDao.findBookByCategoryId(id,paging);
	}
	
	@Override
	public Product findBookDetailById(int id) {
		return productDao.findBookDetailById(id);
	}

	@Override
	public List<Product> getRecommendedProducts(List<Long> multiId) {
		return productDao.getRecommendedProducts(multiId);
	}

	@Override
	public List<Product> getRelatedProduct(int productId, int categoryId) {
		return productDao.getRelatedProduct(productId,categoryId);
	}

	@Override
	public List<Product> getRecommendedProducts(int productId, int categoryId) throws Exception {
		List<Long> multId = recommendService.itemBasedRecommendation(productId);
		List<Product> recommendedProducts = null;
		if (multId != null && !multId.isEmpty() && multId.size() >=2) {
			log.info("item-based product ");
			recommendedProducts = productDao.getRecommendedProducts(multId);
			for (Product p : recommendedProducts) {
				log.info("recomendation id : " + p.getId() + ", name : " + p.getTitle());
			}
		} else {
			log.info("related product ");
			recommendedProducts = productDao.getRelatedProduct(productId,categoryId);
		}
		return recommendedProducts;
	}

	@Override
	public List<Product> getRecommendedProducts(Integer userId, int productId, int categoryId) throws Exception {
		List<Long> multId = null;
		List<Product> recommendedProduct = null;
		if(userId!=null) {
			multId = recommendService.userBasedNeighbor(userId);
			if(multId.size()<2 && multId!=null && !multId.isEmpty()) {
				log.info("user-based product ");
				recommendedProduct = productDao.getRecommendedProducts(multId);
			}else {
				recommendedProduct = getRecommendedProducts(productId,  categoryId);
			}
		}else {
			recommendedProduct = getRecommendedProducts(productId,  categoryId);
		}
		return recommendedProduct;
	}
	
}

