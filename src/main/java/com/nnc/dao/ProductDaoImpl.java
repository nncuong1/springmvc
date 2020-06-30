package com.nnc.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.nnc.entity.Product;
import com.nnc.entity.User;
import com.nnc.util.Paging;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao<Product> {
	
	public List<Product> findBookByAuthorId(int id) {
		List<Product> products =sessionFactory.getCurrentSession().
				createQuery(" from product p join fetch p.authors a where a.id = :id and p.activeFlag = 1",Product.class).setParameter("id", id).getResultList();
		return products;
	}
	public List<Product> findBookByCategoryId(int id, Paging paging) {
		Query<Product> query  =sessionFactory.getCurrentSession().createQuery("select p from product p inner join p.category c where c.id = :id and p.activeFlag = 1",Product.class).setParameter("id", id);
//		List<Product> products =sessionFactory.getCurrentSession().
//				createQuery(" select p from product p inner join p.category c where c.id = :id",Product.class).setParameter("id", id).getResultList();
		Long count =(Long) sessionFactory.getCurrentSession().createQuery(" select count(*) from product p inner join p.category c where c.id = :id and p.activeFlag = 1").setParameter("id", id).uniqueResult();
		if(paging!=null) {
			query.setFirstResult(paging.getOffset()); // bat dau tu dau (0)
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = count;
			paging.setTotalRows(totalRecords);
		}
		List<Product> products = query.getResultList();
		return products;
	}
	@Override
	public Product findBookDetailById(int id) {
		Product product =sessionFactory.getCurrentSession().
				createQuery(" from product p join fetch p.authors a where p.id = :id and p.activeFlag = 1",Product.class).setParameter("id", id).getSingleResult();
		return product;
	}
	
	@Override
	public List<Product> getRecommendedProducts(List<Long> multiId){
		List<Integer> ids = multiId.stream().map(Long::intValue).collect(Collectors.toList());
		List<Product> products = sessionFactory.getCurrentSession().byMultipleIds(Product.class).multiLoad(ids);
		return products;
	}
	@Override
	public List<Product> getRelatedProduct(int productId, int categoryId) {
		Query<Product> query  =sessionFactory.getCurrentSession().createQuery("select p from product p where p.category.id = :categoryId and p.id != :id and p.activeFlag = 1 order by p.createDate DESC",Product.class)
				.setParameter("categoryId", categoryId).setParameter("id", productId);
		query.setMaxResults(3);
		List<Product> products = query.getResultList();
		return products;
	}
	@Override
	public List<Product> getProductForReviews() {
		List<Product> products =sessionFactory.getCurrentSession().
				createQuery("select distinct p from review r join product p on r.product = p.id",Product.class).getResultList();	
		return products;
	}
}
