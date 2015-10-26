package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.io.File;
import java.util.Scanner;

public class MyCompiler {
	public String completeFile = "";
	public static String currentToken;
	
	public static void main(){
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the filename to be used:");
		input.close();
		
		/*
		 * try{ FileScanner = ....
		 * catch{ fileNotFoundException....
		 */
		
		//create filescanner
		//he suggested taking entire file as one line the going through and taking tokens
		//would it be better to jsut do it line by line from file? (would have to keep calling for next line
	}
	
	public void markdown() throws CompilerException{
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){ //create a Token class below
		
		//do stuff
		//start checking for special symbols or text
		//if symbol --> go into symbol state
			//keep collecting characters until an end symbol is found
			//if text found enter text state
		
		//else if text --> go into text state
			//keep finding text/whitespace until a symbol is found
				//enter symbol state
		
		
		
		//check for DOCE here?
		
		}
		else{
		//error stuff
		}
	}

}
