package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer{
	public static Stack<String> tokenStack = new Stack<String>();
	public static Stack<String> supplementalStack = new Stack<String>();

	public MyLexicalAnalyzer buildParseStack;
	public boolean created = false;

	/* Used to check if required text is present*/
	boolean hasText = false;
	/* Used to check if #BEGIN is first token in file */
	int tokenCounter = 0;
	/* Used to check if #BEGIN is used in file */
	boolean beginReceived = false;
	/* Used to check if #END used in file*/
	boolean endReceived = false;
	
	public int primer = 0;

	//This was a quick fix because of static/non-static issues 
	public MySyntaxAnalyzer(){
		try{
			primer = 1;
			markdown();
		}
		catch(CompilerException e){
			System.out.println("CompilerExceptionError");
		}
	}


	public void askForToken(){
		if(MyLexicalAnalyzer.reachedEnd == true){
			if(endReceived == false){
				System.out.println("Syntax error: #END was not found in the file. Exiting conversion ");
				System.exit(1);
			}
		} else{
		if(created == false){
			buildParseStack = new MyLexicalAnalyzer(MyLexicalAnalyzer.completeFile);
			created = true;
		} else{
			buildParseStack.getNextToken(MyLexicalAnalyzer.completeFile);
		}
		}
	}

	public void addToParseStack(){
		if(primer != 1){
			tokenStack.push(MyLexicalAnalyzer.tokenBin);
			MyLexicalAnalyzer.tokenBin ="";	
		}
		/*
		 * Do not save when primer == 1 or token will be saved twice.
		 * Not a good way to do this but issues with creating Class object because static/non-static issues
		 * Tried to have Lexical Analyzer prime with the first token and Syntax Analyzer ask for tokens after primed
		 * Time constraints prevent fixing this at this time
		 */
		MyLexicalAnalyzer.tokenBin ="";
		primer = 0;
	}

	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 */
	public void markdown() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCB)){
			if(tokenCounter != 0){
				System.out.println("Syntax error: #BEGIN does not start the file. Exiting conversion ");
				System.exit(1);
			}
			else{
				beginReceived = true;
				addToParseStack();
				askForToken();
				markdown();
			}
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DEFB)){
			addToParseStack();
			askForToken();
			variableDefine();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.HEAD)){
			addToParseStack();
			askForToken();
			head();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCE)){
			beginReceived = true;
			MyLexicalAnalyzer.reachedEnd = true;
			addToParseStack();
			if(beginReceived ==false){
				System.out.println("Syntax error: #BEGIN was not found in the file. Exiting conversion ");
				System.exit(1);
			}
			else{
				while(!tokenStack.isEmpty()){
					System.out.println("--------------------------------------");
					System.out.println(tokenStack.pop());
				}
				System.out.println();
				System.out.println("******");
				System.out.println("REACHED END OF SYNTAX ANALYZER");
				System.out.println("******");
			}
		}else{
			body();
		}
	}

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	public void head() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.HEAD)){
			addToParseStack();
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.TITLEB)){
			addToParseStack();
			title();

			//
			// WILL WHITESPACE MESS THIS UP? SO ^            ^ WOULD ERROR? or is that acceptable
			// add an else if(ifOnlySpaces()){ ignore and call head()}
			askForToken();
			head();
		} else{
			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException

	 */
	public void title() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.TITLEE)){
				addToParseStack();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			addToParseStack();
			askForToken();
			title();
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the body annotation.
	 * @throws CompilerException
	 */
	public void body() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.PARAB)){
			addToParseStack();
			askForToken();
			paragraph();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LISTITEMB)){
			addToParseStack();
			askForToken();
			listitem();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.NEWLINE)){
			addToParseStack();
			askForToken();
			newline();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LINKB)){
			addToParseStack();
			askForToken();
			link();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.AUDIO)){
			addToParseStack();
			askForToken();
			audio();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.VIDEO)){
			addToParseStack();
			askForToken();
			video();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.USEB)){
			addToParseStack();
			askForToken();
			variableUse();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCE)){
			markdown();	
		} else{
			addToParseStack();
			askForToken();
			markdown();
		}
	}

	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 */
	public void paragraph() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFB)){
			addToParseStack();
			askForToken();
			variableDefine();
			askForToken();
			paragraph();
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
			addToParseStack();
		} else{
			innerText();
		}			
	}

	/**
	 * This method implements the BNF grammar rule for the inner-text annotation.
	 * @throws CompilerException
	 */
	public void innerText() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				addToParseStack();
				askForToken();
				bold();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){
				addToParseStack();
				askForToken();
				italics();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEMB)){
				addToParseStack();
				askForToken();
				listitem();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.AUDIO)){
				addToParseStack();
				askForToken();
				audio();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.VIDEO)){
				addToParseStack();
				askForToken();
				video();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKB)){
				addToParseStack();
				askForToken();
				link();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.NEWLINE)){
				addToParseStack();
				askForToken();
				newline();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.USEB)){
				addToParseStack();
				askForToken();
				variableUse();
				askForToken();
				innerText();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
				paragraph();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			addToParseStack();
			askForToken();
			innerText();
		}

	}

	/**
	 * This method implements the BNF grammar rule for the variable-define annotation.
	 * @throws CompilerException
	 */
	public void variableDefine() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.EQSIGN) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			//Cannot have other tokens here
			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.EQSIGN)){
			if(hasText == false){
				//Must contain text for variable name
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);

			} else{
				addToParseStack();
				hasText = false;
				askForToken();
				variableDefine();
			}
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			if(hasText == false){
				//Must contain text for variable definition
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);

			} else{
				addToParseStack();
				hasText = false;
			}
		} else{
			addToParseStack();
			hasText = true;
			askForToken();
			address();
		}
	}

	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 */
	public void variableUse() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			//Cannot have other tokens here
			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			if(hasText == false){
				//Must contain text for variable name
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);

			} else{
				addToParseStack();
				hasText = false;
			}
		} else{
			addToParseStack();
			hasText = true;
			askForToken();
			variableUse();
		}
	}

	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 */
	public void bold() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				addToParseStack();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			addToParseStack();
			askForToken();
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
				addToParseStack();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			addToParseStack();
			askForToken();
			italics();
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the listitem annotation.
	 * @throws CompilerException
	 */
	public void listitem() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEME)){
			addToParseStack();	
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
				addToParseStack();
				askForToken();
				bold();
				askForToken();
				innerItem();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){
				addToParseStack();
				askForToken();
				italics();
				askForToken();
				innerItem();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKB)){
				addToParseStack();
				askForToken();
				link();
				askForToken();
				innerItem();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEME)){
				listitem();
			} else{
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
				System.exit(1);
			}
		} else{
			addToParseStack();
			askForToken();
			innerItem();
		}
	}

	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 */
	public void link() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			if(hasText == false){
				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion "); // **** --> NEED TO CHECK FOR IF ITS ONLY WHITE SPACE AND NO "TEXT" ??
				System.exit(1);
			} else{
				addToParseStack();
				hasText = false;
				askForToken();
				if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
					addToParseStack();
					askForToken();
					address();
				} else{
					System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
					System.exit(1);
				}
			}
		} else if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);
		} else{
			addToParseStack();
			hasText = true;
			askForToken();
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
			addToParseStack();
			askForToken();
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
			addToParseStack();
			askForToken();
			address();
		} else{

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion ");
			System.exit(1);

		}
	}

	public void address() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){

			System.out.println("Syntax error: does not follow markdown structure. Exiting conversion "); //CAN ONLY BE TEXT
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){
			if(hasText == false){

				System.out.println("Syntax error: does not follow markdown structure. Exiting conversion "); //MUST CONTAIN TEXT
				System.exit(1);

			} else{
				addToParseStack();
				hasText = false;
			}
		} else{
			addToParseStack();
			hasText = true;
			askForToken();
			address();
		}
	}


	/**
	 * This method implements the BNF grammar rule for the newline annotation.
	 * @throws CompilerException
	 */
	public void newline() throws CompilerException{

		//do you even need this?? theres nothing else to do for it
	}

	public boolean allSpaces(String c){
		for(int i = 0; i < c.length(); i++){
			if(!String.valueOf(c.charAt(i)).equals(" ")){
				return false;
			}
		}
		return true;
	}
}
