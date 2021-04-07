package fr.epita.quizmanager.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import fr.epita.quizmanager.datamodel.*;
import fr.epita.quizmanager.exception.*;
import fr.epita.quizmanager.services.*;
import fr.epita.quizmanager.services.dao.QuestionDAO;



public class TestQuestionDAO {

	private static final String COUNT = "select count(1) from QUESTION where subject='What is a dao'";
	private static final String CREATE_QUERY = "CREATE TABLE QUESTION(ID INT PRIMARY KEY AUTO_INCREMENT , SUBJECT VARCHAR(2000), TOPICS VARCHAR(1000), DIFFICULTY INT)";
	private static final String URL = "db.url";
	private static final String USER = "db.user";
	private static final String PWDB = "db.password";
	static boolean isInited = false;

	private static void init() throws SQLException {
		if (!isInited) {
			String createTableSQL = CREATE_QUERY;
			Configuration conf = Configuration.getInstance();
			Connection connection = DriverManager.getConnection(conf.getConfValue(URL), conf.getConfValue(USER),
					conf.getConfValue(PWDB)); // TODO : externalize
			PreparedStatement pstmt = connection.prepareStatement(createTableSQL);
			pstmt.execute();
			isInited = true;
			pstmt.close();
		}

	}
	private static void dropTable() throws SQLException {
			String createTableSQL = "DROP TABLE QUESTION";
			Connection connection = DriverManager.getConnection("jdbc:h2:~/vivien", "sa", ""); // TODO : externalize
			PreparedStatement pstmt = connection.prepareStatement(createTableSQL);
			pstmt.execute();
			isInited = true;
			pstmt.close();
	}
	public static void main(String[] args) throws SQLException, IOException {

		testCreate();
		
		testPassingSearch();
		testNotPassingSearch();
		dropTable();


	}

	private static void testNotPassingSearch() throws SQLException {
		//given
		Question criteria = new Question();
		criteria.setSubject("Java?");
		
		//when
		QuestionDAO dao = new QuestionDAO();
		List<Question> searchResults = dao.search(criteria);
		
		
		//then
		if (searchResults.size() == 0) {
			System.out.println("search (not passing) : success");
		}else {
			System.out.println("search (not passing): failure");
		}
	}
	private static void testPassingSearch() throws SQLException {
		//given
		Question criteria = new Question();
		criteria.setSubject("What is");
		
		//when
		QuestionDAO dao = new QuestionDAO();
		List<Question> searchResults = dao.search(criteria);
		
		
		//then
		if (searchResults.size() == 1) {
			System.out.println("search : success");
		}else {
			System.out.println("search : failure");
		}
	}

	private static void testCreate() throws SQLException {
		Configuration conf = Configuration.getInstance();

		// given
		init();
		Question question = new Question();
		question.setDifficulty(3);
		question.setSubject("What is a dao");
		question.setTopics(new String[] { "design pattern", "dao" });

		// when
		QuestionDAO dao = new QuestionDAO();
		try {
			dao.createQuestion(question);
		} catch (CreateFailedException cfe) {
			System.out.println("this object was not correctly saved" + cfe.getObj());
		}

		// then
		Connection connection = DriverManager.getConnection(conf.getConfValue("db.url"), conf.getConfValue("db.user"),
				conf.getConfValue("db.password")); // TODO : externalize
		PreparedStatement statement = connection
				.prepareStatement(COUNT);
		ResultSet rs = statement.executeQuery();
		rs.next();
		int matchingQuestionCount = rs.getInt(1);
		System.out.println(matchingQuestionCount);
		if (matchingQuestionCount < 1) {
			System.out.println("error");
		} else {
			System.out.println("success!");
		}
		rs.close();
		statement.close();

	}
}
