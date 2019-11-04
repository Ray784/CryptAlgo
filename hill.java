import java.util.*;

class Main{
	private static class Matrices{
		int multiply(int[]A, int[]B, int m){
			int res = 0;
			for(int i = 0; i < A.length; i++)
				res = (res + (A[i] * B[i]) % m) % m;
			return (res+m)%m;
		}

		int[] multiply(int[][]A, int[]B, int m){
			int[] C = new int[B.length];
			for(int i = 0; i < B.length; i++)
				C[i] = multiply(A[i], B, m);
			return C;
		}
		
		int power(int a, int b){
			if((a+b) % 2 == 0)
				return 1;
			return -1;
		}

		int determinant(int A[][]){
			int n = A.length;
			int d = 0;
			if(n == 1) return A[0][0];
			else if(n == 2) return (A[0][0]*A[1][1])-(A[0][1]*A[1][0]);
			else
				for(int i = 0; i < n; i++)
					if(A[0][i] != 0)
						d += power(0, i) * A[0][i] * determinant(minor(A,0,i));
			return d;
		}

		int[][] minor(int[][] A, int row, int col){
			int p = 0, q = 0;
			int temp[][] = new int[A.length-1][A.length-1];
			for(int i = 0; i < A.length; i++){
				for(int j = 0; j < A[i].length; j++){
					if(i != row && j != col){
						temp[p][q] = A[i][j];
						q++;
						if(q == A[i].length - 1){
							p++;
							q = 0;
						}
					}
				}
			}
			return temp;
		}

		int inverseMod(int a, int m){
			for(int i = 1; i < m; i++)
				if(a*i % m == 1)
					return i;
			return -1;
		}

		int[][] inverse(int[][] A){
			int temp[][] = new int[A.length][A.length];
			int d = determinant(A);
			int mul = d>0? 1: -1;
			d *= mul;
			d = inverseMod(d, 26);
			d *= mul;
			for(int i = 0; i < A.length; i++)
				for(int j = 0; j < A[i].length; j++) 
					temp[j][i] = power(i,j)*determinant(minor(A, i, j))*d;
			return temp;
		}
	}
	private static class HillCipher{
		String preprocess(String text){
			text = text.replaceAll("[^a-zA-Z]+", "");
			text = text.trim();
			text = text.toLowerCase();
			return text;
		}

		int [][] toIntArr(String input, int row, int col){
			int res[][] = new int[row][col];
			int k = 0;
			int n = input.length();
			if(n>0)
				for(int i = 0; i < row; i++){
					for(int j = 0; j < col; j++){
						res[i][j] = (int)(input.charAt(k) - 'a');
						k = (k+1) % n;
					}
				}
			return res;
		}

		String toString(int[] input){
			String res = "";
			for(int i = 0; i < input.length; i++)
				res += ""+(char)(input[i]+'a');
			return res;
		}

		private String encrypt(String plainText, String key, int n){
			int [][]K = toIntArr(key, n, n);
			String cipherText = "";
			Matrices matrix = new Matrices();
			for(int i = 0; i < plainText.length(); i += n){
				int []P = toIntArr(plainText.substring(i, i+n), 1, n)[0];
				cipherText += toString(matrix.multiply(K, P, 26));
			}
			return cipherText;
		}

		private String decrypt(String cipherText, String key, int n){
			int [][]K = toIntArr(key, n, n);
			String plainText = "";
			Matrices matrix = new Matrices();
			K = matrix.inverse(K);
			for(int i = 0; i < cipherText.length(); i += n){
				int []P = toIntArr(cipherText.substring(i, i+n), 1, n)[0];
				plainText += toString(matrix.multiply(K, P, 26));
			}
			return plainText;
		}
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText: ");
		String text = in.nextLine();
		System.out.printf("Enter key: ");
		String key = in.nextLine();
		System.out.printf("Enter n: ");
		int n = in.nextInt();
		
		HillCipher cipher = new HillCipher();
		text = cipher.encrypt(text, key, n);
		System.out.printf("cipherText: "+text+"\n");
		text = cipher.decrypt(text, key, n);
		System.out.printf("plainText: "+text);
	}
}