package com.demo;

import java.time.LocalDate;

public class Test {
	public static void main(String[] args) {
		LocalDate startDate = LocalDate.of(2024,01,31);
		System.out.println(startDate.plusMonths(11).minusDays(1));
		System.out.println(startDate.plusMonths(11).plusMonths(1).minusDays(1));
	}

}
