package com.nnc.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.ReviewDao;
import com.nnc.entity.Review;
import com.nnc.util.Constant;
import com.nnc.util.RecommendationModel;

@Service
@Transactional
public class RecommendServiceImpl implements RecommendService {

	@Autowired
	ReviewDao<Review> reviewDao;
	
	private static final Logger log = Logger.getLogger(ProductServiceImpl.class);

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

	@Override
	public List<Long> userBasedNeighbor(int userId) throws Exception {
		List<Long> productIds = new ArrayList<Long>();

		DataModel model = RecommendationModel.getInstance().getDataModel();
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		try {
			List<RecommendedItem> recommendations = recommender.recommend(userId, 3);
			for (RecommendedItem recommendation : recommendations) {
				productIds.add(recommendation.getItemID());
			}
		} catch (Exception e) {
			log.error("user-neighbor :  "+e);
			return null;
		}
		
		return productIds;
	}

	@Override
	public List<Long> itemBasedRecommendation(int productId) throws Exception {
		List<Long> productIds = new ArrayList<Long>();
		DataModel model = RecommendationModel.getInstance().getDataModel();
		ItemSimilarity sim = new LogLikelihoodSimilarity(model);
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

}
