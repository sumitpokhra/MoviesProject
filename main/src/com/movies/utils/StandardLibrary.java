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
				return 0; // or some value to mark this field is wrong. or make
							// a function validates field first ...
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
			try {
				sc = new Scanner(System.in);
				boolean flag = true;
				while (flag) {
					System.out.println("1. Find Director Name");
					System.out.println("2. List down the Movies name based on MovieType");
					System.out.println("3. Find Movie Name");
					System.out.println("4. List down the Movies name based on Genres");
					System.out.println("5. Find Top Ten Movies ");
					System.out.println("6. List down the Movies based on highest Runtime");
					System.out.println("*****Select from the above option*****");
					String option = sc.nextLine();
					switch (option) {

					case "1":
						System.out.println("Enter Movie Name to find the Director Name ?");
						String MovieName = sc.nextLine();
						
						preparedStatement = connection
								.prepareStatement("SELECT director,Title from moviedetails where title=?");
						preparedStatement.setString(1, MovieName);
						ResultSet resultSet = preparedStatement.executeQuery();
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