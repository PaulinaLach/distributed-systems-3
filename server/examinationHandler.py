import glob
import random
import sys
sys.path.append('../gen-py')
# sys.path.insert(0, glob.glob('../../lib/py/build/lib*')[0])

from bson.objectid import ObjectId
from laboratory import LaboratoryService
from laboratory.ttypes import Examination, ExamRecord
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
            "records": list(map(lambda x: {'param': x.param, 'result': x.result, 'unit': x.unit}, exam_records))
        }
        examinations = db.examinations
        exam_id = examinations.insert_one(examination).inserted_id

        return True

    def fill_results(self, exam_id):
        print("Results are ready.")

        examination = db.examinations.find_one({"_id": ObjectId(exam_id)})

        for exam_record in examination["records"]:
            exam_record["result"] = str(random.uniform(1, 10))

        db.examinations.update_one({"_id": ObjectId(exam_id)}, examination)

        return True

    def exam_json_to_record(self, examination):
        e = Examination()
        e.exam_id = str(examination['_id'])
        e.doctor_to_order = examination['doctor']
        e.exam_date = examination['date']
        e.patient_id = examination['patient']
        e.exam_records = list(
            map(lambda x: ExamRecord(param=x['param'], result=x['result'], unit=x['unit']), examination['records']))
        return e

    def list_results_doc(self, exam_id, param):
        print("List was sent to the doctor.")

        results_list = []

        if exam_id == "None":
            for examination in db.examinations.find():
                for exam_record in examination['records']:
                    if exam_record["param"] == param:
                        e = self.exam_json_to_record(examination)
                        results_list.append(e)
        else:
            examination = db.examinations.find_one({"_id": ObjectId(exam_id)})
            if examination:
                results_list.append(self.exam_json_to_record(examination))

        return results_list

    def list_results_pat(self, patient_id):
        print("List was sent to the patient.")

        results_list = []

        for examination in db.examinations.find():
            if examination["patient"] == patient_id:
                results_list.append(self.exam_json_to_record(examination))

        return results_list









