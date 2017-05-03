package clientside;

import laboratory.ExamRecord;
import laboratory.Examination;
import laboratory.LaboratoryService;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulina on 5/3/17.
 */
public class Patient {

    LaboratoryService.Client client;

    public Patient() {
        try {
            this.client = new JavaClient().getClient();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }


    public void patientOptions() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("Options:");
                System.out.println("q/quit/exit - leave");
                System.out.println(" ");

                System.out.println("list patient_id - list your results");
                System.out.println("patient_id must be your unique id.");
                System.out.println(" ");

                String line = in.readLine();

                if (line.startsWith("q") || line.startsWith("quit") || line.startsWith("exit"))
                    break;

                else if (line.startsWith("list")) {

                    String [] splittedLine = line.split(" ");
                    String patient_id = splittedLine[1];

                    this.list_results_pat(patient_id);

                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Examination> list_results_pat(String patient_id) throws TException {
        System.out.println("Search proceeded.");
        return this.client.list_results_pat(patient_id);
    }

    public static void main(String [] args) throws Exception {
        new Patient().patientOptions();
    }

}
