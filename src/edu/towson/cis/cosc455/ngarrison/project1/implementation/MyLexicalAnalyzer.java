package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import edu.towson.cis.cosc455.ngarrison.project1.implementation.Tokens;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer{
	
	//public String tokenBin[];
	//public String tags[];
	//public String completeFile;
	
    /** The next character. */
    String nextCharacter = "";
    
    /** A string to build the next token for lookup  */
    String buildToken = "";

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
	
/*	public void validateString(String allFile, int currentPosition){
		String temp = "";
		if(isSpace(temp)){
			currentPosition++;
			getCharacter();
		while(currentPosition < allFile.length()){
				temp = temp + allFile.charAt(currentPosition);
				if(isSpace(temp)){
					currentPosition++;
				} else{
					
				}
				String currentToken = allFile.next();
				for(int i = 0; i < currentToken.length(); i++){
					
				}
				
			} 
	}
}
*/
	

	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @return the character
	 */
	public void getCharacter(){
		String tempString = "";
		if(currentPosition < completeFile.length()){
			tempString = String.valueOf(completeFile.charAt(currentPosition));
				//if() allFile.charAt(currentPosition) == "";
					//then error, no substance
			if(isTag(String.valueOf(buildToken.charAt(0)))){
				if(isSpace(tempString)){
					lookupToken(buildToken);
				} else {
					addCharacter(tempString);
					getCharacter();
				}
			} else{
				addCharacter(tempString);
				getCharacter();
			}
		} else{
			//at the end???
		}
	}
		/*
		nextCharacter = nextCharacter + file.charAt(currentPosition);
		currentPosition++;
		String temp = "";
		if(currentPosition < file.length()){
			//temp = temp + file.charAt(currentPosition);
			} else{
				nextCharacter = nextCharacter + temp;
			}
		}*/

	 /**
     * This method adds the current character to the nextToken.
     */
	public void addCharacter(String nextToken){
			buildToken = buildToken + nextToken;
			currentPosition++;
	}

	/**
	 * This  method checks if the current character is a space
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
	
	public boolean isTag(String t){
		for(int i = 0; i < tags.length; i++){
		if(t.equals(tags[i])){
			return true;
		}
	}
			return false;
}

	/**
	 * This method checks to see if the current, possible token is legal in the
	 * defined grammar.
	 *
	 * @return true, if it is a legal token, otherwise false
	 */
	public boolean lookupToken(String token){
		for(int i = 0;i < tokenBin.length; i++){
		if(token.equals(tokenBin[i])){
			return true;
		}
	}
			return false;
	}

}
