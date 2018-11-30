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

    private static final String FILE_HEADER = "1;2;3;4;5;6;7;8;9;10";

    public static void main (String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        MasterServerInterface master = (MasterServerInterface) Naming.lookup("rmi://localhost:5096/master");

        FileWriter fileWriter = null;
        try {


            final int NUMBER_OF_ROUNDS = 10;
            final int Min_VAL = 1;
            final int MAX_VAL = 2;
            int m;
            long a;
            long time;
            long[][] measures = new long[NUMBER_OF_ROUNDS][MAX_VAL+1];
            ArrayList<DataTuple3> DataBF;


            for (int round = 1; round < NUMBER_OF_ROUNDS + 1; round++) {
                System.out.println("\nROUND " + round);

                for (int i = Min_VAL; i < MAX_VAL + 1; i++) {
                    m = 1000000*i;
                    System.out.println("i = " + i + "  m = " + m);
                    a = System.currentTimeMillis();
                    DataBF = master.doBFJoin(m);
                    System.out.println(DataBF.size());
                    time = System.currentTimeMillis() - a;
                    measures[round-1][i-1] = time;
                    DataBF = null;
                }
                System.out.println("Naive");
                a = System.currentTimeMillis();
                DataBF = master.doNaiveJoin();
                time = System.currentTimeMillis() - a;
                measures[round-1][MAX_VAL] = time;
                System.out.println(DataBF.size());
                DataBF = null;



            }

            System.out.println("Writing file...");
            fileWriter = new FileWriter("MeasuresDDBSMio.csv");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header

            fileWriter.append(NEW_LINE_SEPARATOR);

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
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}
