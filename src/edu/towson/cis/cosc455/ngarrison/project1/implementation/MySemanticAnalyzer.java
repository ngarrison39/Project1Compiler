package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;

public class MySemanticAnalyzer {
	public MySemanticAnalyzer(){
		variableCheck();
	}

	public Stack<String> outputStack = new Stack<String>();

	String temp;
	String varName;
	String variableDef;
	String outputString;
	/* Used when mapping to html for begin/end tags that are the same: *, **, ^ */
	boolean closeHead = false;
	boolean closeItalics = false;
	boolean closeBold = false;

	final String UPPERPLACEHOLDER = "~~*UPPER*~~";
	final String LOWERPLACEHOLDER = "~~*LOWER*~~";
	final String CURRENTHOLDER = "~~*CURRENT*~~";
	final String IGNOREBLOCK = "~~*IGNORE*~~";

	/*
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
	 */


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
				if(temp.equals(Tokens.EQSIGN)){
					System.out.println("Ignoring the equal sign and reset");
					MySyntaxAnalyzer.tokenStack.push(CURRENTHOLDER);
					varName ="";
					removeLowerBound();
					//break???
					variableCheck();
				} else if(temp.equals(Tokens.USEB)){

					System.out.println("Sending to searchForDefine with var name =" + varName);

					searchForDefine();
				}
			} else{
				outputStack.push(temp);
			}	
		}
		
	/*	
		
		System.out.println();
		System.out.println();

		while(!outputStack.isEmpty()){
			System.out.println(outputStack.pop());
			System.out.println("------------------------");
			
	*/	
		convertToHTML();
		}

	public void variableReplace(){
		while(!temp.equals(LOWERPLACEHOLDER)){

			System.out.println("At top of replace method token is -" + temp + "-");

			if(temp.equals(IGNOREBLOCK)){
				temp = outputStack.pop();
				ignoreBlockReplace();
			} else if(temp.equals(Tokens.DOCE)){
				MySyntaxAnalyzer.tokenStack.push(temp);
				break;
			} else if(temp.equals(Tokens.USEB)){
				MySyntaxAnalyzer.tokenStack.push(temp);
				temp = outputStack.pop();
				if(ignoreSpaces(temp).equals(ignoreSpaces(varName))){
					//do nothing with temp to drop the variable name
					//pop once off this stack to drop the $USE
					MySyntaxAnalyzer.tokenStack.pop();

					//insert the corresponding variable value
					MySyntaxAnalyzer.tokenStack.push(variableDef);

					//ignore the $END
					System.out.println("Should ignore $END ----" + outputStack.pop());

					temp = outputStack.pop();
				} else{
					MySyntaxAnalyzer.tokenStack.push(temp);
					temp = outputStack.pop();
				}

			} else{
				MySyntaxAnalyzer.tokenStack.push(temp);
				temp = outputStack.pop();
			}
		}

	}

	public void searchForDefine(){
		while(!MySyntaxAnalyzer.tokenStack.isEmpty()){
			temp = MySyntaxAnalyzer.tokenStack.pop();
			outputStack.push(temp);

			System.out.println(" From Syntax Stack----" + temp + "----");

			if(temp.equals(Tokens.PARAE)){

				System.out.println("Went to ignore block");

				ignoreBlockSearch();
			} else if(temp.equals(Tokens.EQSIGN)){

				System.out.println("Found in Syntax Stack the variable definition after   " + temp);
				System.out.println("variable name right now is -" + varName + "-");

				//MySyntaxAnalyzer.tokenStack.push(CURRENTHOLDER);

				/*still need to store $DEF name = value $END in case variables are used in a (paragraph) block
				 *but there is no definition at the beginning of the (paragraph) block
				 *will have to ignore when converting to html
				 */
				temp = MySyntaxAnalyzer.tokenStack.pop();

				System.out.println("from syntax analyzer temp should be varName -" + temp + "-");


				//MySyntaxAnalyzer.tokenStack.push(temp);
				//temp = outputStack.pop();

				if(ignoreSpaces(temp).equals(ignoreSpaces(varName))){

					System.out.println("The variable names matched   " + temp);

					MySyntaxAnalyzer.tokenStack.push(temp);

					//stores equals sign
					temp = outputStack.pop();
					MySyntaxAnalyzer.tokenStack.push(temp);

					System.out.println("The temp should have been =  -" + temp + "-");

					//gets the variable definition
					temp = outputStack.pop();
					MySyntaxAnalyzer.tokenStack.push(temp);
					variableDef = temp;

					//stores $END
					temp = outputStack.pop();
					MySyntaxAnalyzer.tokenStack.push(temp);

					System.out.println("Did it store the $END????  :" + temp);


					temp = outputStack.pop();

					System.out.println("temp before replace method  -" + temp + "-");

					variableReplace();
					break;
				}
			} else if(temp.equals(Tokens.DOCB)){
				System.out.println("Semantic error: " + varName + " is undefined.  Exiting conversion process.");
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
				break;
			} else{
				MySyntaxAnalyzer.tokenStack.push(temp);
			}
		}
	}

	public String ignoreSpaces(String name){
		String noSpace = "";
		for(int i = 0; i < name.length(); i++){
			if(!String.valueOf(name.charAt(i)).equals(" ")){
				noSpace = noSpace + String.valueOf(name.charAt(i));
			}
		}
		return noSpace;
	}
	public void convertToHTML(){
		if(!outputStack.isEmpty()){
			temp = outputStack.pop();
		}

		switch (temp) {
		case Tokens.DOCB:
			outputString = "<html>";
			convertToHTML();
			break;
		case Tokens.DOCE:
			outputString = outputString + "</html>";
			System.out.println(outputString);
			break;
		case Tokens.DEFB:
			//do nothing to drop $DEF
			//pop to drop variable name
			outputStack.pop();
			//pop to drop equals sign
			outputStack.pop();
			//pop to drop variable value
			outputStack.pop();
			//pop to drop $END
			outputStack.pop();	
			convertToHTML();	
			break;
		case Tokens.HEAD:
			if(closeHead == false){
				outputString = outputString + "<head>";
				temp = outputStack.pop();
				closeHead = true;
				convertToHTML();
			} else if(closeHead == true){
				outputString = outputString + "</head>";
				closeHead = false;
				convertToHTML();
			}	
		case Tokens.ITALICS: 
			if(closeItalics == false){
				outputString = outputString + "<i>";
				temp = outputStack.pop();
				closeItalics = true;
				convertToHTML();
			} else if(closeItalics == true){
				outputString = outputString + "</i>";
				closeItalics = false;
				convertToHTML();
			}
			break;
		case Tokens.BOLD: 
			if(closeBold == false){
				outputString = outputString + "<b>";
				temp = outputStack.pop();
				closeBold = true;
				convertToHTML();
			} else if(closeBold == true){
				outputString = outputString + "</b>";
				closeBold = false;
				convertToHTML();
			}
			break;

		case Tokens.TITLEB:  
			outputString = outputString + "<title>";
			convertToHTML();
			break;
		case Tokens.TITLEE:  
			outputString = outputString + "</title>";
			convertToHTML();
			break;
		case Tokens.PARAB:  
			outputString = outputString + "<p>";
			convertToHTML();
			break;
		case Tokens.PARAE:  
			outputString = outputString + "</p>";
			convertToHTML();
			break;
		case Tokens.LISTITEMB: 
			outputString = outputString + "<li>";
			convertToHTML();
			break;
		case Tokens.LISTITEME: 
			outputString = outputString + "</li>";
			convertToHTML();
			break;
		case Tokens.NEWLINE: 
			outputString = outputString + "<br>";
			convertToHTML();
			break;
		case Tokens.LINKB: 
			String description;
			String addressL;
			//Set beginning of link from [
			outputString = outputString + "<a href=\"";
			//Save the description for later in link format
			description = outputStack.pop();
			//Ignore the closing ]
			outputStack.pop();
			//Ignore the opening (
			outputStack.pop();
			addressL = outputStack.pop();
			//Ignore the closing )
			outputStack.pop();
			outputString = outputString + addressL + "\">" + description + "</a>";
			convertToHTML();
			break;
		case Tokens.AUDIO:
			String addressA;
			//Set beginning of link from @
			outputString = outputString + "<audio controls> <source src=\"";
			//Ignore the opening (
			outputStack.pop();
			//Save the address
			addressA = outputStack.pop();
			//Ignore the closing )
			outputStack.pop();
			outputString = outputString + addressA + "\"> </audio>";
			convertToHTML();
			break;
		case Tokens.VIDEO:
			String addressV;
			//Set beginning of link from %
			outputString = outputString + "<iframe src=\"";
			//Ignore the opening (
			outputStack.pop();
			//Save the address
			addressV = outputStack.pop();
			//Ignore the closing )
			outputStack.pop();
			outputString = outputString + addressV + "\"/>";
			convertToHTML();
			break;
		default: 
			outputString = outputString + temp;
			convertToHTML();
			break;
		}
	}

}
