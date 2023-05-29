package com.movies.oscar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwardDto {

	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;

}
