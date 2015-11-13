package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer{
	public static Stack<String> tokenStack = new Stack<String>();
	
	public MyLexicalAnalyzer buildParseStack;
	public boolean created = false;
	/* will be used to make sure the definition is only at the beginning of a paragraph block */
	boolean allowParDef = false;
	/* will be used to make sure the definition is only directly after the #BEGIN token */
	boolean allowBeginDef = false;
	/* used to check position of $DEF in terms of #BEGIN or PARAB */
	int defPositinon;
	
	int tokenCount = 0;

	/* Used to check if required text is present*/
	boolean hasText = false;
	/* Used to check if #BEGIN is first token in file */
	int tokenCounter = 0;
	/* Used to check if #BEGIN is used in file */
	boolean beginReceived = false;
	/* Used to check if #END used in file*/
	boolean endReceived = false;
	
	public int primer = 0;
	
	String temp = "";
    public static Stack<String> tempStack = new Stack<String>();
	
	

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
			if(!allSpaces(MyLexicalAnalyzer.tokenBin)){
			tokenCounter++;
		}
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
				System.out.println("Syntax error: #BEGIN must be at start of document. Exiting conversion ");
				System.exit(1);
			}
			else{
				beginReceived = true;
				addToParseStack();
				allowBeginDef = true;
				askForToken();
				markdown();
			}
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DEFB)){
			if(allowBeginDef != true){
				System.out.println("Syntax error: $DEF must be directly after #BEGIN. Exiting conversion ");
				System.exit(1);
			}
			addToParseStack();
			askForToken();
			variableDefine();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.HEAD)){
			allowBeginDef = false;
			addToParseStack();
			askForToken();
			head();
			askForToken();
			markdown();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCE)){
			endReceived = true;
			addToParseStack();
			while(MyLexicalAnalyzer.reachedEnd != true){
			askForToken();
			if(!allSpaces(MyLexicalAnalyzer.tokenBin)){
				System.out.println("Syntax error: annotations were found after #END annotation. Exiting conversion ");
				System.exit(1);
			} 
			
			
			
			else{
				while(!tokenStack.isEmpty()){
										temp = tokenStack.pop();
										System.out.println("|" + temp + "|");
										System.out.println("----------");
										tempStack.push(temp);
									}
									while(!tempStack.isEmpty()){
										temp = tempStack.pop();
										tokenStack.push(temp);
									}
			}
			
			
			
			
			}
				if(beginReceived == false){
				System.out.println("Syntax error: #BEGIN was not found in the file. Exiting conversion ");
				System.exit(1);
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
			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
	if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
		if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.PARAB)){
			addToParseStack();
			allowParDef = true;
			askForToken();
			paragraph();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LISTITEMB)){
			addToParseStack();
			askForToken();
			listitem();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.NEWLINE)){
			addToParseStack();
			askForToken();
			newline();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.LINKB)){
			addToParseStack();
			askForToken();
			link();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.AUDIO)){
			addToParseStack();
			askForToken();
			audio();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.VIDEO)){
			addToParseStack();
			askForToken();
			video();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.USEB)){
			addToParseStack();
			askForToken();
			variableUse();
			askForToken();
			body();
		} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCE)){
			markdown();	
		} else{
			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);
		}
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
			if(allowParDef != true){
				System.out.println("Syntax error: $DEF must be directly after #BEGIN. Exiting conversion ");
				System.exit(1);
			}
			addToParseStack();
			askForToken();
			variableDefine();
			askForToken();
			paragraph();
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.PARAE)){
			addToParseStack();
		} else if(allSpaces(MyLexicalAnalyzer.tokenBin)){ 
			addToParseStack();
			askForToken();
			paragraph();
		} else{
			allowParDef = false;
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
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.EQSIGN)){
			if(hasText == false){
				//Must contain text for variable name
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
				System.exit(1);

			} else{
				addToParseStack();
				hasText = false;
			}
		} else{
			addToParseStack();
			hasText = true;
			askForToken();
			variableDefine();
		}
	}

	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 */
	public void variableUse() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			//Cannot have other tokens here
			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			if(hasText == false){
				//Must contain text for variable name
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
			}  else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.USEB)){
				addToParseStack();
				askForToken();
				variableUse();
				askForToken();
				innerItem();
			} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LISTITEME)){
				listitem();
			} else{
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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
				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion "); // **** --> NEED TO CHECK FOR IF ITS ONLY WHITE SPACE AND NO "TEXT" ??
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
					System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
					System.exit(1);
				}
			}
		} else if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
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

			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			addToParseStack();
			askForToken();
			address();
		} else{

			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);

		}
	}

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	public void video() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){

			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			addToParseStack();
			askForToken();
			address();
		} else{

			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion ");
			System.exit(1);

		}
	}

	public void address() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){

			System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion "); //CAN ONLY BE TEXT
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){
			if(hasText == false){

				System.out.println("Syntax error token #" + tokenCounter + " : " + MyLexicalAnalyzer.tokenBin + "  does not follow markdown structure at this position. Exiting conversion "); //MUST CONTAIN TEXT
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
