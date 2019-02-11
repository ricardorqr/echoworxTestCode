package com.echoworx;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.echoworx.interpreter.Interpreter;

public class Main {

	public static void main(String[] args) {
		Path dir = Paths.get(".\\messages");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
		    for (Path file: stream) {
				new Interpreter(file.toFile());
//				new Interpreter(new File(".\\messages\\132443512.txt"));
		    }
		} catch (IOException | DirectoryIteratorException e) {
		    e.printStackTrace();
		}
	}

}
