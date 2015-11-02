package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import edu.towson.cis.cosc455.ngarrison.project1.implementation.MySyntaxAnalyzer;

public class MyCompiler {
	public static String completeFile = "#BEGIN ^header here^ $DEF = @%* afga @ % * ~ { ] [ } [erw] {erg } ; gc*gc%gc@gc(gc;gc^gc**gc-gc=gc?  this should be text **bold here** #END";
	
	public static void main(String args[]){
		System.out.println(completeFile + " --> completeFile at line 11 MyComp");
		MyLexicalAnalyzer createTokens = new MyLexicalAnalyzer(completeFile);
	}
	
	/*
	public String completeFile = "";
	//public static String currentToken;
	
	public static void main(){
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the filename to be used:");
		try {
			Scanner file = new Scanner(new File(input.next()));
			
			
			
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();
	}
	*/

}
