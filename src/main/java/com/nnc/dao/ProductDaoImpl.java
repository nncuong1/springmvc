package com.nnc.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.nnc.entity.Product;
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
	@Override
	public List<Product> getNewestProducts() {
		Query<Product> query  =sessionFactory.getCurrentSession().createQuery("select p from product p where p.activeFlag = 1 order by p.createDate DESC",Product.class);
		query.setMaxResults(5);
		List<Product> products = query.getResultList();
		return products;
	}
	
	@Override
	public List<Object[]> getBestSellerProductId() {
		String hql = "select oi.product.id, count(oi.product.id) as rak, o.createDate, sum(oi.quantity) as qty from OrderItem oi" + 
				" join orders o on o.id = oi.order and o.status=2 group by oi.product.id order by o.createDate desc, qty desc, rak desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(8);
		List<Object[]> results = query.getResultList();
		return results;
	}
	@Override
	public List<Product> getBestSellerProduct(List<Integer> ids) {
		List<Product> products = sessionFactory.getCurrentSession().byMultipleIds(Product.class).multiLoad(ids);
		return products;
	}
	@Override
	public List<Product> searchProductWithCriteria(String queryStr, Map<String, Object> mapParams, Paging paging) {
		StringBuilder queryString = new StringBuilder();
		StringBuilder countQuery = new StringBuilder();
		countQuery.append(" select count(*) from product p where p.activeFlag = 1");
		
		queryString.append(" from product p join fetch p.category where p.activeFlag=1");
		if(queryStr!=null && !queryStr.isEmpty()) {
			queryString.append(queryStr);
			countQuery.append(queryStr);
		} 
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		Query countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString());
		if(mapParams!=null && !mapParams.isEmpty()) {
			for(String key : mapParams.keySet()) {
				query.setParameter(key,   mapParams.get(key) );
				countQ.setParameter(key,  mapParams.get(key) );
			}
		}
		if(paging!=null) {
			query.setFirstResult(paging.getOffset()); // bat dau tu dau (0)
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (Long) countQ.uniqueResult();
			paging.setTotalRows(totalRecords);
		}
		return query.getResultList();
	}
}
