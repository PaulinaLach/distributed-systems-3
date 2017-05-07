package clientside;

import laboratory.ExamRecord;
import laboratory.Examination;
import laboratory.LaboratoryService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.transport.TTransportException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by paulina on 5/3/17.
 */
public class Doctor {

    LaboratoryService.Client client;
    LaboratoryService.AsyncClient async_client;

    public Doctor() {
        try {
            this.client = new JavaClient().getClient();
            this.async_client = new JavaAsyncClient().getClient();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }


    public void doctorOptions() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("Options:");
                System.out.println("q/quit/exit - leave");
                System.out.println(" ");

                System.out.println("order patient_id exam_date doctor_to_order");
                System.out.println("Order an examination for a patient.");
                System.out.println("Must provide parameters to examine.");
                System.out.println("To stop type \"stop\".");
                System.out.println("Result has to be set to None");
                System.out.println("param result unit");
                System.out.println(" ");

                System.out.println("list exam_id param - list patients' results");
                System.out.println("If exam_id=None - search by exam parameter.");
                System.out.println("Otherwise put param=None - will search one examination by id.");
                System.out.println(" ");

                String line = in.readLine();

                if (line.startsWith("q") || line.startsWith("quit") || line.startsWith("exit"))
                    break;

                else if (line.startsWith("order")) {

                    String [] splittedLine = line.split(" ");
                    String patient_id = splittedLine[1];
                    String exam_date = splittedLine[2];
                    String doctor_to_order = splittedLine[3];
                    List<ExamRecord> exam_records = new ArrayList<ExamRecord>();

                    while (true) {
                        String exam_record_line = in.readLine();

                        if (exam_record_line.startsWith("stop")) {
                            break;
                        }
                        else {
                            String [] splittedRecord = exam_record_line.split(" ");
                            String param = splittedRecord[0];
                            String result = splittedRecord[1];
                            String unit = splittedRecord[2];

                            ExamRecord examRecord = new ExamRecord(param, result, unit);
                            exam_records.add(examRecord);
                        }
                    }

                    this.order_exam(patient_id, exam_date, doctor_to_order, exam_records);

                }
                else if (line.startsWith("list")) {

                    String [] splittedLine = line.split(" ");
                    String exam_id = splittedLine[1];
                    String param = splittedLine[2];

                    this.list_results_doc(exam_id, param);

                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean order_exam(String patient_id, String exam_date, String doctor_to_order,
                               List<ExamRecord> exam_records) {
        System.out.println("Exam ordered.");
        try {
            this.client.order_exam(patient_id, exam_date, doctor_to_order, exam_records);
        } catch (TException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void list_results_doc(String exam_id, String param) throws TException {
        System.out.println("Search proceeded.");
        this.async_client.list_results_doc(exam_id, param, new AsyncMethodCallback<List<Examination>>() {
            @Override
            public void onComplete(List<Examination> examinations) {
                System.out.println(examinations);
            }

            @Override
            public void onError(Exception e) {
                System.out.println(e);
            }
        });
    }

    public static void main(String [] args) throws Exception {
        new Doctor().doctorOptions();
    }
}
