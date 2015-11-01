 package edu.towson.cis.cosc455.ngarrison.project1.implementation;
 
 import edu.towson.cis.cosc455.ngarrison.project1.implementation.Tokens;
 import edu.towson.cis.cosc455.ngarrison.project1.interfaces.LexicalAnalyzer;
 
 public class MyLexicalAnalyzer implements LexicalAnalyzer{
   
     /** The next character. */
     String nextCharacter = "";
     
     /** Super string of the complete file */
     static String completeFile = "";
 
     /** The current position. */
     int currentPosition;
     
  public MyLexicalAnalyzer(){
   completeFile = "";
   //currentPosition = 0;
  }
  
  public MyLexicalAnalyzer(String superString){
   completeFile = superString;
   getNextToken(superString);
   //currentPosition = 0;
  }
     
     public void primeCompiler(MyLexicalAnalyzer primer){
   //System.out.println(MyLexicalAnalyzer.completeFile + " tokenBin at line 29 MyLex");
   //System.out.println();
   getCharacter(MyLexicalAnalyzer.completeFile);
 }
     
  /**
   * This is the public method to be called when the Syntax Analyzer needs a new
   * token to be parsed.
   */
     
  public void getNextToken(String file){
   //System.out.println(Tokens.tokenBin + " tokenBin at line 39 MyLex");
   //System.out.println();
   if(Tokens.tokenBin.equals("")){
    getCharacter(MyLexicalAnalyzer.completeFile);
   } else{
   System.out.println(Tokens.tokenBin + " tokenBin at line 43 MyLex");
   System.out.println();
   Tokens.tokenBin = Tokens.getNextToken();
   MySyntaxAnalyzer.storeToken();
   }
  }
  
  
  /**
   * This is method gets the next character from the input and places it in
   * the nextCharacter class variable.
   *
   * @return the character
   */
  public void getCharacter(String completeFile){
   nextCharacter = "";
   //System.out.println(completeFile.length() + " completeFile.length() at line 79 MyLex");
   //System.out.println();
   //System.out.println(currentPosition + " currentPosition at line 80 MyLex");
   //System.out.println();
   if(currentPosition < completeFile.length()){
    nextCharacter = String.valueOf(completeFile.charAt(currentPosition));
    System.out.println(nextCharacter + " nextCharacter at line 88 MyLex");
    System.out.println();
     //if() allFile.charAt(currentPosition) == "";
      //then error, no substance
    if(Tokens.currentToken.length() > 0){
     if(charIsTag(nextCharacter)){
      Tokens.tokenBin = Tokens.currentToken;
      Tokens.currentToken = "";
      getCharacter(completeFile);
     } else if(charIsTag(String.valueOf(Tokens.currentToken.charAt(0)))){
     System.out.println(Tokens.currentToken + " this is the currentToken line 94 MyLex");
     System.out.println();
     //System.out.println("This is space check -" + nextCharacter + "- in MyLex line 96");
     //System.out.println();
     if(isSpace(nextCharacter)){
      //System.out.println(Tokens.currentToken + " this is the currentToken line 96 MyLex");
      if(lookupToken(Tokens.currentToken)){
       //System.out.println(Tokens.currentToken + " this is the currentToken line 98 MyLex");
       Tokens.tokenBin = Tokens.currentToken;
       Tokens.currentToken = "";
       getCharacter(completeFile);
      }
     } else {
      addCharacter(nextCharacter);
      getCharacter(completeFile);
     }
    } else{
     addCharacter(nextCharacter);
     getCharacter(completeFile);
    }
   } else{
    addCharacter(nextCharacter);
    getCharacter(completeFile);
   }
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
    Tokens.currentToken = Tokens.currentToken + nextToken;
    currentPosition++;
    //System.out.println(currentPosition + " currentPosition at line 120 MyLex");
    //System.out.println();
  }
 
  /**
   * This  method checks if the current character is a space
   *
   * @param c the current character
   * @return true, if is space; otherwise false
   */
  public boolean isSpace(String c){
   if(c.equals(" ")){
    //System.out.println("This is space check -" + c + "- in MyLex line 148");
    //System.out.println();
    return true;
   } else
   return false;
  }
  
  public static boolean charIsTag(String t){
   if(Tokens.isTag(t)){
    return true;
   } else{
    return false;
   }
 }
 
  /**
   * This method checks to see if the current, possible token is legal in the
   * defined grammar.
   *
   * @return true, if it is a legal token, otherwise false
   */
  public boolean lookupToken(String token){
   if(Tokens.isToken(token)){
    return true;
   } else{
    //ERROR MESSAGE LEXICAL ERROR
    return false;
   }
 
  }
  
 }