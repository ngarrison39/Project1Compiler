package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import edu.towson.cis.cosc455.ngarrison.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer{
	
    /** The next character. */
    String nextCharacter = "";

    /** The current position. */
    int currentPosition = 0;
    
    /** The current elements length */
    int inputLength = 0;
    
	/**
	 * This is the public method to be called when the Syntax Analyzer needs a new
	 * token to be parsed.
	 */
	public void getNextToken(){
		//get the next token from the bin
	}
	

	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @return the character
	 */
	public void getCharacter(String input){
		if(currentPosition < inputLength){
			
			nextCharacter = nextCharacter + input.charAt(currentPosition + 1);
		}
		
	}

	 /**
     * This method adds the current character to the nextToken.
     */
	public void addCharacter(String nextToken){
			nextToken = nextToken + line.charAt(currentPosition);
	}

	/**
	 * This  method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @param c the current character
	 * @return true, if is space; otherwise false
	 */
	public boolean isSpace(String c){
		if(c == " "){
			return true;
		} else
		return false;
	}

	/**
	 * This method checks to see if the current, possible token is legal in the
	 * defined grammar.
	 *
	 * @return true, if it is a legal token, otherwise false
	 */
	public boolean lookupToken(String token){
		for(int i = 0;i < tokenBin[].size(); i++){
		if(token.equals(tokenBin[i])){
			return true;
		}
	}
			return false;
	}

}
