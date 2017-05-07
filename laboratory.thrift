namespace java laboratory
namespace py laboratory

typedef i32 int

exception RequestException {
  1: string message,
}

struct ExamRecord {
  1: string param,
  2: string result,
  3: string unit,
}

struct Examination {
  1: string exam_id,
  2: string patient_id,
  3: string exam_date,
  4: string doctor_to_order,
  5: list<ExamRecord> exam_records,
}

struct Patient {
  1: string id,
  2: list<Examination> results,
}

service LaboratoryService {
  list<Examination> list_results_doc(1: string exam_id, 2: string param),
  list<Examination> list_results_pat(1: string patient_id),
  bool order_exam(1: string patient_id, 2: string exam_date, 3: string doctor_to_order, 4: list<ExamRecord> exam_records),
  bool fill_results(1: string exam_id) throws (1: RequestException re),
}