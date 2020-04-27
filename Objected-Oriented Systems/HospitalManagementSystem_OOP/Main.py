import datetime, time, random


class Hospital:
    def __init__(self, administrative_staff: list, operational_staff: list, technical_staff: list):
        self.administrative_staff = administrative_staff
        self.operational_staff = operational_staff
        self.technical_staff = technical_staff
        self.patients_record = {}
        self.patients_queue = []

    def create_new_patient(self, patient, op_staff_member):
        self.patients_record[patient] = op_staff_member

    def new_patient_arrives(self, patient):
        admin_staff_member = random.choice(self.administrative_staff)
        op_staff_member = random.choice(self.operational_staff)
        admin_staff_member.register_patient(self, patient, op_staff_member)
        self.patients_queue.append(patient)
        return

    def serve_patient(self):
        patient = self.patients_queue.pop(0)
        staff_assigned = self.patients_record[patient]
        staff_assigned.checkup(patient)
        self.patient_leaves(patient)

    def patient_leaves(self, patient):
        admin_staff_member = random.choice(self.administrative_staff)
        admin_staff_member.checkout_patient(patient)


class Shift:
    shift_number = None
    staff_members = []

    def __init__(self, shift_number):
        if shift_number < 0:
            shift_number = -shift_number
        self.shift_number = (shift_number + 3) % 3

    def get_shift_num(self):
        if self.shift_number == 1:
            print("Shift 1. 7AM to 3PM")
        elif self.shift_number == 2:
            print("Shift 2. 3PM to 11PM")
        elif self.shift_number == 3:
            print("Shift 3. 11PM to 7AM")
        return self.shift_number

    def add_staff_member(self, staff_member):
        self.staff_members.append(staff_member)

    def get_staff_members(self):
        return self.staff_members

    # Display Shift
    def display_shift_details(self):
        print("Shift {}".format(self.shift_number))
        for v in self.staff_members:
            v.displayStaffMemberDetails()


class Person:
    def __init__(self, person_id, name, address, contact_number):
        self.person_id = person_id
        self.name = name
        self.address = address
        self.contact_number = contact_number

    def get_id(self):
        return self.person_id

    def get_name(self):
        return self.name

    def get_address(self):
        return self.address

    def get_contact_number(self):
        return self.contact_number


class Patient(Person):

    def __init__(self, patient_id, name, address, contact_number):
        super(Patient, self).__init__(patient_id, name, address, contact_number)
        self.diagnosis = ""
        self.bill = 0

    def set_daignosis(self, diagnosis):
        self.diagnosis = diagnosis

    def set_bill(self, bill):
        self.bill = bill

    def get_diagnosis(self):
        return self.diagnosis

    def get_bill(self):
        return self.bill

    def display_patient_details(self):
        patient_details = ""
        patient_details += str(self.get_id()) + '\t'
        patient_details += self.get_name() + '\t'
        patient_details += self.get_address() + '\t'
        patient_details += str(self.get_contact_number()) + '\t'
        patient_details += self.get_diagnosis() + '\t'
        patient_details += str(self.get_bill())

        return  patient_details
        # print(patient_details)


class StaffMember(Person):
    def __init__(self, staff_id, name, address, contact_number, salary, shift: Shift):
        super(StaffMember, self).__init__(staff_id, name, address, contact_number)
        self.salary = salary
        self.shift = shift

    def get_salary(self):
        return self.salary

    def get_shift(self):
        return self.shift

    def punch_attendance(self):
        if self in self.shift.get_staff_members():
            return
        self.shift.add_staff_member(self)
        print("Attendance marked. Added to shift {}.".format(self.get_shift().get_shift_num()))

    def display_staff_member_details(self):
        details = ""
        details += str(self.get_id()) + '\t'
        details += self.get_name() + '\t'
        details += self.get_address() + '\t'
        details += str(self.get_contact_number()) + '\t'
        details += str(self.get_salary()) + '\t'
        details += str(self.shift.get_shift_num()) + '\t'
        print(details)


class AdministrativeStaffMember(StaffMember):
    def __init__(self, staff_id, name, address, contact_number, salary, shift):
        super(AdministrativeStaffMember, self).__init__(staff_id, name, address, contact_number, salary,
                                                         shift)

    def register_patient(self, hospital: Hospital, patient, operational_staff_member):
        hospital.create_new_patient(patient, operational_staff_member)
        print("Patient: {} registered by {}.".format(patient.get_name(), self.get_name()))
        return

    def generate_bill(self, patient):
        disease1 = "Cancer"
        disease2 = "Diabetes"
        disease3 = "Fever"

        if disease1 in patient.diagnosis:
            patient.set_bill(20000)
        elif disease2 in patient.diagnosis:
            patient.set_bill(4000)
        elif disease3 in patient.diagnosis:
            patient.set_bill(1000)

        print("Bill generated for patient {}. Generated by: {}".format(patient.get_name(), self.get_name()))

    def checkout_patient(self, patient):
        self.generate_bill(patient)


class OpeationalStaffMemeber(StaffMember):
    def __init__(self, staff_id, name, address, contact_number, salary, shift, post):
        super(OpeationalStaffMemeber, self).__init__(staff_id, name, address, contact_number, salary, shift)
        self.post = post
        self.patient_under_checkup = None

    def checkup(self, patient):
        print("Treatement started b {} {}. Patient details: {} \n".format(self.post,
                                                                          self.get_name(),
                                                                          patient.display_patient_details()))
        diseases = ["Cancer", "Diabetes", "Fever"]
        disease = random.choice(diseases)
        patient.set_daignosis(disease)
        print("Patient: {}. Disease diagnosed: {}".format(patient.get_name(), disease))


class Machine:
    def __init__(self, name):
        self.name = name


class TechnicalStaffMember(StaffMember):
    def __init__(self, staff_id, name, address, contact_number, salary, shift, machines):
        super(TechnicalStaffMember, self).__init__(staff_id, name, address, contact_number, salary, shift)
        self.machines = machines

    def fix_defect(self):
        for machine in self.machines:
            print("Fixing defect of {} machine.".format(machine.name))
            time.sleep(1)


if __name__ == "__main__":
    shift1 = Shift(1)
    shift2 = Shift(2)
    shift3 = Shift(3)

    patient_names = ["John", "Ali", "Shamima", "Ali2", "Martin", "Syed"]
    patients = []
    for i in range(len(patient_names)):
        patient = Patient(i, patient_names[i], patient_names[i]*2, int(str(i)*3))
        patients.append(patient)

    admin_staff_members = []
    operational_staff_members = []
    tech_staff_members = []

    admin_staff_member = AdministrativeStaffMember(100, 'A', 'AAA', 100100, 10000, shift1)
    admin_staff_members.append(admin_staff_member)
    admin_staff_member = AdministrativeStaffMember(200, 'B', 'BBB', 200200, 10000, shift2)
    admin_staff_members.append(admin_staff_member)
    admin_staff_member = AdministrativeStaffMember(300, 'C', 'CCC', 300300, 10000, shift3)
    admin_staff_members.append(admin_staff_member)
    admin_staff_member = AdministrativeStaffMember(400, 'D', 'DDD', 400400, 10000, shift1)
    admin_staff_members.append(admin_staff_member)
    admin_staff_member = AdministrativeStaffMember(500, 'E', 'EEE', 500500, 10000, shift2)
    admin_staff_members.append(admin_staff_member)
    admin_staff_member = AdministrativeStaffMember(600, 'F', 'FFF', 600600, 10000, shift3)
    admin_staff_members.append(admin_staff_member)
    admin_staff_member = AdministrativeStaffMember(700, 'G', 'GGG', 700700, 10000, shift1)
    admin_staff_members.append(admin_staff_member)

    operational_staff_member = OpeationalStaffMemeber(110, 'O1', 'O100', 110110, 500000, shift1, 'DOCTOR')
    operational_staff_members.append(operational_staff_member)
    operational_staff_member = OpeationalStaffMemeber(120, 'O2', 'O200', 120120, 500000, shift1, 'NURSE')
    operational_staff_members.append(operational_staff_member)
    operational_staff_member = OpeationalStaffMemeber(210, 'O3', 'O300', 210210, 500000, shift2, 'SURGEON')
    operational_staff_members.append(operational_staff_member)
    operational_staff_member = OpeationalStaffMemeber(220, 'O4', 'O400', 220220, 500000, shift2, 'DOCTOR')
    operational_staff_members.append(operational_staff_member)
    operational_staff_member = OpeationalStaffMemeber(310, 'O5', 'O500', 310310, 500000, shift3, 'DOCTOR')
    operational_staff_members.append(operational_staff_member)
    operational_staff_member = OpeationalStaffMemeber(320, 'O6', 'O600', 320320, 500000, shift3, 'NURSE')
    operational_staff_members.append(operational_staff_member)

    machines = [Machine("EEG"), Machine("ECG")]

    tech_staff_member = TechnicalStaffMember(111, 'T1', 'T1111', 111111, 5000, shift1, machines)
    tech_staff_members.append(tech_staff_member)
    tech_staff_member = TechnicalStaffMember(222, 'T2', 'T2222', 222222, 5000, shift2, machines)
    tech_staff_members.append(tech_staff_member)
    tech_staff_member = TechnicalStaffMember(333, 'T3', 'T3333', 333333, 5000, shift3, machines)
    tech_staff_members.append(tech_staff_member)

    hospital = Hospital(admin_staff_members, operational_staff_members, tech_staff_members)

    for patient in patients:
        hospital.new_patient_arrives(patient)

    hospital.serve_patient()
    hospital.serve_patient()
    hospital.serve_patient()

    tech_staff_members[0].fix_defect()

    hospital.serve_patient()
    hospital.serve_patient()
    hospital.serve_patient()

    tech_staff_members[1].fix_defect()