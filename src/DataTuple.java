import java.io.Serializable;

public abstract class DataTuple implements Serializable, Comparable {

    protected int A1;

    public DataTuple(int A1) {
        this.A1 = A1;

    }

    public int getA1() {
        return A1;
    }

    public abstract void print();

    @Override
    public int compareTo(Object o){
        return Integer.compare(this.A1, ((DataTuple) o).getA1());
    }
}
