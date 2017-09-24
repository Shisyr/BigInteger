import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	private static ArrayList<String> init;
	private static Stack<BigInt> stacks;
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		while(scn.hasNextLine()){
			init = new ArrayList<>();
			stacks = new Stack<>();
			String sMinus = "";
			String line = scn.nextLine();
			Scanner scn2 = new Scanner(line);
			BigInt a;
			BigInt b;
			int operand = 0;
			try{
			while(scn2.hasNext()){
				init.add(scn2.next());
			}
			for(String e : init){
				if(isOperator(e) == false){
					operand++;
				}
			}
			if((operand - 1) > init.size() - operand){
				throw new Exception("Input many operands");
			}
			int i = 0;
			while(i < init.size()){
				if(isOperator(init.get(i))){
					if(stacks.size() < 2){
						throw new Exception("Input insufficient values");
					}
					b = stacks.pop();
					a = stacks.pop();
					switch(init.get(i)){
					case "+":
						int compare = 0;
						if(a.getMinus() == b.getMinus()){
							sMinus = (a.getMinus() == 1) ? "-" : "";
							a = a.add(b);
							a.setMinus((sMinus.equals("-")) ? 1 : 0);	
						}
						else{
							compare = Integer.parseInt(a.compareTo(b).toString());
							sMinus = (a.getMinus() == 1 && compare == 1 || a.getMinus() == 0 && compare == -1) ? "-" : "";
							a = a.subtract(b);
							a.setMinus((sMinus.equals("-")) ? 1 : 0);
						}
						break;
					case "-":
						int compares = 0;
						if(a.getMinus() == b.getMinus()){
							compares = Integer.parseInt(a.compareTo(b).toString());	
							sMinus = (a.getMinus() == 1 && compares == 1 || a.getMinus() == 0 && compares == -1) ? "-" : "";	
							a = a.subtract(b);
							a.setMinus((sMinus.equals("-")) ? 1 : 0);
						}
						else{
							sMinus = (a.getMinus() == 1) ? "-" : "";
							a = a.add(b);
							a.setMinus((sMinus.equals("-")) ? 1 : 0);
						}
						break;
					case "*":
						sMinus = (a.getMinus() == b.getMinus()) ? "" : "-";
						a = a.multiply(b);
						a.setMinus((sMinus.equals("-")) ? 1 : 0);
						break;
					case "/":
						int comp = Integer.parseInt(a.compareTo(b).toString());
						sMinus = (a.getMinus() == b.getMinus() || comp == -1) ? "" : "-";
						a = a.divide(b);
						a.setMinus((sMinus.equals("-")) ? 1 : 0);
						break;
					case "%":
						int compr = Integer.parseInt(a.compareTo(b).toString());
						sMinus = (a.getMinus() == b.getMinus() || compr == -1) ? "" : "-";
						a = a.remainder(b);
						a.setMinus((sMinus.equals("-")) ? 1 : 0);
						break;
						default:
							throw new Exception("Incorrect operator: " + init.get(i));
					}
					stacks.push(a);	
				}
				else{
					stacks.push(new BigInt(init.get(i)));
				}
				i++;
				}
				System.out.println(sMinus + stacks.get(0));
				
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
}// Main
	
	static boolean isOperator(String l){
		if("+-/*".indexOf(l.charAt(0)) != -1 && l.length() < 2){
			return true;
		}
		return false;
	}
}
