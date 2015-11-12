package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Queue;
import java.util.Stack;

public class MySemanticAnalyzer {
	public Stack<String> outputStack = new Stack<String>();
	public Stack<String> tempStack = new Stack<String>();
	public Queue<String> html;
	String temp;
	String varName;
	String variableDef;
	final String UPPERPLACEHOLDER = "~~*UPPER*~~";
	final String LOWERPLACEHOLDER = "~~*LOWER*~~";
	final String CURRENTHOLDER = "~~*CURRENT*~~";
	final String IGNOREBLOCK = "~~*IGNORE*~~";
	
	
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
		
	}

}
