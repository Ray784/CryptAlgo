import java.util.*;

class Main{
	private static class SDES{
		int IP[] = {1, 5, 2, 0, 3, 7, 4, 6}, EP[] = {3, 0, 1, 2, 1, 2, 3, 0}, P8[] = {5, 2, 6, 3, 7, 4, 9, 8}, P4[] = {1, 3, 2, 0};
		int P10[] = {2, 4, 1, 6, 3, 9, 0, 8, 7, 5}, LS1[] = {1, 2, 3, 4, 0, 6, 7, 8, 9, 5}, LS2[] = {2, 3, 4, 0, 1, 7, 8, 9, 5, 6};
		int IP1[] = {3, 0, 2, 4, 6, 1, 7, 5};
		String k[] = {"",""};
		String sbox[][][] = {{{"01","00","11","10"},{"11","10","01","00"},{"00","10","01","11"},{"11","01","11","10"}}, {{"00","01","10","11"},{"10","00","01","11"},{"11","00","01","00"},{"10","01","00","11"}}};

		String permutation(int[] sequence, String input){
			String output = "";
			for(int i = 0; i < sequence.length; i++)
				output += input.charAt(sequence[i]);
			return output;
		}

		String xor(String a, String b){
			int t_a = Integer.parseInt(a, 2);
			int t_b = Integer.parseInt(b, 2);
			t_a = t_a ^ t_b;
			a = Integer.toBinaryString(t_a);
			while(a.length() < b.length())
				a = "0" + a;
			return a;
		}

		void keyGenerate(String key){
			key = permutation(P10, key);
			key = permutation(LS1, key);
			k[0] = permutation(P8, key);
			key = permutation(LS2, key);
			k[1] = permutation(P8, key);
		}

		String sBox(String input, int num){
			int row = Integer.parseInt(input.charAt(0)+""+input.charAt(3), 2);
			int col = Integer.parseInt(input.charAt(1)+""+input.charAt(2), 2);
			return sbox[num][row][col];
		}

		String round(String input, int num){
			//fk
			String left = input.substring(0,4);
			String temp = input.substring(4,8);
			String right = temp;
			temp = permutation(EP, temp);
			temp = xor(temp,k[num]);
			temp = sBox(temp.substring(0,4),0) + sBox(temp.substring(4,8),1);
			temp = permutation(P4, temp);
			left = xor(left,temp);
			//s
			return right+left;
		}

		String encrypt(String plainText, String key){
			int i;
			keyGenerate(key);
			plainText = permutation(IP, plainText);
			for(i = 0; i < 2; i++)
				plainText = round(plainText, i);
			plainText = plainText.substring(4,8)+plainText.substring(0,4);
			plainText = permutation(IP1, plainText);
			return plainText;
		}

		String decrypt(String cipherText, String key){
			int i;
			keyGenerate(key);
			cipherText = permutation(IP, cipherText);
			for(i = 1; i >= 0; --i)
				cipherText = round(cipherText, i);
			cipherText = cipherText.substring(4,8)+cipherText.substring(0,4);
			cipherText = permutation(IP1, cipherText);
			return cipherText;
		}
	}
	
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText:(8-bits): ");
		String text = in.nextLine();
		System.out.printf("Enter key:(10-bits): ");
		String key = in.nextLine();
		SDES cipher = new SDES();
		text = cipher.encrypt(text, key);
		System.out.println("cipherText: "+text);
		text = cipher.decrypt(text, key);
		System.out.println("plainText: "+text);
	}
}