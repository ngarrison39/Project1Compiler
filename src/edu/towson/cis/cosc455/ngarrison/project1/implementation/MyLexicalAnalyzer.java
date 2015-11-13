package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import edu.towson.cis.cosc455.ngarrison.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer{

	/** The next character. */
	String nextCharacter = "";

	/** Super string of the complete file */
	static String completeFile = "";

	/** The current position. */
	int currentPosition = 0;

	/** The stored token for the Syntax analyzer to use **/
	public static String tokenBin = "";

	/* breaks loop if getCharacter causes the end of the file to be reached */
	public static boolean reachedEnd = false;
	/* If this is true the previous token must be a parenthesis to allow special symbols such as ~, `, -, _, etc. for a URL that otherwise would not be allowed */
	public boolean wasParan = false;

	public MyLexicalAnalyzer(){
		completeFile = "";
	}
	/* The object used in MyCompiler to prime the compiler */
	public MyLexicalAnalyzer(String superString){
		completeFile = superString;
		getNextToken(superString);
	}
	/**
	 * This is the public method to be called when the Syntax Analyzer needs a new
	 * token to be parsed.
	 */
	public void getNextToken(String file){
		if(tokenBin.equals("")){
			getCharacter(file);
			charStates(nextCharacter);
		} else{
			tokenBin = Tokens.currentToken;
		}
	}
	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @return the character
	 **/
	public void getCharacter(String completeFile){ 
		if(currentPosition < completeFile.length()){
			nextCharacter = String.valueOf(completeFile.charAt(currentPosition));
		}
		else if(currentPosition == completeFile.length()){
			if(reachedEnd != true){
			if(lookupToken(Tokens.currentToken)){
				storeToken(Tokens.currentToken);
				reachedEnd = true;
			} else if(noSpecialTags(Tokens.currentToken)){
					storeToken(Tokens.currentToken);
					reachedEnd = true;
				} 
			}
		}
	}
	/**
	 * This method adds the current character to the nextToken.
	 */
	public void addCharacter(String charAdd){
		Tokens.currentToken = Tokens.currentToken + charAdd;
		currentPosition++;
		nextCharacter = "";
	}
	/**
	 * This  method checks if the current character is a space
	 *
	 * @param c the current character
	 * @return true, if is space; otherwise false
	 */
	public boolean isSpace(String c){
		if(c.equals(" ")){
			return true;
		} else
			return false;
	}
	/* Because text cannot be looked up this is used to make sure there are no symbols */
	public boolean noSpecialTags(String possible){
		for(int i = 0; i < possible.length(); i++){
			if(!String.valueOf(possible.charAt(i)).equals(Tokens.tokenTags[i])){
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
		for(int i = 0; i < Tokens.validTokens.length; i++){
			if(token.equals(Tokens.validTokens[i])){
				return true;
			}
		}
		System.out.println("Lexical error: The following token is not a valid token for this language.");
		System.out.println(Tokens.currentToken);
		System.out.println("Exiting token parsing.");
		System.exit(1);

		return false;
	}
	/* After the token is validated it is stored in the token bin for the Syntax Analyzer to access */
	public static void storeToken(String saveToken){
		tokenBin = saveToken;
		Tokens.currentToken = "";
	}
	/* This method checks to see if the text found is a special text symbol that is not allowed */
	public static boolean isSpecialText(String text){
		if(text.equals("`") || text.equals("-") || text.equals("_") || text.equals("&") ){
			return true;
		}
		else{
			return false;
		}
	}
	/* Handles the symbol/text states to properly manage each character */
	public void charStates(String thisChar){
		switch (thisChar) {
		case Tokens.HASH:
			/*for tokens beginning with '#' */
		case Tokens.DOLLAR:
			/* for tokens beginning with '$' */
			wasParan = false;
			addCharacter(thisChar);
			getCharacter(completeFile);
			while(!isSpace(nextCharacter)){
				addCharacter(nextCharacter.toUpperCase());
				getCharacter(completeFile);
				if(reachedEnd == true){
					break;
				}
			}
			if(reachedEnd == true){
				break;
			}
			if(lookupToken(Tokens.currentToken)){
				storeToken(Tokens.currentToken);
			}
			break;
			/* fall through case statements for tags that will be handled in the same manner */
		case Tokens.HEAD:  
			/* for single character token HEAD */
		case Tokens.TITLEB:  
			/* for single character token TITLEB */
		case Tokens.TITLEE:  
			/* for single character token TITLEE */
		case Tokens.PARAB:  
			/* for single character token PARAB */
		case Tokens.PARAE:  
			/* for single character token PARAE */
		case Tokens.EQSIGN:  
			/* for single character token EQSIGN */
		case Tokens.LISTITEMB: 
			/* for single character token LISTITEMB */
		case Tokens.LISTITEME: 
			/* for single character token LISTITEME */
		case Tokens.NEWLINE: 
			/* for single character token NEWLINE */
		case Tokens.LINKB: 
			/* for single character token LINKB */
		case Tokens.LINKE: 
			/* for single character token LINKE */
		case Tokens.AUDIO:
			/* for single character token AUDIO */
		case Tokens.VIDEO: 
			/* for single character token VIDEO */
		case Tokens.ADDRESSE: 
			/* for single character token ADDRESSE */
			wasParan = false;
			addCharacter(thisChar);
			if(lookupToken(Tokens.currentToken)){
				storeToken(Tokens.currentToken);
			}
			break;
		/* Separate from other cases to set boolean to allow special symbols/text within the address text*/
		case Tokens.ADDRESSB: 
			/* for single character token ADDRESSB */
			wasParan = true;
			addCharacter(thisChar);
			if(lookupToken(Tokens.currentToken)){
				storeToken(Tokens.currentToken);
			}
			break;
		case Tokens.ITALICS: 
			//for single character token ITALICS or two character token BOLD
			wasParan = false;
			addCharacter(thisChar);
			getCharacter(completeFile);
			if(nextCharacter.equals(Tokens.ITALICS)){
				addCharacter(thisChar);
				if(lookupToken(Tokens.currentToken)){
					storeToken(Tokens.currentToken);
				}
			} else{
				if(lookupToken(Tokens.currentToken)){
					storeToken(Tokens.currentToken);
				}
			}
			break;
		default: 
			/* default is used for any plain text, whitespace, \t, etc. */
			
			if(isSpecialText(thisChar)){
				if(wasParan == false){
					System.out.println("Lexical error on character:");
					System.out.println(thisChar);
					System.out.println("is undefined for this language. Exiting compiler.");
					System.exit(1);
				}
			}
			addCharacter(thisChar);
			getCharacter(completeFile);
			if(reachedEnd == true){
				break;
			} else if(Tokens.isTag(nextCharacter)){
				storeToken(Tokens.currentToken);
			} else{
				charStates(nextCharacter);
			}
			break;
		}
	}
}