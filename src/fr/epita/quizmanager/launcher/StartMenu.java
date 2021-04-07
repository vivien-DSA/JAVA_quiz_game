package fr.epita.quizmanager.launcher;

import java.sql.SQLException;
import java.util.Scanner;

import fr.epita.quizmanager.launcher.menu.TeacherMenu;


public class StartMenu {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String userchoice = "";
//		try {
//			TeacherMenu.dropTable();
//		} catch (SQLException e1) {
//			System.out.println("cannot drop the table");
//		}
		do {
			System.out.println("WELCOME TO THE QUIZ MANAGER APP");
			System.out.println("Select an option:");
			System.out.println("1. To Login as a Teacher");
			System.out.println("2. To Login as a Student");
			System.out.println("q. To exit the QUIZ MANAGER");
			System.out.println("What is your Choice");	
			userchoice = scanner.nextLine();
			switch (userchoice) {
			case "1":
				try {
					TeacherMenu.createQuestion();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "2":
				
				break;
			case "q":
				System.out.println("Thank you, See you soo. exiting ...");
				break;
			default:
				break;
			}
		} while (!"q".equals(userchoice));
		scanner.close();
		System.exit(0);
		
	}
}
