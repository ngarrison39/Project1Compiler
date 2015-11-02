 package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import edu.towson.cis.cosc455.ngarrison.project1.implementation.Tokens;
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
  
  /** Special string to look at the following character without setting nextCharacter */
  public static String tempChar = "";
  
  public MyLexicalAnalyzer(){
    completeFile = "";
  }
  
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
    } else{
      System.out.println(tokenBin + " tokenBin at line 43 MyLex");
      System.out.println();
      tokenBin = Tokens.currentToken;
      MySyntaxAnalyzer.saveToStack(tokenBin);
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
    for(int i = 0; i < Tokens.validTokens.length; i++){
      if(token.equals(Tokens.validTokens[i])){
        return true;
      }
    }
    
    //CREATE AN ERROR REPORT: NOT A VALID TOKEN LEX ERROR. print current token " is not a valid token for this language"
    //System.exit(1);
    
    return false;
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
      charStates(nextCharacter);
    }
    else if(currentPosition == completeFile.length()){
      if(lookupToken(Tokens.currentToken)){
        storeToken(Tokens.currentToken);
        
        //have syntax analyzer start working 
        //MySyntaxAnalyzer.testPrintStack();
      }
    }  
  }
  
  public static void storeToken(String saveToken){
    tokenBin = saveToken;
    Tokens.currentToken = "";
    MySyntaxAnalyzer.saveToStack(tokenBin);
  }
  
  public void charStates(String thisChar){
    switch (thisChar) {
      case Tokens.HASH:
        //for tokens beginning with '#'
      case Tokens.DOLLAR:
        //for tokens beginning with '$'
        addCharacter(thisChar);
        if(currentPosition < completeFile.length()){
          nextCharacter = String.valueOf(completeFile.charAt(currentPosition));
          while(!isSpace(nextCharacter)){
            addCharacter(nextCharacter);
            if(currentPosition < completeFile.length()){
              nextCharacter = String.valueOf(completeFile.charAt(currentPosition));
            } else if(currentPosition == completeFile.length()){
              if(lookupToken(Tokens.currentToken)){
                storeToken(Tokens.currentToken);
                
                //have syntax analyzer start working 
                
                break;
              }
            }
          }
          if(lookupToken(Tokens.currentToken)){
            storeToken(Tokens.currentToken);
            charStates(nextCharacter);
          }
        } else if(currentPosition == completeFile.length()){
          if(lookupToken(Tokens.currentToken)){
            storeToken(Tokens.currentToken);
            
            //have syntax analyzer start working 
            
          }
        }
        break;
        
      case Tokens.HEAD:  
        //for single character token HEAD
      case Tokens.TITLEB:  
        //for single character token TITLEB
      case Tokens.TITLEE:  
        //for single character token TITLEE
      case Tokens.PARAB:  
        //for single character token PARAB
      case Tokens.PARAE:  
        //for single character token PARAE
      case Tokens.EQSIGN:  
        //for single character token EQSIGN
      case Tokens.LISTITEMB: 
        //for single character token LISTITEMB  : DOES IT HAVE TO BE FOLLOWED BY A SPACE??? IF SO MAKE LIKE # AND $
      case Tokens.LISTITEME: 
        //for single character token LISTITEME
      case Tokens.NEWLINE: 
        //for single character token NEWLINE
      case Tokens.LINKB: 
        //for single character token LINKB
      case Tokens.LINKE: 
        //for single character token LINKE
      case Tokens.AUDIO:
        //for single character token AUDIO
      case Tokens.VIDEO: 
        //for single character token VIDEO
      case Tokens.ADDRESSB: 
        //for single character token ADDRESSB
      case Tokens.ADDRESSE: 
        //for single character token ADDRESSE
        addCharacter(thisChar);
        if(lookupToken(Tokens.currentToken)){
          storeToken(Tokens.currentToken);
          getCharacter(completeFile);
          getNextToken(completeFile);
        }
        break;
        
      case Tokens.ITALICS: 
        //for single character token ITALICS or two character token BOLD
        addCharacter(thisChar);
        if(String.valueOf(completeFile.charAt(currentPosition)).equals(Tokens.ITALICS)){
          addCharacter(thisChar);
          if(lookupToken(Tokens.currentToken)){
            storeToken(Tokens.currentToken);
            getNextToken(completeFile);
          }
        } else if(lookupToken(Tokens.currentToken)){
          storeToken(Tokens.currentToken);
          getNextToken(completeFile);
        }
        
        break;
        
      default: 
        //will be used for any plain text, whitespace, \n, \r, etc.
        addCharacter(thisChar);
        if(currentPosition < completeFile.length()){
          nextCharacter = String.valueOf(completeFile.charAt(currentPosition));
          if(Tokens.isTag(nextCharacter)){
            storeToken(Tokens.currentToken);
            getCharacter(nextCharacter);
            getNextToken(completeFile);
          } else if(currentPosition == completeFile.length()){
            if(lookupToken(Tokens.currentToken)){
              storeToken(Tokens.currentToken);
              
              //have syntax analyzer start working 
              
            }
          } else{
            getCharacter(completeFile);
          }
          break;
        }
    }
  }
}