package edu.towson.cis.cosc455.ngarrison.project1.implementation;

import java.util.Stack;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MySemanticAnalyzer {
	public MySemanticAnalyzer(){
		variableCheck();
		convertToHTML();
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
					MySyntaxAnalyzer.tokenStack.push(CURRENTHOLDER);
					varName ="";
					removeLowerBound();
				} else if(temp.equals(Tokens.USEB)){
					canResolve(varName);
					searchForDefine();
				}
			} else{
				outputStack.push(temp);
			}	
		}
	}

	public void variableReplace(){
		while(!temp.equals(LOWERPLACEHOLDER)){
			if(temp.equals(IGNOREBLOCK)){
				temp = outputStack.pop();
				ignoreBlockReplace();
			}  else if(temp.equals(Tokens.USEB)){
				MySyntaxAnalyzer.tokenStack.push(temp);
				temp = outputStack.pop();
				if(ignoreSpaces(temp).equals(ignoreSpaces(varName))){
					//do nothing with temp to drop the variable name
					//pop once off this stack to drop the $USE
					MySyntaxAnalyzer.tokenStack.pop();

					//insert the corresponding variable value
					MySyntaxAnalyzer.tokenStack.push(variableDef);

					//ignore the $END
					outputStack.pop();

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
			System.out.println("Checking temp --" + temp + "-- against varName --" + varName + "--");
			if(temp.equals(Tokens.PARAE)){
				ignoreBlockSearch();				
			} else if(temp.equals(Tokens.EQSIGN)){
				/*still need to store $DEF name = value $END in case variables are used in a (paragraph) block
				 *but there is no definition at the beginning of the (paragraph) block
				 *will have to ignore when converting to html
				 */
				temp = MySyntaxAnalyzer.tokenStack.pop();
				if(ignoreSpaces(temp).equals(ignoreSpaces(varName))){
					MySyntaxAnalyzer.tokenStack.push(temp);
					//stores equals sign
					temp = outputStack.pop();
					MySyntaxAnalyzer.tokenStack.push(temp);
					//gets the variable definition
					temp = outputStack.pop();
					MySyntaxAnalyzer.tokenStack.push(temp);
					variableDef = temp;
					//stores $END
					temp = outputStack.pop();
					MySyntaxAnalyzer.tokenStack.push(temp);
					temp = outputStack.pop();
					System.out.println("DO YOU GET HERE ALSO for var name " + varName);
					variableReplace();
					variableCheck();
					break;
				} else{
					outputStack.push(temp);
				}
			} else if(temp.equals(Tokens.DOCB)){
				System.out.println("ERROR HERE varName");
				System.out.println("Semantic error: " + varName + " is undefined.  Exiting conversion process.");
				System.exit(1);
			}
		}
	}

	public void ignoreBlockSearch(){
		//Need to pop once, otherwise } was being stored twice
		outputStack.pop();
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
	
	public boolean canResolve(String variableName){
		temp = variableName.trim();
		for(int i = 0; i < temp.length(); i++){
			if(String.valueOf(temp.charAt(i)).equals(" ")){
				System.out.println("Semantic error: Unable to resolve variable name \"" + temp + "\".  Exiting compiler");
				System.exit(1);
				return false;
			}
		}
		return true;
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
			
			BufferedWriter output = null;
	        try {
	            File file = new File("outputFile.html");
	            output = new BufferedWriter(new FileWriter(file));
	            output.write(outputString);
	            openHTMLFileInBrowswer("outputFile.html");
	        } catch ( IOException e ) {
	            e.printStackTrace();
	        } finally {
	            if ( output != null )
					try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
			
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
	void openHTMLFileInBrowswer(String htmlFileStr){
		File file= new File(htmlFileStr.trim());
		if(!file.exists()){
			System.err.println("File "+ htmlFileStr +" does not exist.");
			return;
		}
		try{
			Desktop.getDesktop().browse(file.toURI());
		}
		catch(IOException ioe){
			System.err.println("Failed to open file");
			ioe.printStackTrace();
		}
		return ;
	}

}
