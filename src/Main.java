import java.io.*;
import java.util.Stack;


public class Main {
	static Double calculateRPN(String arg0){ // Main method 
		String tmp = "", fstr = ""; // temporary variables
		Stack<Double> x = new Stack<Double>(); // Stack for numbers
		Double tmp1 = (double) 0, tmp2 = (double) 0; // variables for getting arguments from the stack
		char ctmp0 = 0; // last character
		for(int i = 0;i < arg0.length();i++){ // main loop of the parser
			if((arg0.charAt(i) >= '0') && (arg0.charAt(i) <= '9')){ // check if the current character a digit
				tmp = tmp + arg0.charAt(i); // add the digit to the temporary string
				ctmp0 = arg0.charAt(i); // set the last character to this digit
			}else if(arg0.charAt(i) == '.'){ // check if the current character is a dot
				tmp += arg0.charAt(i); // add the dot to the temporary string
				ctmp0 = arg0.charAt(i); // set the last character to this dot
			}else if(arg0.charAt(i) == ' '){ // check if the current character is a space
				if((ctmp0 >= '0') && (ctmp0 <= '9')){ // check if last character was a digit (it's used to parse the multi-character digits)
					x.add(Double.parseDouble(tmp)); // flush content of the temporary string to the Stack as a double value
					tmp = ""; // reset the temporary string for parsing next multi-character numbers
				}
				ctmp0 = ' '; // set the last character to this space
			}else if(arg0.charAt(i) == '+'){ // check if the current character is the + operator
				tmp2 = x.pop(); // pop the value from the numbers' stack to the temporary variable
				tmp1 = x.pop(); // same
				x.add(tmp1 + tmp2); // add the result of expression b + a to the numbers' stack
				ctmp0 = arg0.charAt(i); // set the last character to this operator
			}else if(arg0.charAt(i) == '-'){ // check if the current character is the - operator
				tmp2 = x.pop(); // pop the value from the numbers' stack to the temporary variable
				tmp1 = x.pop(); // same
				x.add(tmp1 - tmp2); // add the result of expression b +- a to the numbers' stack
				ctmp0 = arg0.charAt(i); // set the last character to this operator
			}else if(arg0.charAt(i) == '*'){ // check if the current character is the * operator
				tmp2 = x.pop(); // pop the value from the numbers' stack to the temporary variable
				tmp1 = x.pop(); // same
				x.add(tmp1 * tmp2); // add the result of expression b * a to the numbers' stack
				ctmp0 = arg0.charAt(i); // set the last character to this operator
			}else if(arg0.charAt(i) == '/'){ // check if the current character is the / operator
				tmp2 = x.pop(); // pop the value from the numbers' stack to the temporary variable
				tmp1 = x.pop(); // same
				x.add(tmp1 / tmp2); // add the result of expression b / a to the numbers' stack
				ctmp0 = arg0.charAt(i); // set the last character to this operator
			}else if(arg0.charAt(i) == '%'){ // check if the current character is the % operator
				tmp2 = x.pop(); // pop the value from the numbers' stack to the temporary variable
				tmp1 = x.pop(); // same
				x.add(tmp1 % tmp2); // add the result of expression b % a (modulo) to the numbers' stack
				ctmp0 = arg0.charAt(i); // set the last character to this operator
			}else if(arg0.charAt(i) == '^'){ // check if the current character is the ^ operator
				tmp2 = x.pop(); // pop the value from the numbers' stack to the temporary variable
				tmp1 = x.pop(); // same
				x.add(Math.pow(tmp1, tmp2)); // add the result of expression b ^ a to the numbers' stack
				ctmp0 = arg0.charAt(i); // set the last character to this operator
			}else if((Character.toLowerCase(arg0.charAt(i)) >= 'a') && ((Character.toLowerCase(arg0.charAt(i)) <= 'z'))){
				// check if the current character is a letter (this means that it's start of function's name (this function does not support unknowns))
				for(int j = i;(j < arg0.length());j++){ // a loop that gets the name of current function (or math's constant)
					if(arg0.charAt(j) != ' ') // while current character isn't  space
						fstr += arg0.charAt(j); // get this character and put is into the temporary string
					else // if it's a space
						break; // break
				}
				if(fstr.equals("sin")){ // check if function/constant exists in math's constants
					tmp1 = x.pop();
					x.add(Math.sin(tmp1));
				}else if(fstr.equals("cos")){
					tmp1 = x.pop();
					x.add(Math.cos(tmp1));
				}else if(fstr.equals("pi")){
					x.add(Math.PI);
				}else if(fstr.equals("tan")){
					tmp1 = x.pop();
					x.add(Math.tan(tmp1));
				}else if(fstr.equals("ctg")){
					tmp1 = x.pop();
					x.add((Double)1.0 / Math.tan(tmp1));
				}else if(fstr.equals("sqrt")){
					tmp1 = x.pop();
					x.add(Math.sqrt(tmp1));
				}else if(fstr.equals("cbrt")){
					tmp1 = x.pop();
					x.add(Math.cbrt(tmp1));
				}
				ctmp0 = 'f';
			}
		}
		return x.pop(); // get top element of the stack and return it
	}
	private static Integer p(char arg0){ // precedence of operators
		// I don't have to explain how does the function works
		Integer x;
		switch(arg0){
		case '+':
		case '-':{
			x = 1;
			break;
		}
		case '*':
		case '/':
		case '%':{
			x = 2;
			break;
		}
		case '^':{
			x =  3;
			break;
			}
		default:
			return -1;
		}
		return x;
	}
	public static String RPN(String arg0){ // infix to postfix conversion
		String arg1; // temporary string
		if(arg0.charAt(arg0.length()-1) == ' ') // if last element is a space
			arg1 = arg0; // just copy
		else // else
			arg1 = arg0 + " "; // copy and append the space
		Stack<Character> operatorsStack = new Stack<Character>(); // operators' stack
		String expressionString = "", numberParsingString = ""; // temporary strings:
		// t - for main conversion
		// b - for parsing multi-character numbers
		char last = '\0'; // last character
		for(int i = 0;i < arg1.length();i++){ // main loop of the parser
			switch(arg1.charAt(i)){ // what is current character?
			case '(': // if it's left parentheses
				operatorsStack.push(arg1.charAt(i)); // add put it onto the stack
				break;
			case ')':{ // if it's right parentheses
					if(!"".equals(numberParsingString)){ // if there is a number in the temporary string
						expressionString += numberParsingString; // add it into the expression string
						expressionString += ' '; // add a space
						numberParsingString = ""; // clear the string for parsing numbers
					}
					while(operatorsStack.peek() != '('){ // while character on the top isn't a left parentheses
						expressionString += operatorsStack.pop(); // pop a operator from the operators' stack and append it to the expression string
						expressionString += ' '; // add a space
					}
					operatorsStack.pop(); // leave the left parentheses
					break;
				}
			case '+': // if it's a operator
			case '-': // same
			case '*': // same
			case '/': // same
			case '^':{ // same
					while(!operatorsStack.isEmpty() && (p(operatorsStack.peek()) > p(arg1.charAt(i)))){
						// until the stack isn't empty and precedence of the operator at the top of the stack is greater that precedence of the current operator
						expressionString += operatorsStack.pop(); // pop the operator from the stack and append it to the expression string
						expressionString += ' '; // add a space
					}
					operatorsStack.push(arg1.charAt(i)); // add up the current operator
					last = arg1.charAt(i); // set the last character
					break;
				}
			case ' ':{ // if it's a space (spaces are for the separation)
				expressionString += numberParsingString; // add the parsed number to the expression string
				if(Character.isDigit(last) | Character.isAlphabetic(last)) // if the last character was a digit or a unknown
					expressionString += ' '; // append the space to the expression string
				numberParsingString = ""; // reset the string for parsing multi-character numbers
				break;
			}
			default:{ // if it is a digit or a unknown (or something)
					numberParsingString += arg1.charAt(i); // add the current character to the string for parsing multi-character numbers
					last = arg1.charAt(i); // set the last character
					break;
				}
			}
		}
		while(!operatorsStack.isEmpty()){ // if any operators left on the stack
			expressionString += operatorsStack.pop(); // add them to the expression string
			expressionString += ' '; // add a space
		}
		return expressionString.replaceAll("  ", " "); // replace all double-spaces to a single one
	}
	public static void main(String[] args) throws IOException {
		String expression = "";
		Character choise = '\0';
		System.out.println("enter expression to transform:");
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);
		try {
			expression = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(RPN(expression) + "\nDo you want me to calculate it?[Y/N]");
		choise = (char) System.in.read();
		if(Character.toLowerCase(choise) == 'y'){
			System.out.println(calculateRPN(RPN(expression)));
			
		}
	}
}
