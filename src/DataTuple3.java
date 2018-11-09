
public class DataTuple3 extends DataTuple {

    private String A2;
    private String A3;
    private int A4;
    private String A5;

    public DataTuple3(int A1, String A2, String A3, int A4, String A5) {
        super(A1);
        this.A2 = A2;
        this.A3 = A3;
        this.A4 = A4;
        this.A5 = A5;
    }

    public String getA2() {
        return A2;
    }

    public String getA3() {
        return A3;
    }

    public void print(){
        System.out.println("( " + this.A1 + " , " + this.A2 + " , " + this.A3 + " , " + this.A4 + " , " + this.A5 + " )");
    }
}
