package com.twitter.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RelativeTimeConvertor {

	public static String formatToMonthYear(String timeStamp) {

		LocalDateTime dateTime = LocalDateTime.parse(timeStamp,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
		
		

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

		String formattedDate = dateTime.format(outputFormatter);

		return formattedDate;
	}
	public static String formatToDateMonthYear(String timeStamp) {

		LocalDateTime dateTime = LocalDateTime.parse(timeStamp,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

		String formattedDate = dateTime.format(outputFormatter);

		return formattedDate;
	}

	public static String relativeTime(String timeStamp) {
		LocalDateTime yourLocalDateTime = LocalDateTime.parse(timeStamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

		ZonedDateTime yourZonedDateTime = yourLocalDateTime.atZone(ZoneId.of("Asia/Kolkata"));

		long secondsDifference = ChronoUnit.SECONDS.between(yourZonedDateTime, currentDateTime);

		String relativeTime = getRelativeTime(secondsDifference);
		return relativeTime;
	}

	private static String getRelativeTime(long secondsDifference) {

		long absSeconds = Math.abs(secondsDifference);
		if (absSeconds < 60) {
			return absSeconds + " seconds ago";
		}

		long minutes = absSeconds / 60;
		if (minutes < 60) {
			return minutes + " minutes ago";
		}

		long hours = minutes / 60;
		if (hours < 24) {
			return hours + " hours ago";
		}

		long days = hours / 24;
		if (days < 7) {
			return days + " days ago";
		}

		long weeks = days / 7;
		if (weeks < 4) {
			return weeks + " weeks ago";
		}

		long months = weeks / 4;
		if (months < 12) {
			return months + " months ago";
		}

		long years = months / 12;
		return years + " years ago";
	}
}
