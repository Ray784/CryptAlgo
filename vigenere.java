import java.util.*;

class Main{
	private static class VigenereCipher{
		private String preprocess(String text){
			return (text.toLowerCase()).replaceAll("\\s+","");
		}

		private String keyGenerate(String key, String plainText){
			key = preprocess(key);
			while(key.length() < plainText.length())
				key += key;
			return key.substring(0,plainText.length());
		}

		private String encrypt(String plainText, String key){
			plainText = preprocess(plainText);
			key = keyGenerate(key, plainText);
			int n = plainText.length();
			int ks = key.length();
			String res = "";
			for(int i = 0; i < n; i++){
				res += (char)(((plainText.charAt(i) + key.charAt(i) - 194) % 26) + 97)+"";
			}
			return res;
		}

		private String decrypt(String cipherText, String key){
			cipherText = preprocess(cipherText);
			key = keyGenerate(key, cipherText);
			int n = cipherText.length();
			int ks = key.length();
			String res = "";
			for(int i = 0; i < n; i++){
				res += (char)(((cipherText.charAt(i) - key.charAt(i) + 26) % 26) + 97) + "";
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