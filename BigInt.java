import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class BigInt {
	private ArrayList<Integer> digits;
	private int minus;
	private static BigInt remain;
	private StringBuilder sb;
	public BigInt(String s) throws Exception {
		sb = new StringBuilder(s);
		minus = 0;
		digits = new ArrayList<>();
		for(int i = 0;i < s.length();i++){
			if(s.charAt(i) == '-'){
        		minus++;
        	}
        	else if(s.charAt(i) < '0' || s.charAt(i) > '9' || minus > 1){
    		    throw new Exception("Incorrect BigInt: " + s);
    	    }
        	else{
        		digits.add(s.charAt(i) - 48);
        	}
		}
	}
	public int getMinus(){
		return minus;
	}
	public void setMinus(int m){
		minus = m;
	}
	public void changeDigits(BigInt other) throws Exception{
		BigInt a = new BigInt("0");
		a.digits.remove(0);
		a.digits = digits;
		BigInt b = new BigInt("0");
		b.digits.remove(0);
		b.digits = other.digits;
		int comparison = Integer.parseInt(a.compareTo(b).toString());
		if(comparison == -1){
			ArrayList<Integer> tmp = other.digits;
			other.digits = digits;
			digits = tmp;
		}
		
	}
	public BigInt compareTo(BigInt other) throws Exception {
		BigInt r = new BigInt("0");
		r.digits.remove(0);
		if(digits.size() == other.digits.size()){
			int i = 0;
			while(i < digits.size()){
				int one = digits.get(i);
				int two = other.digits.get(i);
				i++;
				if(one != two){
					r.digits.add((one > two) ? 1 : -1);
					return r;
				}
			}
			return new BigInt("0");
		}
		r.digits.add((digits.size() > other.digits.size()) ? 1 : -1);
		return r;
	}
	public BigInt add(BigInt other) throws Exception{
		changeDigits(other);
		BigInt r = new BigInt("0");
    	r.digits.remove(0);
    	int i = digits.size() - 1;
    	int j = other.digits.size() - 1;
    	int carry = 0;
    	while(i > -1 || j > -1 || carry != 0){
    		int sum = carry;
    		if(i > -1){
    			sum += digits.get(i);
    			--i;
    		}
    		if(j > -1){
    			sum += other.digits.get(j);
    			--j;
    		}
    		carry = sum / 10;
    		int d = sum % 10;
    		r.digits.add(0, d);
    	}
    	return r;
	}
	public BigInt subtract(BigInt other) throws Exception{
		changeDigits(other);
		BigInt r = new BigInt("0");
		r.digits.remove(0);
		int i = digits.size() - 1;
		int j = other.digits.size() - 1;
		int remain = 0;
		int sum = 0;
		while(i > -1){
			sum = digits.get(i);
			if(j > -1){
				sum -= other.digits.get(j) + remain;
				remain = 0;
				j--;
			}
			else{
				sum -= remain;
				remain = 0;
			}
			if(sum < 0){
				sum += 10;
				remain = 1;
			}
			r.digits.add(0, sum);
			i--;
		}
		while(r.digits.get(0) == 0){
			r.digits.remove(0);
			if(r.digits.size() == 0){
				r.digits.add(0);
				break;
			}
		}
		return r;
	}
	public BigInt multiply(BigInt b) throws Exception{
    	BigInt result = new BigInt("0");	
		int sum = 0;
		int step = 0;
		int i = b.digits.size() - 1;
		while(i >= 0){
			int r = 0;
			int j = digits.size() - 1;
			BigInt sec = new BigInt("0");
			sec.digits.remove(0);
			while(j >= 0){
				int anum = b.digits.get(i) * digits.get(j) + r;
				r = anum / 10;
				sum = anum % 10;
				sec.digits.add(0, sum);
				j--;
			}
			if(r != 0){
				sec.digits.add(0, r);
			}
			for(int z = 0;z < step;z++){
				sec.digits.add(0);
			}
			step++;
			i--;
			result = result.add(sec);
		}
		return result;
    }

	public BigInt divide(BigInt b) throws Exception {
		StringBuilder sn = new StringBuilder();
        for(int i = 0;i < digits.size();i++){
        	sn.append(digits.get(i));
        }
        if(b.digits.get(0) == 0 && b.digits.size() == 1){
            throw new ArithmeticException("no division by zero");
        }
        if(digits.size() < b.digits.size() || digits.get(0) == 0 && digits.size() == 1){
        	return new BigInt("0");
        }  
        int comparison = Integer.parseInt(new BigInt(sn.toString()).compareTo(b).toString());
        if(comparison == -1){
        	return new BigInt("0");
        }
        BigInt r = new BigInt("0");
        r.digits.remove(0);
        int i = b.digits.size();
        BigInt a = new BigInt(sn.substring(0, i));
        comparison = Integer.parseInt(a.compareTo(b).toString());
        if(comparison == -1){
        	a.digits.add(digits.get(i));
        	i++;
        	comparison = Integer.parseInt(a.compareTo(b).toString());
        }
        int count = 0;
        if(i == digits.size()){
        	while(comparison != -1){
        		a = a.subtract(b);
        		count++;
        		comparison = Integer.parseInt(a.compareTo(b).toString());
        	}
        	r.digits.add(count);
        }
        else{   	
        	while(i < digits.size()){
        		while(comparison == -1){
        				a.digits.add(digits.get(i));
        				i++;
        				comparison = Integer.parseInt(a.compareTo(b).toString());
        				if(comparison == -1){
        					r.digits.add(0);
        				}
        				if(i >= digits.size()){
        					if(comparison == -1){
        						remain = a;
        						return r;
        				}
        			}
        		}
        	while(comparison != -1){
        		a = a.subtract(b);
        		count++;
        		comparison = Integer.parseInt(a.compareTo(b).toString());
        	}
        	r.digits.add(count); 	
        	 if(i < digits.size()){
                 if(digits.get(i) == 0 && a.digits.get(0) == 0){
                     while(digits.get(i) == 0){
                         r.digits.add(0);
                         i++;
                         if(i >= digits.size()){
                             break;
                         }
                     }
                 }
             }
             if(a.digits.get(0) == 0){
                 a.digits.remove(0);
             }
             count = 0;
        }
        }
        remain = a;
        return r;
    }
	public BigInt remainder(BigInt other) throws Exception {
		StringBuilder sn = new StringBuilder();
		for(int i = 0;i < digits.size();i++){
			sn.append(digits.get(i));
		}
		BigInt a = new BigInt(sn.toString());
        BigInt b = new BigInt(other.sb.toString());
        if(Integer.parseInt(a.compareTo(b).toString()) == -1){
            return a;
        }
        BigInt c = a.divide(b);
        if(c.remain.digits.size() == 0){
            c.remain.digits.add(0);
        }
        return c.remain;
	}
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Integer d : digits){
			result.append(d);
		}
		return result.toString();
	}
	
}