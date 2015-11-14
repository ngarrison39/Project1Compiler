package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyCompiler {
	public static String completeFile = "";
	public static String fileName = "";
	public static void main(String args[]){
		//String fileName = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the filename to be used with extension .mkd :");
		/* hasNextLine to use filepath */
		if(input.hasNextLine()){
			fileName = input.nextLine();
		}
		while(!fileName.endsWith(".mkd")){
			System.out.println("The filename must be of extension .mkd");
			System.out.println("Please re-enter the filename:");
			fileName = input.nextLine();
		}
		try {
			Scanner file = new Scanner(new File(fileName));
			while(file.hasNextLine()){
				completeFile += file.nextLine(); 
			}
			input.close();
			file.close();
			
			/* The objects below are used to initiate each separate part of the compiler process
			 * there may have been a better way to solve the static/non-static issues but for the time being this worked
			 */
			
			/* This will get the first token without the Syntax Analyzer asking for it, it "primes" the compiler */
			MyLexicalAnalyzer prime = new MyLexicalAnalyzer(completeFile);
			/* When the Lexical Analyzer is validates all the tokens of the file the Syntax Analyzer will begin creating the "parse tree" */
			MySyntaxAnalyzer  build = new MySyntaxAnalyzer();
			/* After a valid "parse tree" is created the Semantic Analyzer will check that all variables uses are valid and convert the tokens to HTML */
			MySemanticAnalyzer convert = new MySemanticAnalyzer();

		} catch (FileNotFoundException e) {
			System.out.println("The file " + fileName + " was not found.");
			System.out.println("Exiting program.");
			System.exit(1);
			e.printStackTrace();
		}
	}
}
