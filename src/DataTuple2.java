
public class DataTuple2 extends DataTuple {

    private int A2;
    private String A3;

    public DataTuple2(int A1, int A2, String A3) {
        super(A1);
        this.A2 = A2;
        this.A3 = A3;
    }

    public int getA2() {
        return A2;
    }

    public String getA3() {
        return A3;
    }

    public void print() {
        System.out.println("( " + this.A1 + " , " + this.A2 + " , " + this.A3 + " )");
    }
}
