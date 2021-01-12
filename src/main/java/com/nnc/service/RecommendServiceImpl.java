package com.nnc.service;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnc.dao.ReviewDao;
import com.nnc.entity.Review;
import com.nnc.util.Constant;
import com.nnc.util.RecommendationModel;

@Service
@Transactional
public class RecommendServiceImpl implements RecommendService {

	@Autowired
	ReviewDao<Review> reviewDao;
	
	private static final Logger log = Logger.getLogger(RecommendServiceImpl.class);

	@Override
	public void updateCsvFile() {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(Constant.FILE_PATH, false);

			// lay du lieu tu database
			List<Object[]> rows = reviewDao.getDataForCsvFile();
			// ghi vao file
			for (Object[] row : rows) {
				fileWriter.append(String.valueOf(row[0]));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(row[1]));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(row[2]));
				fileWriter.append("\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Object[]> getDataForCsvFile() {
		return reviewDao.getDataForCsvFile();
	}

//	@Override
//	public List<Long> userBasedNeighbor(int userId) throws Exception {
//		List<Long> productIds = new ArrayList<Long>();
//		log.info("user id : "+userId);
//		DataModel model = RecommendationModel.getInstance().getDataModel();
	//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
//		UserSimilarity similarity = new UncenteredCosineSimilarity(model);
//		UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
//		long[] neighborhoodId = neighborhood.getUserNeighborhood(userId);
//		log.info("neighborhood : "+neighborhoodId.length);
//		for(int i=0;i<neighborhoodId.length;i++) {
//			log.info("neighborhood id : "+neighborhoodId[i]);
//		}
//		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
//		try {
//			List<RecommendedItem> recommendations = recommender.recommend(userId, 3);
//			if(recommendations !=null && !recommendations.isEmpty()) {
//				log.info("reomendation size : "+recommendations.size());
//				for (RecommendedItem recommendation : recommendations) {
//					productIds.add(recommendation.getItemID());
//				}
//			}else {
//				log.info("reomendation size : "+recommendations.isEmpty());
//				log.info("reomendation size : "+recommendations.size());
//			}
//		} catch (Exception e) {
//			log.error("user-neighbor :  "+e);
//			return null;
//		}
//		return productIds;
//	}

	@Override
	public List<Long> itemBasedRecommendation(int productId) throws Exception {
		List<Long> productIds = new ArrayList<Long>();
		DataModel model = RecommendationModel.getInstance().getDataModel();
		// caculate similarity by cosine 
		ItemSimilarity sim = new UncenteredCosineSimilarity(model);
		// create recommender and compute a prediction
		GenericItemBasedRecommender recomender = new GenericItemBasedRecommender(model, sim);
		try {
			List<RecommendedItem> recommendations = recomender.mostSimilarItems(productId, 3);
			if(recommendations !=null && !recommendations.isEmpty()) {
				for (RecommendedItem recommendation : recommendations) {
					productIds.add(recommendation.getItemID());
				}
			}
		} catch (Exception e) {
			log.error("item-similarity :  "+e);
			return null;
		}
		
		return productIds;
	}
	@Override
	public List<Long> userBasedNeighbor(int userId) throws Exception {
		List<Long> productIds = new ArrayList<Long>();
		log.info("user id : "+userId);
		// create datamodel read data from file
		DataModel model = RecommendationModel.getInstance().getDataModel();
		// caculate similarity by cosine alogrithm
		UserSimilarity similarity = new UncenteredCosineSimilarity(model);
		// select k user that have the hightest similarity
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);	
		
		// log neighborhood information
		long[] neighborhoodId = neighborhood.getUserNeighborhood(userId);
		log.info("neighborhood : "+neighborhoodId.length);
		for(int i=0;i<neighborhoodId.length;i++) {
			log.info("neighborhood id : "+neighborhoodId[i]);
		}
		
		// create recommender and compute a prediction
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		try {
			List<RecommendedItem> recommendations = recommender.recommend(userId, 3);
			if(recommendations !=null && !recommendations.isEmpty()) {
				log.info("reomendation size : "+recommendations.size());
				for (RecommendedItem recommendation : recommendations) {
					productIds.add(recommendation.getItemID());
				}
			}else {
				//log.info("reomendation size : "+recommendations.isEmpty());
				log.info("reomendation size : "+recommendations.size());
			}
		} catch (Exception e) {
			log.error("user-neighbor :  "+e);
			return null;
		}
		return productIds;
	}

}
