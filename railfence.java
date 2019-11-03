import java.util.*;

class Main{
	private static class RailFence{
		String encrypt(String plainText, int key){
			String[] rails = new String[key];
			Arrays.fill(rails,"");
			int flag = 1, rail = 0;
			for(int i = 0; i < plainText.length(); i++){
				rails[rail] += plainText.charAt(i);
				if(rail == key-1)
					flag = -1;
				else if(rail == 0)
					flag = 1;
				rail += flag;
			}
			for(int i = 1; i < key; i++)
				rails[0] += rails[i];
			return rails[0];
		}

		String decrypt(String cipherText, int key){
			int size[] = new int[key];
			String[] rails = new String[key];
			Arrays.fill(rails,"");
			int flag = 1, rail = 0;
			for(int i = 0; i < cipherText.length(); i++){
				size[rail] += 1;
				if(rail == key-1)
					flag = -1;
				else if(rail == 0)
					flag = 1;
				rail += flag;
			}
			rail = 0;
			for(int i = 0; i < key; i++){
				rails[i] = cipherText.substring(rail, rail+size[i]);
				rail += size[i];
				size[i] = 0;
			}
			String plainText = "";
			rail = 0;
			for(int i = 0; i < cipherText.length(); i++){
				plainText += rails[rail].charAt(size[rail]);
				size[rail] += 1;
				if(rail == key-1)
					flag = -1;
				else if(rail == 0)
					flag = 1;
				rail += flag;
			}
			return plainText;
		}
	}
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter number of rails: ");
		int key = in.nextInt();
		RailFence cipher = new RailFence();
		text = cipher.encrypt(text, key);
		System.out.println("cipherText: "+text);
		text = cipher.decrypt(text, key);
		System.out.println("plainText: "+text);
	}
}