package com.movies.oscar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AwardsIntervalDto {

	private List<AwardDto> min;
	private List<AwardDto> max;

}
