package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer{
	public static Stack<String> tokenStack = new Stack<String>();
	public static Stack<String> supplementalStack = new Stack<String>();

	//public static int printCount = 1;


/*	
	public static void saveToStack(String token){
		System.out.println("SyntaxAnalyzer received token");
		tokenStack.push(MyLexicalAnalyzer.tokenBin);
		System.out.println("Token #" + printCount + " is :");
		printCount++;
		System.out.println("-" + tokenStack.pop() + "-");
		System.out.println();
		MyLexicalAnalyzer.tokenBin = "";
		//MyLexicalAnalyzer.getNextToken(MyLexicalAnalyzer.completeFile);
	}
*/

	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 */
	public void markdown() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.HEAD)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token");
			//get next token
			head();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.TITLEB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			title();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.PARAB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			paragraph();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LISTITEMB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			listitem();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.NEWLINE)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			newline();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LINKB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			link();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.AUDIO)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			audio();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.VIDEO)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			video();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DEFB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			variableDefine();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.USEB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			variableUse();			
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCE)){

			//does it stop here?? and submit doc as is
			//or check if more tokens and error if so?

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

				//IS TEXT REQUIRED OR OPTIONAL???

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
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

				//IS TEXT REQUIRED OR OPTIONAL???

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
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
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			variableDefine();	
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
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

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				bold(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEMB)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.AUDIO)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.VIDEO)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.NEWLINE)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.USEB)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
				paragraph();
			} else{


				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);

			}
		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			innerText();
		}

	}

	/**
	 * This method implements the BNF grammar rule for the variable-define annotation.
	 * @throws CompilerException
	 */
	public void variableDefine() throws CompilerException{
		//will need a method to strip only white space for in between =
	}

	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 */
	public void variableUse() throws CompilerException{
		//will need a method to strip only white space for in between =

	}

	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 */
	public void bold() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){ //change to check if token not tag
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				markdown();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
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
				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				markdown();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
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
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
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

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				bold(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKB)){

				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//get next token
				italics(); // THIS WILL CALL MARKDOWN WHEN DONE DONT WANT THAT
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEME)){
				listitem();
			} else{


				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);

			}
		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			innerItem();
		}

	}

	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 */
	public void link() throws CompilerException{
		boolean descriptionText = false;
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			if(descriptionText == false){

				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion "); // **** --> NEED TO CHECK FOR IF ITS ONLY WHITE SPACE AND NO "TEXT" ??
				System.exit(1);
			} else{
				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//descriptionText = false;
				//get next token
				if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
					tokenStack.push(MyLexicalAnalyzer.tokenBin);
					System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
					//get next token
					address();
				}
			}
		} else if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		} else{
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			link();
		}	
	}

	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 */
	public void audio() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			address();
		} else{

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		}
	}

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	public void video() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
			//get next token
			address();
		} else{

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		}
	}

	public void address() throws CompilerException{
		boolean addressText = false;
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion "); //CAN ONLY BE TEXT
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){
			if(addressText == false){

				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion "); //MUST CONTAIN TEXT
				System.exit(1);

			} else{
				tokenStack.push(MyLexicalAnalyzer.tokenBin);
				System.out.println("SyntaxAnalyzer received token" + MyLexicalAnalyzer.tokenBin);
				//addressText = false;
				//get next token
			}
		}

	}

	/**
	 * This method implements the BNF grammar rule for the newline annotation.
	 * @throws CompilerException
	 */
	public void newline() throws CompilerException{

		//do you even need this?? theres nothing else to do for it
	}

}
