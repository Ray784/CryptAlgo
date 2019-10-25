import java.util.*;

class Main{
	private static class VigenereCipher{
		private String encrypt(String plainText, String key){
			plainText = plainText.toLowerCase();
			key = key.toLowerCase();
			int n = plainText.length();
			int ks = key.length();
			String res = "";
			int j = 0;
			for(int i = 0; i < n; i++){ 
				char temp = plainText.charAt(i);
				if(Character.isLetter(temp)){
					temp = (char)(((temp + key.charAt(j % ks) - 194) % 26) + 97);
					j++;
				}
				res += temp+"";
			}
			return res;
		}
		private String decrypt(String cipherText, String key){
			cipherText = cipherText.toLowerCase();
			key = key.toLowerCase();
			int n = cipherText.length();
			int ks = key.length();
			String res = "";
			int j = 0;
			for(int i = 0; i < n; i++){ 
				char temp = cipherText.charAt(i);
				if(Character.isLetter(temp)){
					temp = (char)(((temp - key.charAt(j % ks) + 26) % 26) + 97);
					j++;
				}
				res += temp+"";
			}
			return res;
		}
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter key: ");
		String key = in.nextLine();
		VigenereCipher cipher = new VigenereCipher();
		text = cipher.encrypt(text, key);
		System.out.printf("cipherText: "+text+"\n");
		text = cipher.decrypt(text, key);
		System.out.printf("plainText: "+text);
	}
}