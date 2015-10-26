package edu.towson.cis.cosc455.ngarrison.project1.implementation;

public class MyCompiler {
	public static void main(){
		String allFile = "";
		
		//create filescanner
		//he suggested taking entire file as one line the going through and taking tokens
		//would it be better to jsut do it line by line from file? (would have to keep calling for next line
	}
	
	public void markdown() throws CompilerException{
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){ //create a Token class below
		
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
	}

}
