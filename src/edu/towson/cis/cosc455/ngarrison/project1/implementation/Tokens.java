package edu.towson.cis.cosc455.ngarrison.project1.implementation;

public class Tokens {
	/* A list of all the valid tokens for the markdown language.  Text/whitespace is handled separately */ 
	public static final String DOCB = "#BEGIN";
	public static final String DOCE = "#END";
	/* separated out just for use with MyLexicalAnalyzer's charState */
		public static final String HASH = "#";
	public static final String DEFB = "$DEF";
	public static final String DEFUSEE = "$END";
	public static final String USEB = "$USE";
	/* separated out just for use with MyLexicalAnalyzer's charState */
		public static final String DOLLAR = "$";
	public static final String HEAD	= "^" ;
	public static final String TITLEB = "<" ;
	public static final String TITLEE = ">" ;
	public static final String PARAB = "{" ;
	public static final String PARAE = "}" ;
	public static final String EQSIGN = "=" ;
	public static final String BOLD = "**" ;
	public static final String ITALICS =  "*" ;
	public static final String LISTITEMB = "+" ;	
	public static final String LISTITEME = ";" ;
	public static final String NEWLINE	= "~" ;
	public static final String LINKB = "[" ;
	public static final String LINKE = "]" ;
	public static final String AUDIO = "@" ;
	public static final String VIDEO = "%" ;
	public static final String ADDRESSB	= "(" ;
	public static final String ADDRESSE	= ")" ;

	/* An array to easily search through the set of valid tokens for validation or case matching */
	public static String validTokens[] = {DOCB, DOCE, DEFB, DEFUSEE, USEB, HEAD, TITLEB, TITLEE, PARAB, PARAE, EQSIGN, BOLD, ITALICS, LISTITEMB, LISTITEME, NEWLINE, LINKB, LINKE, AUDIO, VIDEO, ADDRESSB, ADDRESSE}; //token bin //add this above main
	/* An array to easily search through the set of valid single character tags for validation or case matching */
	public static String tokenTags[] = {"#", "$", HEAD, TITLEB, TITLEE, PARAB, PARAE, EQSIGN, BOLD, ITALICS, LISTITEMB, LISTITEME, NEWLINE, LINKB, LINKE, AUDIO, VIDEO, ADDRESSB, ADDRESSE};
	/* Keeps track of the token being pieced together by the Lexical Analyzer */
	public static String currentToken = "";

	/* This method is used to determine if the string is a token for case matching */
	public static boolean isToken(String thisChar){
		for(int i = 0; i < validTokens.length; i++){
			if(thisChar.equals(validTokens[i])){
				return true;
			}
		}
		return false;
	}
	/* This method is used to determine if the string is a single character tag for case matching */
	public static boolean isTag(String thisChar){
		for(int i = 0; i < tokenTags.length; i++){
			if(thisChar.equals(tokenTags[i])){
				return true;
			}
		}
		return false;
	}		
}