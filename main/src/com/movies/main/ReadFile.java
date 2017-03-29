package com.movies.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.movies.entity.Movies;
import com.movies.utils.DbConnection;
import com.movies.utils.StandardLibrary;

public class ReadFile {

	private static final String FileDelimiter = ";";

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			// Reading the csv file
			System.out.println("*****Reading Data from file....!!*****");
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\file\\movies.txt"));

			// Create List for holding Movie objects
			List<Movies> movieList = new ArrayList<Movies>();

			String record = null;
			int recordCount = 0;
			while ((record = br.readLine()) != null) {
				String[] movieDetails = record.split(FileDelimiter);
				if (movieDetails.length > 0) {
					// Save the movie details in Movie object
					Movies movie = new Movies(movieDetails[0], movieDetails[1], movieDetails[2],
							StandardLibrary.parseDouble(movieDetails[3]), StandardLibrary.parseDouble(movieDetails[4]),
							StandardLibrary.parseInt(movieDetails[5]), movieDetails[6],
							StandardLibrary.parseInt(movieDetails[7]), movieDetails[8], movieDetails[9],
							movieDetails[10]);
					movieList.add(movie);
				}
				recordCount++;
			}

			System.out.println("*****Total Number of Records in File: " + recordCount + " *****");
			System.out.println("*****Creating Connection to Database******");
			Connection connection = null;
			connection = DbConnection.getConnection();
			if (connection != null) {
				System.out.println("*****Connection to Database Successful*****");
				PreparedStatement preparedStatement;
				preparedStatement = connection.prepareStatement(
						"select Title, count(Title) as times from moviedetails group by Title, URL having times>1");
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					System.err.println("-----Records already present in Database-----");
					StandardLibrary.SearchPanel(connection);
				} else {
					System.out.println("*****Inserting Records into Database*****");
					try {
						preparedStatement = connection.prepareStatement(
								"insert into moviedetails (Title, TitleType, Director, IMDbRating, Runtime, MovieYear, Genres, IMDbVotes, Top250, MustSee, URL) values(?,?,?,?,?,?,?,?,?,?,?)");
						for (Movies m : movieList) {
							preparedStatement.setString(1, m.getTitle());
							preparedStatement.setString(2, m.getTitleType());
							preparedStatement.setString(3, m.getDirector());
							preparedStatement.setDouble(4, m.getIMDbRating());
							preparedStatement.setDouble(5, m.getRuntime());
							preparedStatement.setInt(6, m.getMovieYear());
							preparedStatement.setString(7, m.getGenres());
							preparedStatement.setInt(8, m.getIMDbVotes());
							preparedStatement.setString(9, m.getTop250());
							preparedStatement.setString(10, m.getMustSee());
							preparedStatement.setString(11, m.getURL());
							preparedStatement.executeUpdate();
						}
						if (recordCount == movieList.size()) {
							System.out.println(
									"Number of Records inserted into Database Successfully: " + movieList.size());
						} else {
							System.out.println("Number of Records not inserted into Database: "
									+ (recordCount - movieList.size()));
						}
						StandardLibrary.SearchPanel(connection);
					} catch (SQLException e) {
						System.err.println("Insertion of Records into Database Failed");
						e.printStackTrace();
					} finally {
						try {
							connection.close();
						} catch (SQLException e) {
							System.err.println("Error while closing the Database Connection");
							e.printStackTrace();
						}
					}
				}
			} else {
				System.err.println("Database Connection Failed");
			}
		} catch (Exception ee) {
			System.err.println("Error occured while reading records from File");
			ee.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException ie) {
				System.err.println("Error occured while closing the BufferedReader");
				ie.printStackTrace();
			}
		}
	}
}
