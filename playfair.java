import java.util.*;

class Main{
	private static class PlayFair{
		String preprocess(String text){
			text = text.replaceAll("[^a-zA-Z]+", "");
			text = text.replaceAll("j","i");
			text = text.trim();
			text = text.toLowerCase();
			return text;
		}

		int mod(int a, int b){
			a %= b;
			return a < 0? a+b: a;
		}

		String[] keyTableGenerate(String key){
			int[] hashMap = new int[26];
			String[] keyTable = new String[5];
			Arrays.fill(keyTable, "");
			int k = 0;
			hashMap[9] = 1; 
			for(int i = 0; i < key.length(); i++){
				int val = (int)(key.charAt(i) - 'a'); 
				if(hashMap[val]++ == 0)
					keyTable[k] += (char)('a'+val)+"";
				if(keyTable[k].length() == 5)
					k++;
			}
			for(int i = 0; i < 26; i++){
				if(hashMap[i]++ == 0)
					keyTable[k] += (char)('a'+i)+"";
				if(keyTable[k].length() == 5)
					k++;
			}
			return keyTable;
		} 

		String round(char a, char b, String[] keyTable, int flag){
			int[] pos = {-1,-1, -1,-1};
			String ans = "";
			for(int i = 0; i < 5; i++){
				if(pos[1] == -1){
					pos[1] = keyTable[i].indexOf(a);
					if(pos[1] != -1)
						pos[0] = i;
				}
				if(pos[3] == -1){
					pos[3] = keyTable[i].indexOf(b);
					if(pos[3] != -1)
						pos[2] = i;
				}
			}
			if(a == b){
				pos[0] = mod(pos[0]+flag, 5);
				pos[1] = mod(pos[1]+flag, 5);
				pos[2] = mod(pos[2]+flag, 5);
				pos[3] = mod(pos[3]+flag, 5);
			}
			else if(pos[0] == pos[2]){
				pos[1] = mod(pos[1]+flag, 5);
				pos[3] = mod(pos[3]+flag, 5);
			}
			else if(pos[1] == pos[3]){
				pos[0] = mod(pos[0]+flag, 5);
				pos[2] = mod(pos[2]+flag, 5);
			}
			else{
				int temp = pos[1];
				pos[1] = pos[3];
				pos[3] = temp;
			}
			return keyTable[pos[0]].charAt(pos[1])+""+keyTable[pos[2]].charAt(pos[3]);
		}

		String encrypt(String plainText, String key){
			String cipherText = "";
			key = preprocess(key);
			String[] keyTable = keyTableGenerate(key);
			plainText = preprocess(plainText);
			if(plainText.length() % 2 == 1)
				plainText += "z";
			for(int i = 0; i < plainText.length(); i+=2)
				cipherText += round(plainText.charAt(i), plainText.charAt(i+1), keyTable, 1);
			return cipherText;
		}

		String decrypt(String cipherText, String key){
			String plainText = "";
			preprocess(key);
			String[] keyTable = keyTableGenerate(key);
			for(int i = 0; i < cipherText.length(); i+=2)
				plainText += round(cipherText.charAt(i), cipherText.charAt(i+1), keyTable, -1);
			return plainText;
		}
	}
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter key: ");
		String key = in.nextLine();
		PlayFair cipher = new PlayFair();
		text = cipher.encrypt(text, key);
		System.out.println("cipherText: "+text);
		text = cipher.decrypt(text, key);
		System.out.println("plainText: "+text);
	}
}