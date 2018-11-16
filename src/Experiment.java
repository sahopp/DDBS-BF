import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Experiment {

    //Delimiter used in CSV file

    private static final String COMMA_DELIMITER = ";";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "m;time";

    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("MeasuresDDBS.csv");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header

            fileWriter.append(NEW_LINE_SEPARATOR);

            final int NUMBER_OF_ROUNDS = 4;
            final int MAX_EXP = 4;
            int m;
            long a;
            long time;
            long[] measures = new long[MAX_EXP+1];


            for (int round = 1; round < NUMBER_OF_ROUNDS + 1; round++) {
                System.out.println("\nROUND " + round);

                for (int i = 0; i < MAX_EXP + 1; i++) {
                    System.out.println("m = " + i);
                    m = (int) Math.pow(2, i);
                    a = System.currentTimeMillis();
                    ArrayList<DataTuple3> DataBF = master.doBFJoin(m);
                    time = System.currentTimeMillis() - a;
                    measures[i] = (measures[i] * (round-1) + time) / round;
                }
            }

             System.out.println("Writing file...");
            for (int i = 0; i < MAX_EXP + 1; i++) {
                fileWriter.append(String.valueOf((int) Math.pow(2, i)));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(measures[i]));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }


        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }

    }
}
