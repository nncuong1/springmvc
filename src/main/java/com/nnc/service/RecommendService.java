package com.nnc.service;

import java.util.List;

public interface RecommendService {
	public void updateCsvFile();
	public List<Object[]> getDataForCsvFile();
	public List<Long> userBasedNeighbor(int userId) throws Exception;
	public List<Long> itemBasedRecommendation(int productId) throws Exception;
}
