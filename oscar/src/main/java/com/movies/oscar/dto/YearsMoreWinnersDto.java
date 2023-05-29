package com.movies.oscar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class YearsMoreWinnersDto {
	private int year_movie;
	private long wins;

	public YearsMoreWinnersDto(int year_movie, long wins) {
		this.year_movie = year_movie;
		this.wins = wins;
	}

}
