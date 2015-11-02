package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer{
	public static Stack<String> tokenStack = new Stack<String>();
	public static Stack<String> supplementalStack = new Stack<String>();
	
	public static int printCount = 1;
	
	public static void saveToStack(String token){
		System.out.println("SyntaxAnalyzer received token");
		tokenStack.push(token);
		System.out.println("Token #" + printCount + " is :");
		printCount++;
		System.out.println(tokenStack.pop());
		System.out.println();
		MyLexicalAnalyzer.tokenBin = "";
		//MyLexicalAnalyzer.getNextToken(MyLexicalAnalyzer.completeFile);
	}
	
	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 */
	public void markdown() throws CompilerException{
	/*	if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){ //create a Token class below
			
		//do stuff
		//start checking for special symbols or text
		//if symbol --> go into symbol state
			//keep collecting characters until an end symbol is found
			//if text found enter text state
		
		//else if text --> go into text state
			//keep finding text/whitespace until a symbol is found
				//enter symbol state
		
		
		
		//check for DOCE here?
		
		}
		else{
		//error stuff
		}
		*/
	}
	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	public void head() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException

	 */
	public void title() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the body annotation.
	 * @throws CompilerException
	 */
	public void body() throws CompilerException{
		
	}
	
	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 */
	public void paragraph() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the inner-text annotation.
	 * @throws CompilerException
	 */
	public void innerText() throws CompilerException{
		
	}
	
	/**
	 * This method implements the BNF grammar rule for the variable-define annotation.
	 * @throws CompilerException
	 */
	public void variableDefine() throws CompilerException{
		
	}
	
	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 */
	public void variableUse() throws CompilerException{
		
	}
	
	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 */
	public void bold() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @throws CompilerException
	 */
	public void italics() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the listitem annotation.
	 * @throws CompilerException
	 */
	public void listitem() throws CompilerException{
		
	}
	
	/**
	 * This method implements the BNF grammar rule for the inner-item annotation.
	 * @throws CompilerException
	 */
	public void innerItem() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 */
	public void link() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 */
	public void audio() throws CompilerException{
		
	}

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	public void video() throws CompilerException{
		
	}

	/**
	* This method implements the BNF grammar rule for the newline annotation.
	* @throws CompilerException
	*/
	public void newline() throws CompilerException{
		
	}

}
