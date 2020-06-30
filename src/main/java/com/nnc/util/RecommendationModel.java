package com.nnc.util;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class RecommendationModel {
	
	private static RecommendationModel instance = null;
	private DataModel dataModel = null;
	
	private RecommendationModel () {
		try {
			dataModel = new FileDataModel(new File(Constant.FILE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static RecommendationModel getInstance() {
		if(instance==null) {
			synchronized (RecommendationModel.class) {
				instance = new RecommendationModel();
			}
		}
		return instance;
	}
	
	public DataModel getDataModel() {
		return dataModel;
	}
}
