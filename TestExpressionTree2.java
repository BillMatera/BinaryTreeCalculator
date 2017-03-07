package project06;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * ExpressionTree -	A library of methods that work with the TreeNode data structure. These methods provide the following:
 * 		- Builds a binary tree (TreeNode<String>) from a postfix expression string.
 * 		- Evaluates a postfix expression stored in a binary tree and returns the integer result.
 * 		- Creates a prefix, infix and postfix expression string from a binary tree.
 * 
 * Documentation for TreeNode<E>: http://web.cse.ohio-state.edu/cse2123/currentsem/closedlabs/ClosedLab12.code/docs/osu/cse2123/TreeNode.html
 * 
 * @author Bill Matera
 * 
 */
import osu.cse2123.TreeNode;


public class TestExpressionTree2 {

	public static void main(String[] args) {
//		
//		String expr1 = "30 10 9 + % 50 * 2 - 80 /";
		String expr1 = "30 10 9 % + 50 * 2 - 80 / 7 8 + * 200 + 1 2 3 + - /";
//		String expr1 = "5 10 15 - * 7 +";     //postfix expression working
//		String expr1 = "5 10 *";     //postfix expression working
//		String expr1 = "5 10 * 7 +";          //postfix expression working
//		String expr1 = "10 15 - * 7 +";       //postfix expression invalid. This will raise exception in try catch block
//		String expr1 = "5 10 15 - * 7 + 20";  //postfix expression invalid. Stack not empty on last pop for result
//		String expr1 = "5 10 A - * 7 +";      //postfix expression invalid. invalid element "A" in expression	
		TreeNode<String> expr = buildTreeFromString(expr1);
		if (expr != null) {
			System.out.println(buildTreeFromString(expr1));
			System.out.println("postfix: " + toPostfixString(expr));  //converts TreeNode expression to postfix string
			System.out.println("prefix: " + toPrefixString(expr));    //converts TreeNode expression to prefix string
			System.out.println("infix: " + toInfixString(expr));      //converts TreeNode expression to infix string
			System.out.println("result = " + evaluate(expr));
		} else
			System.out.println("Error, invalid postfix expression");
	}
	
/**
 * evaluate
 * 		Evaluates a postfix expression stored in a binary tree and returns the integer result.
 * 
 * @param expr - The postfix expression store in TreeNode<String>
 * @return - An integer of the postfix expression result.
 * 
 */ 
	public static int evaluate(TreeNode<String> expr) {
		int left, right, result = 0;
		char operator;
		
		String nodeData = expr.getData();
		
		if (nodeData.matches("[-+*/%]")) {
			left = evaluate(expr.getLeftChild());    
			right = evaluate(expr.getRightChild());
			
			operator = nodeData.charAt(0);
			switch ( operator )
	         {
	            case '-':  result = left - right;  break;
	            case '*':  result = left * right;  break;
	            case '/':  result = left / right;  break;
	            case '+':  result = left + right;  break;
	            case '%':  result = left % right;  break;
	         }
			
			return result;
		}
		
		if (nodeData.matches("\\d+")) {
			return Integer.parseInt(nodeData);
		}
		
		return result;

	}
	
	/**
	 * buildTreeFromString
	 * 		Creates a binary tree (TreeNode<String>) from a postfix expression string. Non-recursive algorithm using a stack data structure.
	 * 		Returns null if invalid expression.
	 * 
	 * @param expr - A postfix expression string
	 * @return - A TreeNode<String> containing the postfix expression 
	 */
	public static TreeNode<String> buildTreeFromString(String expr) {
		
		String[] exprArr = expr.split("\\s+");                                //Parse expr into an array of Strings
		
		Stack<TreeNode<String>> exprStack = new Stack<TreeNode<String>>();    //Stack of TreeNode<String>
		for (int i=0; i<exprArr.length; i++) {
			if (exprArr[i].matches("[-+*/%]")) {                              //if expr element is an operator
				TreeNode<String> newNode = new TreeNode<String>(exprArr[i]);  //create a new TreeNode for operator
				try {														  //if two elements do not exist, then expression not properly formatted
				newNode.setRightChild(exprStack.pop());                       //pop first element (operand) off stack and set as right child
				newNode.setLeftChild(exprStack.pop());                        //pop next element (operand) off stack and set as left child
				} catch (EmptyStackException e) {                             //pop will throw EmptystackException
					return null;
				}
				exprStack.push(newNode);                                      //push new node onto the stack
			} else if (exprArr[i].matches("\\d+")) {                          //if expr element is one or more digits
				TreeNode<String> newNode = new TreeNode<String>(exprArr[i]);  //create a new TreeNode for operand
				exprStack.push(newNode);                                      //push operand onto the stack
			} else return null;                                               //invalid element in postfix string
		}
		TreeNode<String> result = exprStack.pop();
		if (!exprStack.isEmpty()) {     //if stack is not empty then expression not properly formatted
			return null;
		}
		return result;                 //only root of new expression should be on the stack
	}
	
	/**
	 * toPostfixString
	 *   Creates a postfix expression string from a TreeNode data structure.

	 * @param expr - The root node of the tree you want to traverse - if this is null, the method returns an empty String
	 * @return A String containing the postfix expression of the TreeNode data structure.
	 */
	public static String toPostfixString(TreeNode<String> expr) {
		String result="";
		
		if (expr == null) 
			return result;
		
		if (expr.getLeftChild() != null)
			result += toPostfixString(expr.getLeftChild()) + " ";
		if (expr.getRightChild() != null) 
			result += toPostfixString(expr.getRightChild()) + " ";
		
		result = result + expr.getData().toString();
		
		return result;
	}

	/**
	 * toPrefixString
	 * 		Creates a prefix expression string from a TreeNode data structure.
	 *
	 * @param expr the root node of the tree you want to traverse - if this is null, the method returns an empty String
	 * @return a String containing the prefix expression of the TreeNode data structure.
	 */
	public static String toPrefixString(TreeNode<String> expr) {
		String result="";
		
		if (expr == null) 
			return result;
		
		result = result + expr.getData().toString();
		if (expr.getLeftChild() != null)
			result += " " + toPrefixString(expr.getLeftChild());
		if (expr.getRightChild() != null)
			result += " " + toPrefixString(expr.getRightChild());
		
		return result;
	}
	
	/**
	 * toInfixString
	 * 		Creates an infix expression string from a TreeNode data structure.
	 * 
	 * @param expr the root node of the tree you want to traverse - if this is null, the method returns an empty String
	 * @return a String containing the infix expression of the TreeNode data structure.
	 *   
	 */
	public static String toInfixString(TreeNode<String> expr) {
		String result="";
		
		if (expr == null) 
			return result;
		
		if (expr.getLeftChild() != null) {
			result += "(";
			result += toInfixString(expr.getLeftChild()) + " ";  //need to append result with previous stack frame result (the recursive result)
		} 
		
		result += expr.getData().toString();
		
		if (expr.getRightChild() != null) {
			result += " " + toInfixString(expr.getRightChild()) + ")";  //need to append result with previous stack frame result (the recursive result)
		}
		
		return result;
	}
}
