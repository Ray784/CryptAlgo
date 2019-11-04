import java.util.*;

class Main{
	private static class Columnar{

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

		String encrypt(String plainText, String key){
			String[] columns = fill(plainText, key.length());
			String cipherText = "";
			int[] index = rearrange(key);
			for(int i = 0; i < key.length(); i++)
				cipherText += columns[index[i]];
			return cipherText;
		}

		String decrypt(String cipherText, String key){
			String[] columns = fill(cipherText, key.length());
			String plainText = "";
			int[] index = rearrange(key);

			int start = 0;
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
	}
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter key: ");
		String key = in.nextLine();
		Columnar cipher = new Columnar();
		text = cipher.encrypt(text, key);
		System.out.println("cipherText: "+text);
		text = cipher.decrypt(text, key);
		System.out.println("plainText: "+text);
	}
}