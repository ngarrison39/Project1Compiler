package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;

public class MySemanticAnalyzer {
	public Stack<String> outputStack = new Stack<String>();
	String temp;
	String varName;
	String variableDef;
	final String UPPERPLACEHOLDER = "~~*UPPER*~~";
	final String LOWERPLACEHOLDER = "~~*LOWER*~~";
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
					varName ="";
					//break???
					variableCheck();
				} else if(temp.equals(Tokens.USEB)){
					searchForDefine();
				}
			} else{
				outputStack.push(temp);
			}	
		}
	}
	
	public void variableReplace(){
		
	}
	
	public void searchForDefine(){
		while(!MySyntaxAnalyzer.tokenStack.isEmpty()){
			temp = MySyntaxAnalyzer.tokenStack.pop();
			if(temp.equals(Tokens.PARAE))
			outputStack.push(temp);
			ignoreBlock();
		}
	}
	
	public void ignoreBlock(){
		while(!MySyntaxAnalyzer.tokenStack.isEmpty() && !temp.equals(Tokens.PARAB)){
			temp = MySyntaxAnalyzer.tokenStack.pop();
			outputStack.push(temp);
		}
		if(temp.equals(Tokens.PARAB)){
			while(!outputStack.isEmpty() && !temp.equals(Tokens.PARAB)){
				temp = MySyntaxAnalyzer.tokenStack.pop();
				outputStack.push(temp);
			}
		}
	}

	public void removeLowerBound(){

	}

}
