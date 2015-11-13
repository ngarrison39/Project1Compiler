package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import edu.towson.cis.cosc455.ngarrison.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer{
	/* Stack used to act as the parse tree*/
	public static Stack<String> tokenStack = new Stack<String>();

	/* Used this object to access the getNextToken method in MyLexicalAnalyzer
	 * This was a quick fix for the static/non-static conflictions
	 * A better solution was not created under time restraints
	 */
	public MyLexicalAnalyzer buildParseStack;
	/* Used to prevent creation of a new MyLexicalAnalyzer object each time the askForToken method is called */
	public boolean created = false;
	/* will be used to make sure the definition is only at the beginning of a paragraph block */
	boolean allowParDef = false;
	/* will be used to make sure the definition is only directly after the #BEGIN token */
	boolean allowBeginDef = false;
	/* used to check position of $DEF in terms of #BEGIN or PARAB */
	int defPositinon;
	/* Tracks number of tokens stored to ensure #BEGIN is first */
	int tokenCount = 0;
	/* Used to check if required text is present*/
	boolean hasText = false;
	/* Used to check if #BEGIN is first token in file */
	int tokenCounter = 0;
	/* Used to check if #BEGIN is used in file */
	boolean beginReceived = false;
	/* Used to check if #END used in file*/
	boolean endReceived = false;
	/* A quick fix for preventing #BEGIN from being stored twice because of the arrangement of methods */
	public int primer = 0;

	/* This was a quick fix because of static/non-static issues 
	 * Because of time constraints this fix accepted as viable to have a working program
	 */
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
				System.out.println("Syntax error: #END was not found in the file. Exiting compiler.");
				System.exit(1);
			}
		} else{
			/* Refer to declarations at top of file for descriptions*/
			if(created == false){
				buildParseStack = new MyLexicalAnalyzer(MyLexicalAnalyzer.completeFile);
				created = true;
			} else{
				buildParseStack.getNextToken(MyLexicalAnalyzer.completeFile);
			}
		}
	}
	/*  */
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
	/* Custom "trim" method to ignore spaces were necessary */
	public boolean allSpaces(String c){
		for(int i = 0; i < c.length(); i++){
			if(!String.valueOf(c.charAt(i)).equals(" ")){
				return false;
			}
		}
		return true;
	}

	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 */
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void markdown() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.DOCB)){
			if(tokenCounter != 0){
				System.out.println("Syntax error: #BEGIN must be at start of document. Exiting compiler.");
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
				System.out.println("Syntax error: $DEF must be directly after #BEGIN. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void head() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.HEAD)){
			addToParseStack();
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.TITLEB)){
			addToParseStack();
			title();
			askForToken();
			head();
		} else if(allSpaces(MyLexicalAnalyzer.tokenBin)){
			addToParseStack();
			askForToken();
			head();
		} else{
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in header. Exiting compiler.");
			System.exit(1);
		}		
	}

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException
	 */
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void title() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.TITLEE)){
				addToParseStack();
			} else{
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("is not allowed in title. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
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
			} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.ITALICS)){
				addToParseStack();
				askForToken();
				italics();
				askForToken();
				body();
			} else if(MyLexicalAnalyzer.tokenBin.equalsIgnoreCase(Tokens.BOLD)){
				addToParseStack();
				askForToken();
				bold();
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
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("is not allowed in body structure. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void paragraph() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFB)){
			if(allowParDef != true){
				System.out.println("Syntax error: $DEF must be directly after paragraph begin tag. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
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
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("is not allowed in paragraph structure. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void variableDefine() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.EQSIGN) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			//Cannot have other tokens here
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in the variable definition. Exiting compiler.");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.EQSIGN)){
			if(hasText == false){
				//Must contain text for variable name
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("no variable name present. Exiting compiler.");
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
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("no value present for variable definition. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void variableUse() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			//Cannot have other tokens here
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in the variable use. Exiting compiler.");
			System.exit(1);

		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.DEFUSEE)){
			if(hasText == false){
				//Must contain text for variable name
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("no variable name present for use. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void bold() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.BOLD)){
				addToParseStack();
			} else{
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("is not allowed in bold structure. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void italics() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin)){
			if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ITALICS)){
				addToParseStack();
			} else{
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("is not allowed in italics structure. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
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
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("is not allowed in list structure. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void link() throws CompilerException{
		if(MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			if(hasText == false){
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("link structure requires a description. Exiting compiler.");
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
					System.out.println("Syntax error on token:");
					System.out.println(MyLexicalAnalyzer.tokenBin);
					System.out.println("is not allowed in web address structure. Exiting compiler.");
					System.exit(1);
				}
			}
		} else if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.LINKE)){
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in link structure. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void audio() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in audio link structure. Exiting compiler.");
			System.exit(1);
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			addToParseStack();
			askForToken();
			address();
		} else{
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in audio link structure. Exiting compiler.");
			System.exit(1);

		}
	}

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void video() throws CompilerException{
		if(Tokens.isToken(MyLexicalAnalyzer.tokenBin) && !MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in video link structure. Exiting compiler.");
			System.exit(1);
		} else if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSB)){
			addToParseStack();
			askForToken();
			address();
		} else{
			System.out.println("Syntax error on token:");
			System.out.println(MyLexicalAnalyzer.tokenBin);
			System.out.println("is not allowed in video link structure. Exiting compiler.");
			System.exit(1);

		}
	}

	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void address() throws CompilerException{
		 if(MyLexicalAnalyzer.tokenBin.equals(Tokens.ADDRESSE)){
			if(hasText == false){
				System.out.println("Syntax error on token:");
				System.out.println(MyLexicalAnalyzer.tokenBin);
				System.out.println("an URL is required. Exiting compiler.");
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
	/* Implementation of the CompilerException was overlooked 
	 * Should have implemented throw statements rather than System.exit(1)
	 */
	public void newline() throws CompilerException{

		/* Nothing to handle here */ 
	}
}
