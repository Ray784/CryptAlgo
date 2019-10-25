import java.util.*;

class Main{
	private static class CaeserCipher{
		private String encrypt(String plainText, int key){
			int n = plainText.length();
			String cipherText = "";
			for(int i = 0; i < n; i++){
				int flag = 0;
				char temp = plainText.charAt(i);
				if(Character.isLetter(temp)){
					if(temp <= 'Z')
						flag = 1;
					temp += key;
					if((flag == 1 && temp > 'Z') || (flag == 0 && temp > 'z')) 
						temp -= 26;
				}
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
				if(Character.isLetter(temp)){
					if(temp <= 'Z')
						flag = 1;
					temp -= key;
					if((flag == 1 && temp < 'A') || (flag == 0 && temp < 'a')) 
						temp += 26;
				}
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