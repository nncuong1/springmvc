package com.nnc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ResourceUtils;

public class CsvFile {

	public static void main(String[] args) {
		String filePath = "classpath:file/test.csv";
		System.out.println("starting read user.csv file");
		readCsv(filePath);
	}

	public static void readCsv(String filePath) {
		BufferedReader reader = null;

		try {
			List<Csv> csvs = new ArrayList<Csv>();
			String line = "";
			reader = new BufferedReader(new FileReader(ResourceUtils.getFile(filePath)));
			reader.readLine();

			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");

				if (fields.length > 0) {
					Csv csv = new Csv();
					csv.setId(Integer.parseInt(fields[0]));
					csv.setFirstName(fields[1]);
					csv.setLastName(fields[2]);
					csvs.add(csv);
				}
			}

			for (Csv u : csvs) {
				System.out.printf("[csvId=%d, firstName=%s, lastName=%s]\n", u.getId(), u.getFirstName(),
						u.getLastName());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
