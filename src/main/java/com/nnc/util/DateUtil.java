package com.nnc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String formatDate(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateString = sdf.format(date);
		// return sdf.format(date);
		//return sdf.parse(dateString);
		return dateString;
	}
	
	public static Date formatDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.parse(date);
	}

	public static String getDaysAgo(Date date) {
		double seconds = Math.floor((new Date().getTime() - date.getTime()) / 1000);

		double interval = Math.floor(seconds / 31536000);

		if (interval >= 1) {
			return (int) interval + " năm trước";
		}
		interval = Math.floor(seconds / 2592000);
		if (interval >= 1) {
			return (int) interval + " tháng trước";
		}
		interval = Math.floor(seconds / 86400);
		if (interval >= 1) {
			return (int) interval + " ngày trước";
		}
		interval = Math.floor(seconds / 3600);
		if (interval >= 1) {
			return (int) interval + " giờ trước";
		}
		interval = Math.floor(seconds / 60);
		if (interval >= 1) {
			return (int) interval + " phút trước";
		}
		return (int) Math.floor(seconds) + " giây trước";
	}

//	public static String timeAgo(Date time_ago) {
//		long cur_time = (Calendar.getInstance().getTimeInMillis()) / 1000;
//		long time_elapsed = cur_time - time_ago.getTime();
//		long seconds = time_elapsed;
//		int minutes = Math.round(time_elapsed / 60);
//		int hours = Math.round(time_elapsed / 3600);
//		int days = Math.round(time_elapsed / 86400);
//		int weeks = Math.round(time_elapsed / 604800);
//		int months = Math.round(time_elapsed / 2600640);
//		int years = Math.round(time_elapsed / 31207680);
//
//		// Seconds
//		if (seconds <= 60) {
//			return "just now";
//		}
//		// Minutes
//		else if (minutes <= 60) {
//			if (minutes == 1) {
//				return "one minute ago";
//			} else {
//				return minutes + " minutes ago";
//			}
//		}
//		// Hours
//		else if (hours <= 24) {
//			if (hours == 1) {
//				return "an hour ago";
//			} else {
//				return hours + " hrs ago";
//			}
//		}
//		// Days
//		else if (days <= 7) {
//			if (days == 1) {
//				return "yesterday";
//			} else {
//				return days + " days ago";
//			}
//		}
//		// Weeks
//		else if (weeks <= 4.3) {
//			if (weeks == 1) {
//				return "a week ago";
//			} else {
//				return weeks + " weeks ago";
//			}
//		}
//		// Months
//		else if (months <= 12) {
//			if (months == 1) {
//				return "a month ago";
//			} else {
//				return months + " months ago";
//			}
//		}
//		// Years
//		else {
//			if (years == 1) {
//				return "one year ago";
//			} else {
//				return years + " years ago";
//			}
//		}
//	}
}
//long days = (new Date().getTime() - date.getTime()) / 86400000;
//
// if(days == 0) return "Today";
// else if(days == 1) return "Yesterday";
// else return days + " days ago";
