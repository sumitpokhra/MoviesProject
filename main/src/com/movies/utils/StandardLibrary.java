package com.movies.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StandardLibrary {
	public static double parseDouble(String strNumber) {
		if (strNumber != null && strNumber.length() > 0) {
			try {
				return Double.parseDouble(strNumber);
			} catch (Exception e) {
				return 0; 
			}
		} else
			return 0;
	}

	public static int parseInt(String strNumber) {
		if (strNumber != null && strNumber.length() > 0) {
			try {
				return Integer.parseInt(strNumber);
			} catch (Exception e) {
				return 0; // or some value to mark this field is wrong. or make
							// a function validates field first ...
			}
		} else
			return 0;
	}

	public static void SearchPanel(Connection connection) {
		if (connection != null) {
			Scanner sc = null;
			PreparedStatement preparedStatement;
			ResultSet resultSet;
			try {
				sc = new Scanner(System.in);
				boolean flag = true;
				while (flag) {
					System.out.println("1. Find Director Name");
					System.out.println("2. List down the Movies name based on MovieType");
					System.out.println("3. List down the Documentary Movies name released in Year 2000");
					System.out.println("4. List down the Movies name based on Genres");
					System.out.println("5. List down the Movies name based on Director Name");
					System.out.println("6. Find Movie Year in which maximum number of Movies were released");
					System.out.println(
							"7. List down top 10 Movies with Genre='Romance' based on highest number of Votes on IMDb");
					System.out.println(
							"8. Find Movie with Genre 'Thriller' having highest Runtime and released after 2005");
					System.out.println("9. Find Average IMDb Rating for Movies");
					System.out.println("*****Select from the above option*****");
					String option = sc.nextLine();
					switch (option) {

					case "1":
						System.out.println("Enter Movie Name to find the Director Name ?");
						String MovieName = sc.nextLine();

						preparedStatement = connection
								.prepareStatement("SELECT director,Title from moviedetails where title=?");
						preparedStatement.setString(1, MovieName);
						resultSet = preparedStatement.executeQuery();
						int count = 0;
						while (resultSet.next()) {
							String TitleName = resultSet.getString("Title");
							String DirectorName = resultSet.getString("Director");
							System.out.println(DirectorName + " : is the Director of Movie: " + TitleName);
							count++;
						}
						if (count < 1) {
							System.err.println(
									"NO Movies found with name: " + MovieName + " Please enter appropriate Movie Name");
						}
						break;

					case "2":
						System.out.println("Enter Movie Type ?");
						String MovieType = sc.nextLine();

						preparedStatement = connection
								.prepareStatement("SELECT Title from moviedetails where TitleType=?");
						preparedStatement.setString(1, MovieType);
						resultSet = preparedStatement.executeQuery();
						int countTitle = 0;
						while (resultSet.next()) {
							countTitle++;
							System.out.println(countTitle + " Movie Name:  " + resultSet.getString("Title"));
						}
						if (countTitle < 1) {
							System.err.println(
									"NO Movies found of Type: " + MovieType + " Please enter appropriate Movie Type");
						}
						break;

					case "3":
						preparedStatement = connection.prepareStatement(
								"SELECT Title,TitleType,MovieYear from moviedetails where TitleType=? and MovieYear=?");
						preparedStatement.setString(1, "Documentary");
						preparedStatement.setString(2, "2000");
						resultSet = preparedStatement.executeQuery();
						int countDocumentary = 0;
						System.out.println("S.No. \t MovieName");
						while (resultSet.next()) {
							countDocumentary++;
							System.out.println(countDocumentary + "\t" + resultSet.getString("Title"));
						}
						if (countDocumentary < 1) {
							System.err.println("NO Movies found of Type: Documentary released in Year 2000");
						}
						break;

					case "4":
						System.out.println("Enter Movie Genre ?");
						String Genre = sc.nextLine();
						preparedStatement = connection
								.prepareStatement("SELECT Title from moviedetails where Genres like '%"+Genre+"%'");
						resultSet = preparedStatement.executeQuery();
						int countGenre = 0;
						while (resultSet.next()) {
							countGenre++;
							System.out.println(countGenre + " Movie Name:  " + resultSet.getString("Title"));
						}
						if (countGenre < 1) {
							System.err
									.println("NO Movies found of : " + Genre + " Please enter appropriate Movie Genre");
						}
						break;

					case "5":
						System.out.println("Enter Director Name ?");
						String Director = sc.nextLine();
						preparedStatement = connection
								.prepareStatement("SELECT Title from moviedetails where Director=?");
						preparedStatement.setString(1, Director);
						resultSet = preparedStatement.executeQuery();
						int countDirector = 0;
						while (resultSet.next()) {
							countDirector++;
							System.out.println(countDirector + " Movie Name:  " + resultSet.getString("Title"));
						}
						if (countDirector < 1) {
							System.err.println("NO Movies found Directed by : " + Director
									+ " Please enter appropriate Director Name");
						}
						break;

					case "6":
						preparedStatement = connection.prepareStatement(
								"select movieyear,count(*) as No from moviedetails group by movieyear order by count(*) desc limit 1");
						resultSet = preparedStatement.executeQuery();
						int countMovieYear = 0;
						System.out.println("MovieYear \t Number of Movie released");
						while (resultSet.next()) {
							countMovieYear++;
							System.out
									.println(resultSet.getString("movieyear") + "\t  \t " + resultSet.getString("No"));
						}
						if (countMovieYear < 1) {
							System.err.println("Unexpected Error: Please try again");
						}
						break;

					case "7":
						preparedStatement = connection.prepareStatement(
								"SELECT title,genres,imdbvotes from moviedetails where genres like '%romance%' order by imdbvotes desc limit 10");
						resultSet = preparedStatement.executeQuery();
						int countImdbVotes = 0;
						while (resultSet.next()) {
							countImdbVotes++;
							System.out.print(countImdbVotes + " " + resultSet.getString("title") + " ");
							System.out.println("'" + resultSet.getString("imdbvotes") + "'");
						}
						if (countImdbVotes < 1) {
							System.err.println("NO Movies found of Genre 'Romance having highest Number of IMDbVotes'");
						}
						break;

					case "8":
						preparedStatement = connection.prepareStatement(
								"select title,genres,runtime,movieyear from moviedetails where genres like '%thriller%' and movieyear > 2005 order by runtime desc limit 1");
						resultSet = preparedStatement.executeQuery();
						int countRuntime = 0;
						while (resultSet.next()) {
							countRuntime++;
							System.out.println("MovieName: '" + resultSet.getString("title")
									+ "' released after 2005 having highest Runtime: '" + resultSet.getString("Runtime")
									+ "'");
						}
						if (countRuntime < 1) {
							System.err.println("NO Movies found of Genre 'Thriller' having highest Runtime");
						}
						break;

					case "9":
						preparedStatement = connection.prepareStatement(
								"select avg(nullif(Imdbrating, 0)) as AvgIMDbRating from moviedetails");
						resultSet = preparedStatement.executeQuery();
						int countAvgRating = 0;
						while (resultSet.next()) {
							countAvgRating++;
							System.out.println("Average IMDb Rating for Movies is: '"
									+ resultSet.getString("AvgIMDbRating") + "'");
						}
						if (countAvgRating < 1) {
							System.err.println("Unexpected Error: Please try again");
						}
						break;

					default:
						System.err.println("Please Select appropriate option from above list!!!!!");
					}
					System.out.println("Want to exit? (type Yes/No)\n");
					if (sc.nextLine().equalsIgnoreCase("Yes")) {
						flag = false;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("****Exited Successfully*****");
		} else {
			System.err.println("Database Connection Failed");
		}
	}
}