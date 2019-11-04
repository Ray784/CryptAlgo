import java.util.*;

class Main{
	private static class DiffieHellman{
		int q, a;
		DiffieHellman(){
			q = getRandomPrime();
			a = primitiveRoot(q);
		}

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

		ArrayList<Integer> primeFactors(int n){
			ArrayList<Integer> factors = new ArrayList();
			if(n % 2 == 0) factors.add(2);
			while(n % 2 == 0) n /= 2;
			for(int i = 3; i <= Math.sqrt(n); i+=2){
				if(n % i == 0) factors.add(i);
				while(n % i == 0) n /= i;
			}
			if(n > 2) factors.add(n);
			return factors;
		}

		int primitiveRoot(int q){
			int phi = q-1;
			ArrayList<Integer>factors = primeFactors(phi);
			for(int i = 2; i < phi; i++){
				int flag = 0;
				for(int j = 0; j < factors.size(); j++){
					if(powerModm(i, phi/factors.get(j), q) == 1){
						flag = 1;
						break;
					}
				}
				if(flag == 0)
					return i;
			}
			return -1;
		}

		int privateKey(int X){
			return powerModm(a, X, q);
		}

		int secretKey(int X, int Y){
			return powerModm(Y, X, q);
		}

	}

	public static void main(String args[]){
		DiffieHellman manager = new DiffieHellman();
		int Xa,Xb,Ya,Yb;
		Random rand = new Random();
		Xa = rand.nextInt(1000);
		Xb = rand.nextInt(1000);
		System.out.println("public key at A: "+Xa);
		System.out.println("public key at B: "+Xb);
		Ya = manager.privateKey(Xa);
		Yb = manager.privateKey(Xb);
		System.out.println("Secret key calculated at A: "+manager.secretKey(Xa, Yb));
		System.out.println("Secret key calculated at B: "+manager.secretKey(Xb, Ya));
	}
}