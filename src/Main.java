import java.util.Stack;

class Tree {
	protected Tree left, right;
	protected Object cargo;
	public Tree(Object c, Tree l, Tree r){
		left = l;
		right = r;
		cargo = c;
	}
	public Tree(Object c){
		cargo = c;
		right = null;
		left = null;
	}
	public void print(){ 
		if (this.left != null)
			this.left.print(); 
		System.out.print (this.cargo + " ");
		if(this.right != null)
			this.right.print();
	}
	public void printPostorder(){
		if (this.left != null)
			this.left.printPostorder();
		if (this.right != null)
			this.right.printPostorder();
		System.out.print (this.cargo + " ");
	}
	public void printPreorder(){
		System.out.print(this.cargo + " ");
		if (this.left != null)
			this.left.printPreorder();
		if (this.right != null)
			this.right.printPreorder();
	}
	public String getPostorder(){
		String temp = "";
		if (this.left != null)
			temp += this.left.getPostorder();
		if (this.right != null)
			temp += this.right.getPostorder();
		temp += this.cargo;
		temp += " ";
		return temp;
	}
	public void changeLeft(Tree arg0){
		this.left = arg0;
	}
	public void changeRight(Tree arg0){
		this.right = arg0;
	}
	public void setCargo(Object arg0){
		this.cargo = arg0;
	}
}
public class Main {
	private static Boolean isOperator(char arg0){
		if((arg0 == '+') || (arg0 == '-') | (arg0 == '*') || (arg0 == '/') || (arg0 == '^'))
			return true;
		else
			return false;
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
				tmp1 = x.pop();
				x.add(tmp1 + tmp2);
				ctmp0 = arg0.charAt(i);
			}else if(arg0.charAt(i) == '-'){
				tmp2 = x.pop();
				tmp1 = x.pop();
				x.add(tmp1 - tmp2);
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
	public static Tree ParseTree(String arg0){
		Stack<Tree> x = new Stack<Tree>();
		Object tmp1, tmp2;
		for(int i = 0;i < arg0.length();i++){
			if(Character.isAlphabetic(arg0.charAt(i)) || Character.isDigit(arg0.charAt(i))){
				x.push(new Tree(arg0.charAt(i), null, null));
			}else if(isOperator(arg0.charAt(i))){
				tmp2 = x.pop();
				tmp1 = x.pop();
				if(arg0.charAt(i) == '+')
					x.push(new Tree('+', new Tree(tmp1, null, null), new Tree(tmp2, null, null)));
				else if(arg0.charAt(i) == '-')
					x.push(new Tree('-', new Tree(tmp1, null, null), new Tree(tmp2, null, null)));
				else if(arg0.charAt(i) == '*')
					x.push(new Tree('*', new Tree(tmp1, null, null), new Tree(tmp2, null, null)));
				else if(arg0.charAt(i) == '/')
					x.push(new Tree('/', new Tree(tmp1, null, null), new Tree(tmp2, null, null)));
				else if(arg0.charAt(i) == '^')
					x.push(new Tree('^', new Tree(tmp1, null, null), new Tree(tmp2, null, null)));
			}
		}
		return x.pop();
	}
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
	public static String RPN(String arg0){
		String arg1;
		if(arg0.charAt(arg0.length()-1) == ' ')
			arg1 = arg0;
		else
			arg1 = arg0 + " ";
		Stack<Character> S = new Stack<Character>();
		String t = "", b = "";
		char last = '\0';
		for(int i = 0;i < arg1.length();i++){
			switch(arg1.charAt(i)){
			case '(':
				S.push(arg1.charAt(i));
				break;
			case ')':{
					if(!"".equals(b)){
						t += b;
						t += ' ';
						b = "";
					}
					while(S.peek() != '('){
						t += S.pop();
						t += ' ';
					}
					S.pop();
					break;
				}
			case '+':
			case '-':
			case '*':
			case '/':
			case '^':{
					while(!S.isEmpty() && (p(S.peek()) > p(arg1.charAt(i)))){
						t += S.pop();
						t += ' ';
					}
					S.push(arg1.charAt(i));
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
		while(!S.isEmpty()){
			t += S.pop();
			t += ' ';
		}
		return t;
	}
	public static void main(String[] args) {
		System.out.println(calculateRPN(RPN("(2 * 3 ) + 8")) + "\n" + (12 + 1 * (2 * 3 + 4 / 5)));
	}
}
