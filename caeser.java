import java.util.*;

class Main{
	private static class CaeserCipher{

		private String preprocess(String text){
			return (text.toLowerCase()).replaceAll("\\s+","");
		}

		private String encrypt(String plainText, int key){
			plainText = preprocess(plainText);
			int n = plainText.length();
			String cipherText = "";
			for(int i = 0; i < n; i++){
				int flag = 0;
				char temp = plainText.charAt(i);
				temp += key;
				if(temp > 'z') 
					temp -= 26;
				cipherText += temp+"";
			}
			return cipherText;
		}
		private String decrypt(String cipherText, int key){
			int n = cipherText.length();
			String plainText = "";
			for(int i = 0; i < n; i++){
				int flag = 0;
				char temp = cipherText.charAt(i);
				temp -= key;
				if(temp < 'a')
					temp += 26;
				plainText += temp+"";
			}
			return plainText;
		}
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter key: ");
		int key = in.nextInt();
		CaeserCipher cipher = new CaeserCipher();
		text = cipher.encrypt(text, key);
		System.out.printf("cipherText: "+text+"\n");
		text = cipher.decrypt(text, key);
		System.out.printf("plainText: "+text);
	}
}