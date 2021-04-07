/**
 * 
 */
/**
 * @author vivie
 *
 */
module fr.epita.quizmanager {
	// requires modules
	requires java.sql;
	requires java.base;
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.base;
	// make the classes available for others modules
	exports fr.epita.quizmanager.services.dao;
	exports fr.epita.quizmanager.datamodel;
	exports fr.epita.quizmanager.launcher;

}