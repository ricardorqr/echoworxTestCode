package com.echoworx;

import java.io.File;
import java.io.FileNotFoundException;

import com.echoworx.interpreter.Interpreter;

public class Main {

	public static void main(String[] args) {
		try {
			File folder = new File(".\\messages");
			File[] listOfFiles = folder.listFiles();
			
			
			for (File file : listOfFiles) {
				new Interpreter(file);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
