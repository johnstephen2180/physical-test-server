package com.loankim.examonline.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class TimeExUtil {

	public static long getNewDateMiliseconds() {
		return DateUtils.truncate(new Date(), Calendar.DATE).getTime();
	}


	public static void main(String[] args) {
		System.out.println(TimeExUtil.getNewDateMiliseconds());
	}
}
