package com.nnc.util;

import java.util.HashMap;
import java.util.Map;

public class MapStatus {
	
	private static MapStatus instance = null;
	private Map<String, String> mapStatus = null;
	
	private MapStatus () {
		mapStatus = new HashMap<String, String>();
	}
	public static MapStatus getInstance() {
		if(instance==null) {
			synchronized (MapStatus.class) {
				instance = new MapStatus();
			}
		}
		return instance;
	}
	public Map<String, String> getMapStatus() {
		mapStatus.put("0", "Tất cả đơn hàng");
		mapStatus.put("1", "Chưa xử lý");
		mapStatus.put("2", "Xác nhận");
		mapStatus.put("3", "Hoàn thành");
		mapStatus.put("4", "Bị hủy");
		return mapStatus;
	}
}
