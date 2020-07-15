package com.nnc.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.nnc.entity.Review;
import com.nnc.util.Paging;

@Repository
public class ReviewDaoImpl extends BaseDaoImpl<Review> implements ReviewDao<Review> {

	@Override
	public List<Review> findByProductId(Integer id, Paging paging) {
		StringBuilder countQuery = new StringBuilder();
		countQuery.append(" select count(*) from review r where r.activeFlag = 1 and r.product.id = :id");
		String hql = "SELECT r FROM review r JOIN FETCH r.customer c WHERE r.product.id = :id AND r.activeFlag = 1 AND r.status = 1 ORDER BY r.createDate DESC";
		
		Query countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString()).setParameter("id", id);
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id);
		if(paging!=null) {
			query.setFirstResult(paging.getOffset()); // bat dau tu dau (0)
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (Long) countQ.uniqueResult();
			paging.setTotalRows(totalRecords);
		}
		List<Review> reviews = query.getResultList();
		return reviews;
	}

	@Override
	public List<Object[]> getDataForCsvFile() {
		String hql = "select r.customer.id , r.product.id, r.rating from review r left outer join review r2 on (r.customer.id = r2.customer.id and"
				+ " r.product.id =  r2.product.id and r.createDate < r2.createDate) and r.activeFlag = 1 where r2.product.id is null and r2.customer.id is null order by r.customer.id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Object[]> results = query.getResultList();
		return results;
	}

	@Override
	public List<Review> getAllReview(Paging paging) {
		StringBuilder countQuery = new StringBuilder();
		countQuery.append(" select count(*) from review r");
		String hql = "SELECT r FROM review r ORDER BY r.activeFlag ASC , r.createDate DESC";
		
		Query countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString());
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(paging!=null) {
			query.setFirstResult(paging.getOffset()); // bat dau tu dau (0)
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (Long) countQ.uniqueResult();
			paging.setTotalRows(totalRecords);
		}
		List<Review> reviews = query.getResultList();
		return reviews;
	}

	@Override
	public void deleteReview(int id) {
		sessionFactory.getCurrentSession().delete(findById(Review.class, id));
	}

}
