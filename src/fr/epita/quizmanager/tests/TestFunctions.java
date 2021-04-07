package fr.epita.quizmanager.tests;

import java.util.Arrays;

public class TestFunctions {
	public static void main(String[] args) {
		String [] string = new String [] {"a","p","d"};
		for (String i : string) {
		System.out.println(i);
		}
		System.out.println(String.valueOf(string));
		System.out.println(Arrays.asList(string));
		System.out.println(String.valueOf(Arrays.asList(string)));

	}
}
