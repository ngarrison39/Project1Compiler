package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyCompiler {
	//public static String completeFile = "#BEGIN ^header here^ $DEF = @%* afga @ % * ~ { ] [ } [erw] {erg } ; gc*gc%gc@gc(gc;gc^gc**gc-gc=gc?  this should be text **bold here** #END";
	public static String completeFile = "#BEGIN ^<simple test>^ #END";

	public static void main(String args[]){
		System.out.println(completeFile + " --> completeFile at line 11 MyComp");
		MyLexicalAnalyzer prime = new MyLexicalAnalyzer(completeFile);
		/*
		String fileName = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the filename to be used with extension .mkd:");
		if(input.hasNext()){
		fileName = input.next();
		}
		try {
			Scanner file = new Scanner(new File(fileName));
			while(file.hasNextLine()){
				completeFile += file.nextLine(); 
			}
			input.close();
			file.close();
			System.out.println(completeFile + " --> completeFile at line 11 MyComp");
			MyLexicalAnalyzer createTokens = new MyLexicalAnalyzer(completeFile);


		} catch (FileNotFoundException e) {
			System.out.println("The file " + fileName + " was not found.");
			System.out.println("Exiting program.");
			System.exit(1);
			//e.printStackTrace();
		}

		 */
	}
}
