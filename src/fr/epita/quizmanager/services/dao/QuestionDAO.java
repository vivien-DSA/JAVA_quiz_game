package fr.epita.quizmanager.services.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.exception.CreateFailedException;
import fr.epita.quizmanager.exception.DeleteFailedException;
import fr.epita.quizmanager.exception.UpdatedFailedException;
import fr.epita.quizmanager.services.Configuration;

public class QuestionDAO {

	private static final String INSERT_QUERY = "INSERT INTO QUESTION(SUBJECT, TOPICS, DIFFICULTY) VALUES(?, ?, ?);";
	
	private static final String SEARCH_QUERY = "select SUBJECT, TOPICS, DIFFICULTY from QUESTION where subject LIKE ?";
	
	private static final String UPDATE_QUERY = "UPDATE QUESTION SET SUBJECT = ?, TOPICS = ?, DIFFICULTY = ? WHERE ID = 2" ;
	
	private static final String DELETE_QUERY = "DELETE FROM QUESTION WHERE SUBJECT = ? OR TOPICS = ? OR DIFFICULTY = ?" ;
	
	// CRUD : CREATE, READ, UPDATE, DELETE
	public void createQuestion(Question question) throws CreateFailedException {
		Configuration conf = Configuration.getInstance();
		try {
			Connection connection = getConnection(conf);
			PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);
			pstmt.setString(1, question.getSubject());
			String arrayAsString = String.valueOf(Arrays.asList(question.getTopics())); //add comma between each part of the array
			pstmt.setString(2, arrayAsString);
			pstmt.setInt(3, question.getDifficulty());
			pstmt.execute();
			connection.close();
		} catch (SQLException e) {
			CreateFailedException createFailedException = new CreateFailedException();
			createFailedException.initCause(e);
			createFailedException.setObj(question);
			throw createFailedException;
		}

	}

	public List<Question> search(Question question) throws SQLException {
		Configuration conf = Configuration.getInstance();
		List<Question> questionResult = new ArrayList<>();
		Connection connection = getConnection(conf);
		PreparedStatement statement = connection.prepareStatement(SEARCH_QUERY);
		statement.setString(1, question.getSubject() + "%");
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			String subject = rs.getString(1);
			String topics = rs.getString(2);
			Integer difficulty = rs.getInt(3);
			Question currentQuestion = new Question();
			currentQuestion.setSubject(subject);
			currentQuestion.setTopics(topics.split(","));
			currentQuestion.setDifficulty(difficulty);
			questionResult.add(currentQuestion);
		}
		
		connection.close();
		return questionResult;
	}

	private Connection getConnection(Configuration conf) throws SQLException {
		Connection connection = DriverManager.getConnection(conf.getConfValue("db.url"), conf.getConfValue("db.user"),
				conf.getConfValue("db.password"));
		return connection;
	}

	public void update(Question question) throws SQLException , UpdatedFailedException{
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		try {
		
		PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);// TODO ID DYNAMIC
			pstmt.setString(1, question.getSubject());
			String arrayAsString = String.valueOf(Arrays.asList(question.getTopics())); //add comma between each part of the array
			pstmt.setString(2, arrayAsString);
			pstmt.setInt(3, question.getDifficulty());
			pstmt.execute();
			connection.close();
		} catch (SQLException e) {
			UpdatedFailedException updatedFailedException = new UpdatedFailedException();
			updatedFailedException.initCause(e);
			updatedFailedException.setObj(question);
			throw updatedFailedException;
		}
		connection.close();
	}

	public void delete(Question question) throws SQLException, DeleteFailedException {
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		try {
			PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);

				pstmt.setString(1, question.getSubject());
				String arrayAsString = String.valueOf(Arrays.asList(question.getTopics())); //add comma between each part of the array
				pstmt.setString(2, arrayAsString);
				pstmt.setInt(3, question.getDifficulty());
				pstmt.execute();
		} catch (Exception e) {
			DeleteFailedException deleteFailedException = new DeleteFailedException();
			deleteFailedException.initCause(e);
			deleteFailedException.setObj(question);
			throw deleteFailedException;
		} 
	}

}
