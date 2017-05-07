package clientside;

import laboratory.Examination;
import laboratory.LaboratoryService;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by paulina on 5/3/17.
 */
public class Technician {

    LaboratoryService.Client client;

    public Technician() {
        try {
            this.client = new JavaClient().getClient();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public void technicianOptions() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("Options:");
                System.out.println("q/quit/exit - leave");
                System.out.println(" ");

                System.out.println("fill exam_id - fill results of an exam.");
                System.out.println(" ");

                String line = in.readLine();

                if (line.startsWith("q") || line.startsWith("quit") || line.startsWith("exit"))
                    break;

                else if (line.startsWith("fill")) {

                    String [] splittedLine = line.split(" ");
                    String exam_id = splittedLine[1];

                    System.out.println(this.fill_results(exam_id));

                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean fill_results(String exam_id) throws TException {
        System.out.println("Results filled.");
        return this.client.fill_results(exam_id);
    }

    public static void main(String [] args) throws Exception {
        new Technician().technicianOptions();
    }

}
