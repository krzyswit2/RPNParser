import java.io.*;
import java.util.Stack;
public class Main {
	private static Integer p(char arg0){
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
		static Double calculateRPN(String arg0){
			String tmp = "", fstr = "";
			Stack<Double> x = new Stack<Double>();
			Double tmp1 = (double) 0, tmp2 = (double) 0;
			char ctmp0 = 0;
			for(int i = 0;i < arg0.length();i++){
				if((arg0.charAt(i) >= '0') && (arg0.charAt(i) <= '9')){
					tmp = tmp + arg0.charAt(i);
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == '.'){
					tmp += arg0.charAt(i);
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == ' '){
					if((ctmp0 >= '0') && (ctmp0 <= '9')){
						x.add(Double.parseDouble(tmp));
						tmp = "";
					}
					ctmp0 = ' ';
				}else if(arg0.charAt(i) == '+'){
					tmp2 = x.pop();
					if(!x.isEmpty()){
						tmp1 = x.pop();
						x.add(tmp1 + tmp2);
					}else{
						x.add(tmp2);
					}
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == '-'){
					tmp2 = x.pop();
					if(!x.isEmpty()){
						tmp1 = x.pop();
						x.add(tmp1 - tmp2);
					}else{
						x.add(-tmp2);
					}
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == '*'){
					tmp2 = x.pop();
					tmp1 = x.pop();
					x.add(tmp1 * tmp2);
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == '/'){
					tmp2 = x.pop();
					tmp1 = x.pop();
					x.add(tmp1 / tmp2);
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == '%'){
					tmp2 = x.pop();
					tmp1 = x.pop();
					x.add(tmp1 % tmp2);
					ctmp0 = arg0.charAt(i);
				}else if(arg0.charAt(i) == '^'){
					tmp2 = x.pop();
					tmp1 = x.pop();
					x.add(Math.pow(tmp1, tmp2));
				}else if((Character.toLowerCase(arg0.charAt(i)) >= 'a') && ((Character.toLowerCase(arg0.charAt(i)) <= 'z'))){
					for(int j = i;(j < arg0.length());j++){
						if(arg0.charAt(j) != ' ')
							fstr += arg0.charAt(j);
						else
							break;
					}
					if(fstr.equals("sin")){
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
			return x.pop();
		}
	public static String RPN(String arg0){
		String arg1;
		if(arg0.charAt(arg0.length()-1) == ' ')
			arg1 = arg0;
		else
			arg1 = arg0 + " ";
		Stack<Character> operatorStack = new Stack<Character>();
		String t = "", b = "";
		char last = '\0';
		for(int i = 0;i < arg1.length();i++){
			switch(arg1.charAt(i)){
			case '(':
				operatorStack.push(arg1.charAt(i));
				break;
			case ')':{
					if(!"".equals(b)){
						t += b;
						t += ' ';
						b = "";
					}
					while(operatorStack.peek() != '('){
						t += operatorStack.pop();
						t += ' ';
					}
					operatorStack.pop();
					break;
				}
			case '+':
			case '-':
			case '*':
			case '/':
			case '^':
			case '%':{
					while(!operatorStack.isEmpty() && (p(operatorStack.peek()) > p(arg1.charAt(i)))){
						t += operatorStack.pop();
						t += ' ';
					}
					operatorStack.push(arg1.charAt(i));
					last = arg1.charAt(i);
					break;
				}
			case ' ':{
				t += b;
				if(Character.isDigit(last) | Character.isAlphabetic(last))
					t += ' ';
				b = "";
				break;
			}
			default:{
					b += arg1.charAt(i);
					last = arg1.charAt(i);
					break;
				}
			}
		}
		while(!operatorStack.isEmpty()){
			t += operatorStack.pop();
			t += ' ';
		}
		return t.replaceAll("  ", " ");
	}
	public static void main(String[] args) throws IOException {
		String expression = "";
		Character choise = '\0';
		System.out.println("enter expression to transform:");
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);
		expression = br.readLine();
		System.out.println(RPN(expression) + "\nDo you want me to calculate it?[Y/N]");
		choise = (char) System.in.read();
		if(Character.toLowerCase(choise) == 'y'){
			System.out.println(calculateRPN(RPN(expression)));
		}else if(Character.toLowerCase(choise) == 'x'){
			return;
		}
	}
}
