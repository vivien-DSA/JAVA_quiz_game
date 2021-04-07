package fr.epita.quizmanager.launcher.menu;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.epita.quizmanager.datamodel.*;
import fr.epita.quizmanager.exception.*;
import fr.epita.quizmanager.services.*;
import fr.epita.quizmanager.services.dao.QuestionDAO;

/**
 * @author vivie
 *
 */
public class TeacherMenu {

	private static Scanner scanner = new Scanner(System.in);
	private static final String COUNT = "select count(ID) from QUESTION";
	private static final String CREATE_QUERY = "CREATE TABLE QUESTION(ID INT PRIMARY KEY AUTO_INCREMENT , SUBJECT VARCHAR(2000), TOPICS VARCHAR(1000), DIFFICULTY INT)";
	private static final String URL = "db.url";
	private static final String USER = "db.user";
	private static final String PWDB = "db.password";
	static boolean isInited = false;
	private static String[] arrayOfTopics = {};

	private TeacherMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static void init() throws SQLException {
		
		if (!isInited) {
			String createTableSQL = CREATE_QUERY;
			Configuration conf = Configuration.getInstance();
			Connection connection = DriverManager.getConnection(conf.getConfValue(URL), conf.getConfValue(USER),
					conf.getConfValue(PWDB)); // TODO : externalize
			try {
				PreparedStatement pstmt = connection.prepareStatement(createTableSQL);
				try {
					pstmt.execute();
				} finally {
					pstmt.close();
				}
			} finally {
				connection.close();
			}
			connection.close();
			isInited = true;

		}

	}

	public static void dropTable() throws SQLException {
		String createTableSQL = "DROP TABLE QUESTION";
		Configuration conf = Configuration.getInstance();
		Connection connection = DriverManager.getConnection(conf.getConfValue(URL), conf.getConfValue(USER),
				conf.getConfValue(PWDB)); // TODO : externalize
		try {
			PreparedStatement pstmt = connection.prepareStatement(createTableSQL);
			try {
				pstmt.execute();
			} finally {
				pstmt.close();
			}
		} finally {
			connection.close();
		}
		connection.close();
	}

	// Function to add x in arr
	/**
	 * @param n
	 * @param arr
	 * @param x
	 * @return
	 */
	public static String[] addX(int n, String arr[], String x) {
		int i;

		// create a new ArrayList
		List<String> arrlist = new ArrayList<String>(Arrays.asList(arr));

		// Add the new element
		arrlist.add(x);

		// Convert the Arraylist to array
		arr = arrlist.toArray(arr);

		// return the array
		return arr;
	}

	private static void testNotPassingSearch() throws SQLException {
		// given
		Question criteria = new Question();
		criteria.setSubject("Java?");

		// when
		QuestionDAO dao = new QuestionDAO();
		List<Question> searchResults = dao.search(criteria);

		// then
		if (searchResults.size() == 0) {
			System.out.println("search (not passing) : success");
		} else {
			System.out.println("search (not passing): failure");
		}
	}

	private static void testPassingSearch() throws SQLException {
		// given
		Question criteria = new Question();
		criteria.setSubject("What is");

		// when
		QuestionDAO dao = new QuestionDAO();
		List<Question> searchResults = dao.search(criteria);

		// then
		if (searchResults.size() == 1) {
			System.out.println("search : success");
		} else {
			System.out.println("search : failure");
		}
	}

	public static void createQuestion() throws SQLException {
		Configuration conf = Configuration.getInstance();

		// given
		init();
		Question question = new Question();
		System.out.println("Set the Difficulty");
		boolean out = true;
		while (out) {
			try {
				question.setDifficulty(Integer.parseInt(scanner.nextLine()));
				out = false;
			} catch (NumberFormatException e) {
				System.out.println("Difficulty is an integer please correct it");
			}
		}

		System.out.println("What is the subject");
		question.setSubject(scanner.nextLine());
		System.out.println("What is (are) the topic(s)");
		System.out.println("how many topics for this subject");
		int nberOfTopics = 0;
		out = true;
		while (out) {
			try {
				nberOfTopics = Integer.parseInt(scanner.nextLine());
				out = false;
			} catch (NumberFormatException e) {
//			    e.printStackTrace();
				System.out.println("Number of topic is an integer please correct it");
			}
		}
		// call the method to add x in arr
		for (int i = 0; i < nberOfTopics; i++) {
			arrayOfTopics = addX(i, arrayOfTopics, scanner.nextLine());
		}

		question.setTopics(arrayOfTopics);
		// when
		QuestionDAO dao = new QuestionDAO();
		try {
			dao.createQuestion(question);
		} catch (CreateFailedException cfe) {
			System.out.println("this object was not correctly saved" + cfe.getObj());
		}

		// then
		Connection connection = DriverManager.getConnection(conf.getConfValue(URL), conf.getConfValue(USER),
				conf.getConfValue(PWDB));
		try {
			PreparedStatement statement = connection.prepareStatement(COUNT);
			try {
				ResultSet rs = statement.executeQuery();
				rs.next();
				int matchingQuestionCount = rs.getInt(1);
				System.out.println(matchingQuestionCount);
				if (matchingQuestionCount < 1) {
					System.out.println("error");
				} else {
					System.out.println("success!");
				}
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
		connection.close();

	}
}
