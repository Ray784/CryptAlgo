import java.util.*;

class Main{
	private static class SHA512{
		String hashBuffer[] = {"6a09e667f3bcc908", "bb67ae8584caa73b", "3c6ef372fe94f82b", "a54ff53a5f1d36f1", "510e527fade682d1", "9b05688c2b3e6c1f","1f83d9abfb41bd6b", "5be0cd19137e2179"};
    	String k[] = {"428a2f98d728ae22", "7137449123ef65cd", "b5c0fbcfec4d3b2f", "e9b5dba58189dbbc", "3956c25bf348b538", "59f111f1b605d019", "923f82a4af194f9b", "ab1c5ed5da6d8118", "d807aa98a3030242", "12835b0145706fbe", "243185be4ee4b28c", "550c7dc3d5ffb4e2", "72be5d74f27b896f", "80deb1fe3b1696b1", "9bdc06a725c71235", "c19bf174cf692694", "e49b69c19ef14ad2", "efbe4786384f25e3", "0fc19dc68b8cd5b5", "240ca1cc77ac9c65", "2de92c6f592b0275", "4a7484aa6ea6e483", "5cb0a9dcbd41fbd4", "76f988da831153b5", "983e5152ee66dfab", "a831c66d2db43210", "b00327c898fb213f", "bf597fc7beef0ee4", "c6e00bf33da88fc2", "d5a79147930aa725", "06ca6351e003826f", "142929670a0e6e70", "27b70a8546d22ffc", "2e1b21385c26c926", "4d2c6dfc5ac42aed", "53380d139d95b3df", "650a73548baf63de", "766a0abb3c77b2a8", "81c2c92e47edaee6", "92722c851482353b", "a2bfe8a14cf10364", "a81a664bbc423001", "c24b8b70d0f89791", "c76c51a30654be30", "d192e819d6ef5218", "d69906245565a910", "f40e35855771202a", "106aa07032bbd1b8", "19a4c116b8d2d0c8", "1e376c085141ab53", "2748774cdf8eeb99", "34b0bcb5e19b48a8", "391c0cb3c5c95a63", "4ed8aa4ae3418acb", "5b9cca4f7763e373", "682e6ff3d6b2b8a3", "748f82ee5defb2fc", "78a5636f43172f60", "84c87814a1f0ab72", "8cc702081a6439ec", "90befffa23631e28", "a4506cebde82bde9", "bef9a3f7b2c67915", "c67178f2e372532b", "ca273eceea26619c", "d186b8c721c0c207", "eada7dd6cde0eb1e", "f57d4f7fee6ed178", "06f067aa72176fba", "0a637dc5a2c898a6", "113f9804bef90dae", "1b710b35131c471b", "28db77f523047d84", "32caab7b40c72493", "3c9ebe0a15c9bebc",  "431d67c49c100d4c", "4cc5d4becb3e42b6", "597f299cfc657e2a", "5fcb6fab3ad6faec", "6c44198c4a475817"};
    	String toHex(String text){
    		String output = "";
    		for(int i = 0; i < text.length(); i++)
    			output += Integer.toHexString((int)text.charAt(i));
    		return output;
    	}

    	String prepare(String text){
    		String l = Long.toHexString(text.length()*4);
    		while(l.length() < 32)
    			l = "0"+l;
    		text += "8";
    		while(text.length()%256 != 224)
    			text += "0";
    		return text+l;
    	}

    	String xor(String... a){
			long ans;
			ans = Long.parseUnsignedLong(a[0], 16);
			for(int i = 1; i < a.length; i++)
				ans ^= Long.parseUnsignedLong(a[i], 16);
			a[0] = Long.toHexString(ans);
			while(a[0].length() < a[1].length())
				a[0] = "0" + a[0];
			return a[0];
		}

		String and(String... a){
			long ans;
			ans = Long.parseUnsignedLong(a[0], 16);
			for(int i = 1; i < a.length; i++)
				ans &= Long.parseUnsignedLong(a[i], 16);
			a[0] = Long.toHexString(ans);
			while(a[0].length() < a[1].length())
				a[0] = "0" + a[0];
			return a[0];
		}

		String add(String... a){
			long ans;
			ans = Long.parseUnsignedLong(a[0], 16);
			for(int i = 1; i < a.length; i++)
				ans = ans + Long.parseUnsignedLong(a[i], 16);
			a[0] = Long.toHexString(ans);
			while(a[0].length() < a[1].length())
				a[0] = "0" + a[0];
			return a[0];
		}

		String not(String a){
			int n = a.length();
			long t_a = Long.parseUnsignedLong(a, 16);;
			t_a = ~ t_a;
			a = Long.toHexString(t_a);
			while(a.length() < n)
				a = "0" + a;
			return a;
		}

		String rotr(String input, int numBits){
			long a = Long.parseUnsignedLong(input, 16);
			a = (a >>> numBits) | (a << (64 - numBits));
			String output = Long.toHexString(a);
			while(output.length() < input.length())
				output = "0"+output;
			return output;
		}

		String shr(String input, int numBits){
			long a = Long.parseUnsignedLong(input, 16);
			a = (a >>> numBits);
			String output = Long.toHexString(a);
			while(output.length() < input.length())
				output = "0"+output;
			return output;
		}

		String S0(String input){
			return xor(rotr(input, 1), rotr(input, 8), shr(input, 7));
		}

		String S1(String input){
			return xor(rotr(input, 19), rotr(input, 61), shr(input, 6));
		} 

		String s0(String  input){
			return xor(rotr(input, 28), rotr(input, 34), rotr(input, 39));
		}

		String s1(String input){
			return xor(rotr(input, 14), rotr(input, 18), rotr(input, 41));
		}

		String ch(String e, String f, String g){	
			return xor(and(e, f), and(not(e), g));
		}

		String maj(String a, String b, String c){
			return xor(and(a, b), and(a, c), and(b, c));			
		}

		String[] round(String hashBuffer[], String word, String key){
			String t1 = add(hashBuffer[7], ch(hashBuffer[4], hashBuffer[5], hashBuffer[6]), s1(hashBuffer[4]), word, key);
			String t2 = add(s0(hashBuffer[0]), maj(hashBuffer[0] ,hashBuffer[1], hashBuffer[2]));
			hashBuffer[7] = hashBuffer[6];
			hashBuffer[6] = hashBuffer[5];
			hashBuffer[5] = hashBuffer[4];
			hashBuffer[4] = add(hashBuffer[3], t1);
			hashBuffer[3] = hashBuffer[2];
			hashBuffer[2] = hashBuffer[1];
			hashBuffer[1] = hashBuffer[0];
			hashBuffer[0] = add(t1,t2);
			return hashBuffer;
		}

		String[] genWords(String M){
			String W[] = new String[80];
			for(int i = 0; i < M.length(); i += 16)
				W[i/16] = M.substring(i, i+16);
			for(int i = 16; i < 80; i++)
				W[i] = add( S1(W[i-2]), W[i-7], S0(W[i-15]), W[i-16] );
			return W;
		}

		String print1024B(String[] input){
			String output = "";
			for(int i = 0; i < input.length; i++){
				if(i % 4 == 0)
					output+="\n";
				output += input[i]+"  ";
			}
			return output;
		}

		String digest(String input){
    		input = toHex(input);
    		input = prepare(input);
    		for(int i = 0; i < input.length(); i+=256){
    			String m = input.substring(i, i+256);
    			String w[] = genWords(m);
    			String hi_1[] = new String[8];

    			for(int j = 0; j < 8; j++)
    				hi_1[j] = hashBuffer[j];

    			for(int j = 0; j < 80; j++)
    				hashBuffer = round(hashBuffer, w[j], k[j]);

    			for(int j = 0; j < 8; j++)
    				hashBuffer[j] = add(hashBuffer[j],hi_1[j]);
    		}
    		return print1024B(hashBuffer);
    	}
	}
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plaintext: ");
		String text = in.nextLine();
		SHA512 hash = new SHA512();
		text = hash.digest(text);
		System.out.println("message digest:"+text);
	}
}