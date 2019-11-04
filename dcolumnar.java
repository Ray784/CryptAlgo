import java.util.*;

class Main{
	private static class DoubleColumnar{

		String[] fill(String input, int n){
			String[] result = new String[n];
			Arrays.fill(result, "");
			int k = 0, i = 0;
			while(k < input.length()){
				result[i++] += input.charAt(k++);
				i %= n;
			}
			return result;
		}

		int[] rearrange(String key){
			int[][] arr = new int[key.length()][2];
			for(int i = 0; i < key.length(); i++){
				arr[i][0] = (int)key.charAt(i) - 'a';
				arr[i][1] = i;
			}
			Arrays.sort(arr, new Comparator<int[]>(){ 
				public int compare(final int[] val1, final int[] val2){ 
					if (val1[0] > val2[0]) return 1; 
					return -1; 
				} 
	        });
	        int[] keys = new int[key.length()];
	        for(int i = 0; i < key.length(); i++)
	        	keys[i] = arr[i][1];
	        return keys;
		}

		String encrypt(String plainText, String key1, String key2){
			String cipherText = "";
			String[] columns = fill(plainText, key1.length());
			int[] index = rearrange(key1);
			for(int i = 0; i < key1.length(); i++)
				cipherText += columns[index[i]];

			columns = fill(cipherText, key2.length());
			cipherText = "";
			index = rearrange(key2);
			for(int i = 0; i < key2.length(); i++)
				cipherText += columns[index[i]];
			return cipherText;
		}

		String getText(String cipherText, String key){
			int start = 0;
			String[] columns = fill(cipherText, key.length());
			String plainText = "";
			int[] index = rearrange(key);
			for(int i = 0; i < key.length(); i++){
				int end = columns[index[i]].length();
				columns[index[i]] = cipherText.substring(start, start+end);
				start = start + end;
			}

			int rows = (int)Math.ceil((double)cipherText.length()/key.length());
			for(int j = 0; j < rows; j++)
				for(int i = 0; i < key.length(); i++)
					if(j < columns[i].length())
						plainText += columns[i].charAt(j);
			return plainText;
		}

		String decrypt(String cipherText, String key1, String key2){
			String plainText = "";
			cipherText = getText(cipherText, key2);
			plainText = getText(cipherText, key1);
			return plainText;
		}

	}
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter key1: ");
		String key1 = in.nextLine();
		System.out.printf("Enter key2: ");
		String key2 = in.nextLine();
		DoubleColumnar cipher = new DoubleColumnar();
		text = cipher.encrypt(text, key1, key2);
		System.out.println("cipherText: "+text);
		text = cipher.decrypt(text, key1, key2);
		System.out.println("plainText: "+text);
	}
}