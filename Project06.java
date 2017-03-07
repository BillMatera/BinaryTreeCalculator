package project06;

import java.util.Scanner;
import osu.cse2123.TreeNode;
import static project06.ExpressionTree.*;  //this will import static methods

/**
 *           - A postfix calculator for simple algebraic operations involving (+, -, *, /, %).
 *             Once a postfix expression has been entered, the calculator will display the 
 *             the expression and result. The display format can be changed to prefix, infix or
 *             postfix, which will show the expression in the requested format along with the 
 *             result. 
 * 
 * @author Bill Matera
 *
 */
public class Project06 {

	public static void main(String[] args) {
		Scanner consoleInput = new Scanner(System.in);   	//Scanner for getting console input.
		boolean promptUser = true;
		boolean expressionEntered = false;
		TreeNode<String> expr = null;                      //the expression in TreeNode data structure
		
		System.out.println("No expression in memory\n");
		
		//display menu and prompt for choice
		do {
			char choice = menuChoice(consoleInput);
			switch (choice) {
			case 'S': // set display format
				if (!(expressionEntered)) {
					System.out.println("No expression in memory. Expression must be entered before setting display format\n");
					break;
				}
				char displayFormat = setDisplayFormat(consoleInput);
				switch (displayFormat) {
				case 'P':  //display postfix format
					System.out.print(toPostfixString(expr) + " = ");
					break;
				case 'I': //display infix format
					System.out.print(toInfixString(expr) + " = ");
					break;
				case 'R': //display prefix format
					System.out.print(toPrefixString(expr) + " = ");	
					break;
				}
				System.out.println(evaluate(expr));
				System.out.println();
				break;
			case 'E': // enter a new expression
				expr = enterNewExpression(consoleInput);
				expressionEntered = true;
				break;
			case 'Q': // quit
				promptUser = false;
				break;
			default:
				System.out.println("ERROR! You must enter on of [E], [S] or [Q]!\n");
				break;
			}
		} while (promptUser);
		
		System.out.println("Goodbye!");
		consoleInput.close();		
	}

	/**
	 * menuChoice - Displays first menu and reads in user selection.
	 * 
	 * @param consoleInput - scanner for console input.
	 * @return - user selection.
	 */
	private static char menuChoice(Scanner consoleInput) {
		System.out.println("Enter your choice:");
		System.out.println("[S]et the display format");
		System.out.println("[E]nter a new expression");
		System.out.println("[Q]uit");
		System.out.print("> ");
		char choice = consoleInput.next().toUpperCase().charAt(0);  //converts string to char and sets to uppercase.
		System.out.println();
		return choice;
	}
	
	/**
	 * setDisplayFormat - Displays set format menu and reads in user selection.
	 * 
	 * @param consoleInput - scanner for console input.
	 * @return - user selection
	 */
	private static char setDisplayFormat(Scanner consoleInput) {
		boolean validInput;
		char choice = 0;
		
		do {
			validInput = true;
			System.out.println("Enter your preferred output display:");
			System.out.println("[P]ostfix");
			System.out.println("[I]nfix");
			System.out.println("P[R]efix");
			System.out.print("> ");
			choice = consoleInput.next().toUpperCase().charAt(0);    //converts string to char and sets to upper case.
			if (!(choice == 'P' || choice == 'I' || choice == 'R'))  {
				validInput = false;
				System.out.println("ERROR! You must enter [P], [I] or [R]!\n");
			}
		} while (!validInput);
		return choice;
	}
	
	/**
	 * enterNewExpression - prompts to enter new expression. If expression is invalid return error message,
	 * 						else printout expression and result.
	 * 		
	 * @param consoleInput - scanner for console input.
	 * @return - expression in TreeNode<String> data structure.
	 */
	private static TreeNode<String> enterNewExpression(Scanner consoleInput) {
		consoleInput.nextLine();  //console input was skipping over the read below, this fixed it.
		boolean invalidExpression = true;
		TreeNode<String> expr;
		
		do {
			System.out.print("Enter your expression in postfix notation: ");
			String userExpression = consoleInput.nextLine();
			expr = buildTreeFromString(userExpression);  //create the TreeNode expression
			if (expr == null) {                          //invalid expression
				System.out.println("\nERROR! Expression not in postfix notation!\n");
			} else {
			System.out.print(userExpression + " = ");    //print postfix expression
			System.out.println(evaluate(expr));          //evaluate and print the result
			System.out.println();
			invalidExpression = false;
			}
		} while (invalidExpression);
		return expr;
	}
}
