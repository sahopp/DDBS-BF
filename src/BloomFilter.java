import java.util.Arrays;
import java.util.Random;

public class BloomFilter {

    private boolean[] filter;
    private int m;
    private int k;
    private int[] a;
    private int[] b;
    private int p;


    public BloomFilter(int m, int n){
        this.filter = new boolean[m];
        this.m = m;
        this.k = (int) (Math.log(2)*m/n);
        RandomPrime randomPrime = new RandomPrime();
        this.p = randomPrime.generate(m);
        this.a = new int[k];
        this.b = new int[k];
        Random ran = new Random();
        for (int i = 0; i < k; i++) {
            a[i] = ran.nextInt(p)+1;
            b[i] = ran.nextInt(p+1);
        }
    }

    public BloomFilter(int m, int p, int[] a, int[] b){
        this.a = a;
        this.b = b;
        this.p = p;
        this.k = a.length;
        this.m = m;
        this.filter = new boolean[m];
    }

    public boolean[] getFilter() {
        return this.filter;
    }

    public void setFilter(boolean[] bf) {
        this.filter = bf;
    }

    public int getK(){
        return this.k;
    }

    public int getM() {
        return this.m;
    }

    public int getP() {
        return this.p;
    }

    public int[] getA() {
        return this.a;
    }

    public int[] getB() {
        return this.b;
    }

    public void add(int x){
        for (int i = 0; i < k; i++) {
            this.filter[(int) (((long) a[i]*x + b[i]) % p) % m] = true;
        }
    }

    public boolean check(int x){
        for (int i = 0; i < k; i++) {
            if (!this.filter[(int) (((long) a[i]*x + b[i]) % p) % m]) {return false;}
        }
        return true;
    }

    public void union(BloomFilter filter1){
        if (this.m == filter1.getM()){
            for (int i = 0; i < m; i++) {
                filter[i] = filter[i] || filter1.getFilter()[i];
            }
        }
    }

    public void intersect(BloomFilter filter1){
        if (this.m == filter1.getM()){
            for (int i = 0; i < m; i++) {
                this.filter[i] = filter[i] && filter1.getFilter()[i];
            }
        }
    }

    public void printArray(){
        int[] q = new int[m];
        for (int i = 0; i < m; i++) {
            if (filter[i]) {q[i] = 1;}
        }
        System.out.println(Arrays.toString(q));
        System.out.println(Arrays.toString(a));
        System.out.println((Arrays.toString(b)));
    }

    public void printBoolean(){
        System.out.println(Arrays.toString(filter));
    }

    public void printConfig() {
        System.out.println("m = " + m);
        System.out.println("p = " + p);
        System.out.println("k = " + k);
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("b = " + Arrays.toString(b));
        int sum = 0;
        for (int i = 0; i < m; i++) {
            if (filter[i]) {
                sum += 1;
            }
        }
        System.out.println("Number of bits set: " + sum);
        System.out.println("Fraction of bits set: " + sum*1./m);
        System.out.println("Estimated fraction of false positives: " + Math.pow(sum*1./m, 13));
    }
}
