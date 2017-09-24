import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws Exception{
		Scanner scn = new Scanner(System.in);
		int x1 = scn.nextInt(); int y1 = scn.nextInt();
		int x2 = scn.nextInt(); int y2 = scn.nextInt();
		while(x1 > 0 && y1 > 0 && x2 > 0 && y2 > 0){
			if(x1 == x2 || y1 == y2){
				System.out.println(1);
			}
			else if(x1 == x2 && y1 == y2){
				System.out.println(0);
			}
			else{
				int compx1 = x1 + y1;
				int compx2 = x2 + y2;
				if(compx1 == compx2){
					System.out.println(1);
				}
				else{
					System.out.println(2);
				}
			}
			x1 = scn.nextInt(); y1 = scn.nextInt();
			x2 = scn.nextInt(); y2 = scn.nextInt();
		}
		
	}
	}




