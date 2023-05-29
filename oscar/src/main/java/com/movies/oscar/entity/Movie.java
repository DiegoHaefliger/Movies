package com.movies.oscar.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "MOVIE")
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "year_movie", nullable = false)
	private Integer year;

	@Column(name = "title", nullable = false, length = 200)
	private String title;

	@Column(name = "winner")
	private Boolean winner;

	@OneToMany(mappedBy = "movieProducer", fetch = FetchType.LAZY)
	private List<MovieProducer> movieProducer;

	@OneToMany(mappedBy = "movieStudio", fetch = FetchType.LAZY)
	private List<MovieStudio> movieStudio;

	public Movie(Integer year, String title, Boolean winner) {
		this.year = year;
		this.title = title;
		this.winner = winner;
	}

}
