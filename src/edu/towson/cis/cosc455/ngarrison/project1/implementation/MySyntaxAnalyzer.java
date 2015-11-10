package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer{
	public static Stack<String> tokenStack = new Stack<String>();
	public static Stack<String> supplementalStack = new Stack<String>();

	// Used to see if variable defined as first thing in block
	public static int defCount = 0;

	public static int printCount = 1;

	public static void saveToStack(String token){
		System.out.println("SyntaxAnalyzer received token");
		tokenStack.push(token);
		System.out.println("Token #" + printCount + " is :");
		printCount++;
		System.out.println("-" + tokenStack.pop() + "-");
		System.out.println();
		MyLexicalAnalyzer.tokenBin = "";
		//MyLexicalAnalyzer.getNextToken(MyLexicalAnalyzer.completeFile);
	}

	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 */
	public void markdown() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCB)){
			//Add to parse tree
			//get next token
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.HEAD)){
			//Add to parse tree
			//get next token
			head();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.TITLEB)){
			//Add to parse tree
			//get next token
			title();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.PARAB)){
			//Add to parse tree
			//get next token
			paragraph();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LISTITEMB)){
			//Add to parse tree
			//get next token
			listitem();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.NEWLINE)){
			//Add to parse tree
			//get next token
			newline();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LINKB)){
			//Add to parse tree
			//get next token
			link();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.AUDIO)){
			//Add to parse tree
			//get next token
			audio();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.VIDEO)){
			//Add to parse tree
			//get next token
			video();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DEFB)){
			//Add to parse tree
			//get next token
			variableDefine();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.USEB)){
			//Add to parse tree
			//get next token
			variableUse();
		} else{
			//accept as text
		}
	}

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	public void head() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){ //change to check if token not tag
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.HEAD)){
				//Add to parse tree
				//get next token
				markdown();
			} else{
				//ERROR REPORT NOT VALID IN TITLE STRUCTURE
			}
		} else{
			//Add to parse tree
			//get next token
			head();
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException

	 */
	public void title() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){ //change to check if token not tag
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.TITLEE)){
				//Add to parse tree
				//get next token
				markdown();
			} else{
				//ERROR REPORT NOT VALID IN TITLE STRUCTURE
			}
		} else{
			//Add to parse tree
			//get next token
			title();
		}		
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
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFB)){
			//add to parse tree
			//get next token
			variableDefine();	
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
			//Add to parse tree
			//get next token
			defCount = 0; //wont need this with BNF style?
			markdown();
		} else{
			innerText();
		}			
	}

	/**
	 * This method implements the BNF grammar rule for the inner-text annotation.
	 * @throws CompilerException
	 */
	public void innerText() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){ //change to check if token not tag
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				defCount++;
				//Add to parse tree which is the stack?
				//get next token
				bold(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEMB)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.AUDIO)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.VIDEO)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.NEWLINE)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.USEB)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
				paragraph();
			} else{


				//ERROR REPORT NOT VALID IN TITLE STRUCTURE

			}
		} else{
			//Add to parse tree
			//get next token
			innerText();
		}

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
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){ //change to check if token not tag
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				//Add to parse tree
				//get next token
				markdown();
			} else{
				//ERROR REPORT NOT VALID IN BOLD STRUCTURE
			}
		} else{
			//Add to parse tree
			//get next token
			bold();
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @throws CompilerException
	 */
	public void italics() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){
				//Add to parse tree
				//get next token
				markdown();
			} else{
				//ERROR REPORT NOT VALID IN ITALICS STRUCTURE
			}
		} else{
			//Add to parse tree
			//get next token
			italics();
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the listitem annotation.
	 * @throws CompilerException
	 */
	public void listitem() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEME)){
			//add to parse tree
			//get next token
			markdown();	
		} else{
			innerItem();
		}			
	}

	/**
	 * This method implements the BNF grammar rule for the inner-item annotation.
	 * @throws CompilerException
	 */
	public void innerItem() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){ //change to check if token not tag
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				defCount++;
				//Add to parse tree which is the stack?
				//get next token
				bold(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKB)){
				defCount++;
				//Add to parse tree
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEME)){
				listitem();
			} else{


				//ERROR REPORT NOT VALID IN TITLE STRUCTURE

			}
		} else{
			//Add to parse tree
			//get next token
			innerItem();
		}

	}

	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 */
	public void link() throws CompilerException{
		boolean hasText = false;
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			if(hasText == false){

				//ERROR MUST CONTAIN TEXT

			} else{
				//add to parse tree
				hasText = false;
				//get next token
				if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
					//add to parse tree
					//get next token
					if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){

						//ERROR CAN ONLY BE TEXT

					}

				}
			}
		} else if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){

			//ERROR DOES NOT FOLLOW SYNTAX

		} else{
			//add to parse tree
			//get next token
			link();
		}	
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
