package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Queue;
import java.util.Stack;
import java.util.HashMap;

public class MySemanticAnalyzer {
	public Stack<String> outputStack = new Stack<String>();
	//public Stack<String> tempStack = new Stack<String>();
	public Queue<String> html;
	
	String temp;
	String varName;
	String variableDef;
	/* Used when mapping to html for begin/end tags that are the same: *, **, ^ */
	boolean beginTagUsed = false;
	
	final String UPPERPLACEHOLDER = "~~*UPPER*~~";
	final String LOWERPLACEHOLDER = "~~*LOWER*~~";
	final String CURRENTHOLDER = "~~*CURRENT*~~";
	final String IGNOREBLOCK = "~~*IGNORE*~~";
    HashMap<String, String> hmap = new HashMap<String, String>();

    public void mapValues(){
    hmap.put("#BEGIN", "<html>");
    hmap.put("#END", "</html>");
    hmap.put("TITLEB", "<title>");
    hmap.put("TITLEE", "</title>");
    hmap.put("PARAB", "<p>");
    hmap.put("PARAE", "</p>");
    hmap.put("LISTITEMB", "<li>");
    hmap.put("LISTITEME", "</li>");
    hmap.put("NEWLINE", "<br>");
    
    
    hmap.put("LINKB", "<html>");
    hmap.put("LINKE", "</a>");
    
    
    hmap.put("AUDIO", "<audio controls><source src =\"");
    hmap.put("VIDEO", "<html>");
    hmap.put("ADDRESSB", "<html>");
    hmap.put("ADDRESSE", "<html>");
    hmap.put("AUDIO", "<html>");
    $DEF $USE
    }
	
	
	public void variableCheck(){
		while(!MySyntaxAnalyzer.tokenStack.isEmpty()){
			temp = MySyntaxAnalyzer.tokenStack.pop();
			if(temp.equals(Tokens.DEFUSEE)){
				outputStack.push(LOWERPLACEHOLDER);
				outputStack.push(temp);
				varName = MySyntaxAnalyzer.tokenStack.pop();
				outputStack.push(varName);
				temp = MySyntaxAnalyzer.tokenStack.pop();
				outputStack.push(temp);
				if(temp.equals(Tokens.DEFB)){
					MySyntaxAnalyzer.tokenStack.push(CURRENTHOLDER);
					varName ="";
					removeLowerBound();
					//break???
					variableCheck();
				} else if(temp.equals(Tokens.USEB)){
					searchForDefine();
				}
			} else{
				outputStack.push(temp);
			}	
		}
		convertToHTML();
	}
	
	public void variableReplace(){
		while(!temp.equals(LOWERPLACEHOLDER)){
			if(temp.equals(IGNOREBLOCK)){
				temp = outputStack.pop();
				ignoreBlockReplace();
			} else if(temp.equals(Tokens.USEB)){
				//do nothing and ignore the $USE
				//ignore the variable name
				outputStack.pop();
				//insert the corresponding variable value
				MySyntaxAnalyzer.tokenStack.push(variableDef);
				//ignore the $END
				outputStack.pop();
			} else{
				MySyntaxAnalyzer.tokenStack.push(temp);
				temp = outputStack.pop();
			}
		}
		
	}
	
	public void searchForDefine(){
		while(!MySyntaxAnalyzer.tokenStack.isEmpty()){
			temp = MySyntaxAnalyzer.tokenStack.pop();
			if(temp.equals(Tokens.PARAE)){
				ignoreBlockSearch();
			} else if(temp.equals(Tokens.DEFB)){
				
				//MySyntaxAnalyzer.tokenStack.push(CURRENTHOLDER);
				
				/*still need to store $DEF name = value $END in case variables are used in a (paragraph) block
				 *but there is no definition at the beginning of the (paragraph) block
				 *will have to ignore when converting to html
				 */
				MySyntaxAnalyzer.tokenStack.push(temp);
				temp = outputStack.pop();
				if(temp.equals(varName)){
					MySyntaxAnalyzer.tokenStack.push(temp);
					//stores equals sign
					MySyntaxAnalyzer.tokenStack.push(outputStack.pop());
					variableDef = outputStack.pop();
					//stores #END
					MySyntaxAnalyzer.tokenStack.push(outputStack.pop());
					temp = outputStack.pop();
					variableReplace();
				}
			} else if(temp.equals(Tokens.DOCB)){
				System.out.println("Semantic error: " + temp + " is undefined.  Exiting conversion process.");
				System.exit(1);
			}
		}
	}
	
	public void ignoreBlockSearch(){
		while(!temp.equals(Tokens.PARAB)){
			outputStack.push(temp);
			temp = MySyntaxAnalyzer.tokenStack.pop();
		}
		outputStack.push(temp);
		outputStack.push(IGNOREBLOCK);
		
		//do i need this here
		temp = MySyntaxAnalyzer.tokenStack.pop();
		//searchForDefine();
		
	}
	
	public void ignoreBlockReplace(){
		while(!temp.equals(Tokens.PARAE)){
			MySyntaxAnalyzer.tokenStack.push(temp);
			temp = outputStack.pop();
		}
		MySyntaxAnalyzer.tokenStack.push(temp);
		temp = outputStack.pop();
	}

	public void removeLowerBound(){
		while(!outputStack.isEmpty()){
			temp = outputStack.pop();
			if(temp.equals(LOWERPLACEHOLDER)){
				temp = MySyntaxAnalyzer.tokenStack.pop();
				while(!temp.equals(CURRENTHOLDER)){
					outputStack.push(temp);
					temp = MySyntaxAnalyzer.tokenStack.pop();
				}
			} else{
				MySyntaxAnalyzer.tokenStack.push(temp);
			}
		}

	}
	public void convertToHTML(){
		while(!outputStack.isEmpty()){
			temp = outputStack.pop();
			switch (temp) {
			case Tokens.DOCB:
				
				break;
			//ignore these two cases, they are not used for html output
			case Tokens.DEFB:				
			case Tokens.DEFUSEE:
				
				break;
			case Tokens.HEAD:  
				html.enqueue("<h>");
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
				}
				break;

			case Tokens.ITALICS: 
				//for single character token ITALICS or two character token BOLD
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
				//will be used for any plain text, whitespace, \n, \r, etc.
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
