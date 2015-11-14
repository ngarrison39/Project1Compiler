# Project1Compiler
Software: For this project I used Eclipse (Mars.1) with the Subclipse installed.   

GitHub: https://github.com/ngarrison39/Project1Compiler

Run procedure: Compiles with the executable Jar file.  To run the program enter a document with a .mkd extension.
 From the command line: C:\> java -jar Project1Compiler Test2.mkd  
 To run from Eclipse IDE: if the file is within the same directory, the file name with extension is sufficient, for example Test2.mkd would work.  If the files are not located in the same directory, using the file path will work as well, for example C:\\Users\\Nick's\\workspace\\CompilerProject1\\Test2.mkd *Note* A second \ must be present otherwise the file path will be examined as escape clauses. 

Known Bugs: 
	-Not a bug with my code, I don't think, but when I print my outputString in the semantic analyzer after the markdown is converted to HTML (just to check for any errors, not included in final , a portion of it is  
	<p> <li><b>SLEEP</b></li> </p> however, looking at the code in the HTML output browser (inspect element) it is shown to be <p></p> <li><b>SLEEP</b></li> <p></p>

Possible Improvements:
	- A better solution could be used for variable resolution by reversing the order of the stack (when the syntax analyzer creates the stack the beginning of the file will be stored first so #BEGIN will be the bottom of the stack).  I believe by working top down I would be able to utilize a global and local variable to store the variable names and values.  This should create easier cases for resolution and be able to complete the task in one pass.
	- Remove booleans and counters such as  allowBeginDef, allowParDef, created, tokenCounter, etc. that make the code more complicated and confusing.  By betting structuring the methods that use them, I believe there would be no need for them at all and the cases they prevent would be prevented by the structure itself (which may already be the case, and I did not realize those situations were already handled by the structure).
	- Class objects used to initiate certain methods each of the three analyzers.  They were rushed ideas and a quick fix for static/non-static issues between the analyzers.  Objects may be a good way to do it but with a more thought out implementation.
	-I somehow managed to overlook the throws CompilerException for MySyntax analyzer and instead printed an informative message about the reason for the error and exited the program with System.exit(1).  I realize it would be much better to have thrown the CompilerException error but that cannot be dealt with at this time.
	
