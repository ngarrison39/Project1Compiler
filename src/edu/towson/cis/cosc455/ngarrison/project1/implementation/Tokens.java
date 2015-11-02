	package edu.towson.cis.cosc455.ngarrison.project1.implementation;
	
	public class Tokens {
		//list all tokens here
		public static final String DOCB = "#BEGIN";
		public static final String DOCE = "#END";
			//separated out just for use with MyLexicalAnalyzer's charState
			public static final String HASH = "#";
		public static final String DEFB = "$DEF";
		public static final String DEFUSEE = "$END";
		public static final String USEB = "$USE";
		//separated out just for use with MyLexicalAnalyzer's charState
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
		
		public static String validTokens[] = {DOCB, DOCE, DEFB, DEFUSEE, USEB, HEAD, TITLEB, TITLEE, PARAB, PARAE, EQSIGN, BOLD, ITALICS, LISTITEMB, LISTITEME, NEWLINE, LINKB, LINKE, AUDIO, VIDEO, ADDRESSB, ADDRESSE}; //token bin //add this above main
		public static String tokenTags[] = {"#", "$", HEAD, TITLEB, TITLEE, PARAB, PARAE, EQSIGN, BOLD, ITALICS, LISTITEMB, LISTITEME, NEWLINE, LINKB, LINKE, AUDIO, VIDEO, ADDRESSB, ADDRESSE};
		public static String currentToken = "";
		
		public static String allFoundTokensTest = "";
		
		//TEXT = ("A".."Z" | "a".."z" | "0".."9" | "."| "," | ":" | "_" | "!" | "/" | "\u000C")* ;
		//WS  =   ( " " | "\t"  | "\r" | "\n" ) {$channel=HIDDEN;} ;

/*
		public Tokens(){
			String tokenBin[] = {DOCB, DOCE, DEFB, DEFUSEE, USEB, HEAD, TITLEB, TITLEE, PARAB, PARAE, EQSIGN, BOLD, ITALICS, LISTITEMB, LISTITEME, NEWLINE, LINKB, LINKE, AUDIO, VIDEO, ADDRESSB, ADDRESSE}; //token bin //add this above main;
			String[] tags;
			currentToken = "";
		}

		 public static String getNextToken(){
				return currentToken;
			}
*/

			
			public static boolean isTag(String thisChar){
				for(int i = 0; i < tokenTags.length; i++){
				if(thisChar.equals(tokenTags[i])){
					return true;
				}
			}
				return false;
		}		
	}