package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyCompiler {
	//public static String completeFile;
///*	
	//public static String completeFile = "#BEGIN ^header here^ $DEF = @%* afga @ % * ~ { ] [ } [erw] {erg } ; gc*gc%gc@gc(gc;gc^gc**gc-gc=gc?  this should be text **bold here** #END";
	//public static String completeFile = "#BEGIN ^<simple  test>^ { ** bold here ** } *italics here* plain text @(http://www.help.com) #END";
	public static String completeFile = "#BEGIN      { $DEF lastname = Simpson $END The members of the $USE name $END family are: + Homer $USE name $END ; + Marge $USE lastname $END ; + Bart $USE lastname $END ; + Lisa $USE lastname $END ; + Maggie $USE lastname $END ; }{The members of the $USE name $END extended family are: + Abe $USE name $END ; + Mona $USE name $END ; } #END ";

	public static void main(String args[]){
		System.out.println(completeFile + " --> completeFile at line 11 MyComp");
		MyLexicalAnalyzer prime = new MyLexicalAnalyzer(completeFile);
		MySyntaxAnalyzer  build = new MySyntaxAnalyzer();
		//MySemanticAnalyzer convert = new MySemanticAnalyzer();
	}
	// */
	/*
	public static void main(String args[]){
		String fileName = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the filename to be used with extension .mkd :");
		if(input.hasNextLine()){  //hasNextLine to use filepath
			fileName = input.next();
		}
		while(!fileName.endsWith(".mkd")){
			System.out.println("The filename must be of extension .mkd");
			System.out.println("Please re-enter the filename:");
			fileName = input.next();
		}
		try {
			Scanner file = new Scanner(new File(fileName));  // need to put fileName in quotes???
			while(file.hasNextLine()){
				completeFile += file.nextLine(); 
			}
			input.close();
			file.close();
			System.out.println(completeFile + " --> completeFile at line 11 MyComp");
			MyLexicalAnalyzer prime = new MyLexicalAnalyzer(completeFile);
			MySyntaxAnalyzer  build = new MySyntaxAnalyzer();

		} catch (FileNotFoundException e) {
			System.out.println("The file " + fileName + " was not found.");
			System.out.println("Exiting program.");
			System.exit(1);
			//e.printStackTrace();
		}
	}
	*/
}
