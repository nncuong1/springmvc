package com.nnc.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
	private int id;
	private String title;
	private BigDecimal price;
	private String imagePath;
	private List<OptionDTO> options;
	
}
