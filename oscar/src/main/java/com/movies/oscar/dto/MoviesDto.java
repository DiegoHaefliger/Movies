package com.movies.oscar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoviesDto {

	private String title;

	private Integer ano;

	private List<IdDescricaoDto> studios;

	private List<IdDescricaoDto> producers;

	private Boolean winner;

}
