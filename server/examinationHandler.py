import glob
import random
import sys
sys.path.append('../gen-py')
# sys.path.insert(0, glob.glob('../../lib/py/build/lib*')[0])

from laboratory import LaboratoryService
from pymongo import MongoClient

client = MongoClient()
db = client.lab_database


class ExaminationHandler:

    def __init__(self):
        pass

    def order_exam(self, patient_id, exam_date, doctor_to_order, exam_records):
        print("Examination received.")

        # create exam in database
        examination = {
            "patient": patient_id,
            "date": exam_date,
            "doctor": doctor_to_order,
            "records": exam_records
        }
        examinations = db.examinations
        exam_id = examinations.insert_one(examination).inserted_id

        return True

    def fill_results(self, exam_id):
        print("Results are ready.")

        examination = db.examinations.find_one({"_id": exam_id})
        exam_records = []

        for exam_record in examination["records"]:
            record_to_append = {
                "param_name": exam_record.param,
                "result": str(random.uniform(1, 10)),
                "unit": exam_record.unit,
            }
            exam_records.append(record_to_append)

        db.examinations.update_one({"_id": exam_id}, examination)

        return True

    def list_results_doc(self, exam_id, param):
        print("List was sent to the doctor.")

        results_list = []

        if exam_id == "None":
            for examination in db.examinations:
                for exam_record in examination["records"]:
                    if exam_record["param_name"] == param:
                        results_list.append(examination)
        else:
            examination = db.examinations.find_one({"_id": exam_id})
            results_list.append(examination)

        return results_list

    def list_results_pat(self, patient_id):
        print("List was sent to the patient.")

        results_list = []

        for examination in db.examinations:
            if examination["patient"] == patient_id:
                results_list.append(examination)

        return results_list









