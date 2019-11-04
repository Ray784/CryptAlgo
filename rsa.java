import java.util.*;

class Main{
	private static class RSA{
		private int privateKey, n;

		boolean isPrime(int number){
			if(number<=1) return false;
			if(number==3 || number==2) return true;
			if(number %2 == 0) return false;
			for(int i = 3; i <= Math.sqrt(number); i += 2)
				if(number % i == 0)
					return false;
			return true;
		}

		int getRandomPrime(){
			Random rand = new Random();
			int prime = rand.nextInt(100);
			while(!isPrime(prime))
				prime = rand.nextInt(100);
			return prime;
		}

		int gcd(int a, int b){
			if(a == b) return a;
			else if(a == 0 || b == 0) return 0;
			else if(b > a) return gcd(b,a);
			else if(a % b == 0) return b;
			else return gcd(b, a%b);
		}

		int inverseMod(int a, int m){
			for(int i = 1; i < m; i++)
				if(a*i % m == 1)
					return i;
			return -1;
		}

		int powerModm(int a, int b, int m){
			int ans = 1;
			a %= m;
			while(b > 0){
				if(b % 2 != 0)
					ans = (ans * a) % m;
				b /= 2;
				a = (a * a) % m;
			}
			return ans;
		}

		int keyGenerate(){
			int p = getRandomPrime();
			int q = getRandomPrime();
			n = p*q;
			int phi = (p-1) * (q-1);

			Random rand = new Random();
			int e = rand.nextInt(phi-2)+2;
			privateKey = e;
			while(privateKey == e){
				while(gcd(e,phi) != 1)
					e = rand.nextInt(phi-2)+2;
				privateKey = inverseMod(e, phi);
			}
			return e;
		}

		int encrypt(int plainText){
			int publicKey = keyGenerate();
			System.out.println("Public key of B: <"+publicKey+", "+n+">");
			return powerModm(plainText, publicKey, n);
		}

		int decrypt(int cipherText){
			return powerModm(cipherText, privateKey, n);
		}
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter plainText(1 - 1000): ");
		int text = in.nextInt();
		RSA cipher = new RSA();
		System.out.println("At A (encrypt with public key of B):");
		text = cipher.encrypt(text);
		System.out.printf("cipherText: "+text+"\n");

		System.out.println("At B (decrypt with private key of B):");
		text = cipher.decrypt(text);
		System.out.printf("plainText: "+text);
	}
}