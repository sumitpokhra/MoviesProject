package com.movies.entity;

public class Movies {

	private String Title;
	private String TitleType;
	private String Director;
	private double IMDbRating;
	private double Runtime;
	private int MovieYear;
	private String Genres;
	private int IMDbVotes;
	private String Top250;
	private String MustSee;
	private String URL;

	public Movies(String title, String titleType, String director, double iMDbRating, double runtime, int movieYear,
			String genres, int iMDbVotes, String top250, String mustSee, String uRL) {
		super();
		Title = title;
		TitleType = titleType;
		Director = director;
		IMDbRating = iMDbRating;
		Runtime = runtime;
		MovieYear = movieYear;
		Genres = genres;
		IMDbVotes = iMDbVotes;
		Top250 = top250;
		MustSee = mustSee;
		URL = uRL;
	}

	

	@Override
	public String toString() {
		return "Movies [Title=" + Title + ", TitleType=" + TitleType + ", Director=" + Director + ", IMDbRating="
				+ IMDbRating + ", Runtime=" + Runtime + ", MovieYear=" + MovieYear + ", Genres=" + Genres
				+ ", IMDbVotes=" + IMDbVotes + ", Top250=" + Top250 + ", MustSee=" + MustSee + ", URL=" + URL + "]";
	}



	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getTitleType() {
		return TitleType;
	}

	public void setTitleType(String titleType) {
		TitleType = titleType;
	}

	public String getDirector() {
		return Director;
	}

	public void setDirector(String director) {
		Director = director;
	}

	public double getIMDbRating() {
		return IMDbRating;
	}

	public void setIMDbRating(double iMDbRating) {
		IMDbRating = iMDbRating;
	}

	public double getRuntime() {
		return Runtime;
	}

	public void setRuntime(double runtime) {
		Runtime = runtime;
	}

	public int getMovieYear() {
		return MovieYear;
	}

	public void setMovieYear(int movieYear) {
		MovieYear = movieYear;
	}

	public String getGenres() {
		return Genres;
	}

	public void setGenres(String genres) {
		Genres = genres;
	}

	public int getIMDbVotes() {
		return IMDbVotes;
	}

	public void setIMDbVotes(int iMDbVotes) {
		IMDbVotes = iMDbVotes;
	}

	public String getTop250() {
		return Top250;
	}

	public void setTop250(String top250) {
		Top250 = top250;
	}

	public String getMustSee() {
		return MustSee;
	}

	public void setMustSee(String mustSee) {
		MustSee = mustSee;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

}
