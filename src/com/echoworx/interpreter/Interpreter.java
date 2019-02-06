package com.echoworx.interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.echoworx.model.Message;

public class Interpreter {
	
	private Message message;

	public Interpreter(File file) throws FileNotFoundException {
		message = getLines(file);
		printMessage(checkMessage(message));
	}

	private Message getLines(File file) throws FileNotFoundException {
		if (file.isFile() && file.getName().endsWith(".txt")) {
			Scanner scanner = new Scanner(file);
			message = new Message();
			List<String> body = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if (line.startsWith(Tag.TO)) {
					message.setTo(line);
					continue;
				} else if (line.startsWith(Tag.FROM)) {
					message.setFrom(line);
					continue;
				} else if (line.startsWith(Tag.SUBJECT)) {
					message.setSubject(line);
					continue;
				} else {
					body.add(line);
				}
			}
			message.setBody(body);
			scanner.close();
		}

		return message;
	}
	
	private Message checkMessage(Message message) {
		if (checkEmailDomain(message.getTo())) {
			List<String> body = message.getBody();
			List<String> newBody = new ArrayList<String>();
			
			for (String line : body) {
				newBody.add(replacementRule(line));
			}
			
			message.setBody(newBody);
		} 

		if (checkSubjectStartsWithSECURE(message.getSubject())) {
			List<String> body = message.getBody();
			List<String> newBody = new ArrayList<String>();
			
			for (String line : body) {
				newBody.add(reversalRule(line));
			}
			
			message.setBody(newBody);
		}
		
		if (checkBody(message.getBody())) {
			List<String> body = message.getBody();
			List<String> newBody = new ArrayList<String>();
			
			for (int i = 1; i < body.size(); i++) {
				newBody.add(reversalRule(replacementRule(body.get(i))));
			}
			
			message.setBody(newBody);
		}
		
		return message;
	}
	
	private void printMessage(Message message) {
		System.out.println("=== Begin ===");
		System.out.println(message.getTo());
		System.out.println(message.getFrom());
		System.out.println(message.getSubject());
		message.getBody().stream().forEach(System.out::println);
		System.out.println("=== End ===");
	}
	
	private boolean checkEmailDomain(String line) {
		if (line.contains(Tag.DOMAINCOM)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkSubjectStartsWithSECURE(String line) {
		if (line.contains(Tag.SECURE)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkBody(List<String> body) {
		for (String line : body) {
			String words[] = line.split(Pattern.quote(" "));
			
			for (String word : words) {
				if (word.length() >= 10) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private String replacementRule(String line) {
		char[] chars = line.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '$') {
				chars[i] = 'e';
			}

			if (chars[i] == '^') {
				chars[i] = 'y';
			}
			
			if (chars[i] == '&') {
				chars[i] = 'u';
			}
		}
		
		return String.valueOf(chars);
	}

	private String reversalRule(String line) {
		String newLine = "";
		String[] words = line.split(Pattern.quote(" "));

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String reverseWord = "";
			
			for (int j = word.length() - 1; j >= 0; j--) {
				reverseWord += word.charAt(j);
			}
			
			newLine += reverseWord + " ";
		}
		
		return newLine;
	}
	
}
