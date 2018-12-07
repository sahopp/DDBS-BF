import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Experiment {

    private static final String COMMA_DELIMITER = ";";

    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");

        FileWriter fileWriter = null;
        try {


            final int NUMBER_OF_ROUNDS = 10;
            final int Min_VAL = 1;
            final int MAX_VAL = 27;

            int m;
            long a;
            long time;
            long[][] measures = new long[NUMBER_OF_ROUNDS][MAX_VAL+1];

            for (int round = 1; round < NUMBER_OF_ROUNDS + 1; round++) {
                System.out.println("\nROUND " + round);

                for (int i = Min_VAL; i < MAX_VAL + 1; i++) {
                    m = (int) Math.pow(2, i);
                    System.out.println("i = " + i + "  m = " + m);
                    a = System.currentTimeMillis();
                    master.doBFJoin(m);
                    time = System.currentTimeMillis() - a;
                    measures[round-1][i-1] = time;
                }
                System.out.println("Naive");
                a = System.currentTimeMillis();
                master.doNaiveJoin();
                time = System.currentTimeMillis() - a;
                measures[round-1][MAX_VAL] = time;
            }

            System.out.println("Writing file...");
            fileWriter = new FileWriter("MeasuresDDBS.csv");

            for (int round = 1; round < NUMBER_OF_ROUNDS + 1; round++) {
                for (int i = Min_VAL; i < MAX_VAL+2; i++) {
                    fileWriter.append(String.valueOf(measures[round-1][i-1]));
                    fileWriter.append(COMMA_DELIMITER);
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                assert fileWriter != null;
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}
