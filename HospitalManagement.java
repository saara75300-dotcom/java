```java
import java.io.*;
import java.util.*;

public class HospitalManagement{

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();
    static ArrayList<Billing> bills = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        int choice;
        do {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Add Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Generate Bill");
            System.out.println("6. View All Data");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
switch (choice) {
    case 1:
        addPatient();
        break;
    case 2:
        viewPatients();
        break;
    case 3:
        addDoctor();
        break;
    case 4:
        scheduleAppointment();
        break;
    case 5:
        generateBill();
        break;
    case 6:
        viewAll();
        break;
    case 7:
        saveData();
        System.out.println("Exiting. Data saved.");
        break;
    default:
        System.out.println("Invalid choice!");
}

           
                 
        } while (choice != 7);
    }

    static void addPatient() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Gender: ");
        String gender = sc.next();
        System.out.print("Enter Disease: ");
        String disease = sc.next();
        patients.add(new Patient(id, name, age, gender, disease));
        System.out.println("Patient added successfully!");
    }

    static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("\n--- Patient List ---");
        for (Patient p : patients) {
            System.out.println(p);
        }
    }

    static void addDoctor() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Specialization: ");
        String specialization = sc.next();
        doctors.add(new Doctor(id, name, specialization));
        System.out.println("Doctor added successfully!");
    }

    static void scheduleAppointment() {
        System.out.print("Enter Appointment ID: ");
        int appId = sc.nextInt();
        System.out.print("Enter Patient ID: ");
        int pId = sc.nextInt();
        System.out.print("Enter Doctor ID: ");
        int dId = sc.nextInt();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = sc.next();

        boolean pExists = patients.stream().anyMatch(p -> p.id == pId);
        boolean dExists = doctors.stream().anyMatch(d -> d.id == dId);

        if (!pExists) {
            System.out.println("Patient ID not found.");
            return;
        }
        if (!dExists) {
            System.out.println("Doctor ID not found.");
            return;
        }

        appointments.add(new Appointment(appId, pId, dId, date));
        System.out.println("Appointment scheduled successfully!");
    }

    static void generateBill() {
        System.out.print("Enter Bill ID: ");
        int billId = sc.nextInt();
        System.out.print("Enter Appointment ID: ");
        int appId = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        boolean appExists = appointments.stream().anyMatch(a -> a.appointmentId == appId);
        if (!appExists) {
            System.out.println("Appointment ID not found.");
            return;
        }

        bills.add(new Billing(billId, appId, amount));
        System.out.println("Bill generated successfully!");
    }

    static void viewAll() {
        System.out.println("\n--- Patients ---");
        viewPatients();
        System.out.println("\n--- Doctors ---");
        if (doctors.isEmpty()) System.out.println("No doctors found.");
        else doctors.forEach(System.out::println);
        System.out.println("\n--- Appointments ---");
        if (appointments.isEmpty()) System.out.println("No appointments found.");
        else appointments.forEach(System.out::println);
        System.out.println("\n--- Bills ---");
        if (bills.isEmpty()) System.out.println("No bills found.");
        else bills.forEach(System.out::println);
    }

    static void loadData() {
        patients = loadPatients("patients.txt");
        doctors = loadDoctors("doctors.txt");
        appointments = loadAppointments("appointments.txt");
        bills = loadBills("bills.txt");
    }

    static void saveData() {
        savePatients("patients.txt", patients);
        saveDoctors("doctors.txt", doctors);
        saveAppointments("appointments.txt", appointments);
        saveBills("bills.txt", bills);
    }

    // Loading patients from text file
    static ArrayList<Patient> loadPatients(String filename) {
        ArrayList<Patient> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty patient list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: id,name,age,gender,disease
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String gender = parts[3];
                    String disease = parts[4];
                    list.add(new Patient(id, name, age, gender, disease));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void savePatients(String filename, ArrayList<Patient> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Patient p : list) {
                pw.println(p.id + "," + p.name + "," + p.age + "," + p.gender + "," + p.disease);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    // Loading doctors from text file
    static ArrayList<Doctor> loadDoctors(String filename) {
        ArrayList<Doctor> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty doctor list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: id,name,specialization
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String specialization = parts[2];
                    list.add(new Doctor(id, name, specialization));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void saveDoctors(String filename, ArrayList<Doctor> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Doctor d : list) {
                pw.println(d.id + "," + d.name + "," + d.specialization);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    // Loading appointments from text file
    static ArrayList<Appointment> loadAppointments(String filename) {
        ArrayList<Appointment> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty appointment list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: appointmentId,patientId,doctorId,date
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int appId = Integer.parseInt(parts[0]);
                    int pId = Integer.parseInt(parts[1]);
                    int dId = Integer.parseInt(parts[2]);
                    String date = parts[3];
                    list.add(new Appointment(appId, pId, dId, date));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void saveAppointments(String filename, ArrayList<Appointment> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Appointment a : list) {
                pw.println(a.appointmentId + "," + a.patientId + "," + a.doctorId + "," + a.date);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    // Loading bills from text file
    static ArrayList<Billing> loadBills(String filename) {
        ArrayList<Billing> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty billing list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: billId,appointmentId,amount
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int billId = Integer.parseInt(parts[0]);
                    int appId = Integer.parseInt(parts[1]);
                    double amount = Double.parseDouble(parts[2]);
                    list.add(new Billing(billId, appId, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void saveBills(String filename, ArrayList<Billing> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Billing b : list) {
                pw.println(b.billId + "," + b.appointmentId + "," + b.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }
}

// Model classes

class Patient {
    int id;
    String name;
    int age;
    String gender;
    String disease;

    public Patient(int id, String name, int age, String gender, String disease) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    @Override
    public String toString() {
        return "Patient [ID=" + id + ", Name=" + name + ", Age=" + age +
                ", Gender=" + gender + ", Disease=" + disease + "]";
    }
}

class Doctor {
    int id;
    String name;
    String specialization;

    public Doctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor [ID=" + id + ", Name=" + name + ", Specialization=" + specialization + "]";
    }
}

class Appointment {
    int appointmentId;
    int patientId;
    int doctorId;
    String date;

    public Appointment(int appointmentId, int patientId, int doctorId, String date) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment [ID=" + appointmentId + ", PatientID=" + patientId +
                ", DoctorID=" + doctorId + ", Date=" + date + "]";
    }
}

class Billing {
    int billId;
    int appointmentId;
    double amount;

    public Billing(int billId, int appointmentId, double amount) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Billing [BillID=" + billId + ", AppointmentID=" + appointmentId +
                ", Amount=" + amount + "]";
    }
}vimport java.io.*;
import java.util.*;

public class HospitalManagement{

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();
    static ArrayList<Billing> bills = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        int choice;
        do {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Add Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Generate Bill");
            System.out.println("6. View All Data");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
switch (choice) {
    case 1:
        addPatient();
        break;
    case 2:
        viewPatients();
        break;
    case 3:
        addDoctor();
        break;
    case 4:
        scheduleAppointment();
        break;
    case 5:
        generateBill();
        break;
    case 6:
        viewAll();
        break;
    case 7:
        saveData();
        System.out.println("Exiting. Data saved.");
        break;
    default:
        System.out.println("Invalid choice!");
}

           
                 
        } while (choice != 7);
    }

    static void addPatient() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Gender: ");
        String gender = sc.next();
        System.out.print("Enter Disease: ");
        String disease = sc.next();
        patients.add(new Patient(id, name, age, gender, disease));
        System.out.println("Patient added successfully!");
    }

    static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("\n--- Patient List ---");
        for (Patient p : patients) {
            System.out.println(p);
        }
    }

    static void addDoctor() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Specialization: ");
        String specialization = sc.next();
        doctors.add(new Doctor(id, name, specialization));
        System.out.println("Doctor added successfully!");
    }

    static void scheduleAppointment() {
        System.out.print("Enter Appointment ID: ");
        int appId = sc.nextInt();
        System.out.print("Enter Patient ID: ");
        int pId = sc.nextInt();
        System.out.print("Enter Doctor ID: ");
        int dId = sc.nextInt();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = sc.next();

        boolean pExists = patients.stream().anyMatch(p -> p.id == pId);
        boolean dExists = doctors.stream().anyMatch(d -> d.id == dId);

        if (!pExists) {
            System.out.println("Patient ID not found.");
            return;
        }
        if (!dExists) {
            System.out.println("Doctor ID not found.");
            return;
        }

        appointments.add(new Appointment(appId, pId, dId, date));
        System.out.println("Appointment scheduled successfully!");
    }

    static void generateBill() {
        System.out.print("Enter Bill ID: ");
        int billId = sc.nextInt();
        System.out.print("Enter Appointment ID: ");
        int appId = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        boolean appExists = appointments.stream().anyMatch(a -> a.appointmentId == appId);
        if (!appExists) {
            System.out.println("Appointment ID not found.");
            return;
        }

        bills.add(new Billing(billId, appId, amount));
        System.out.println("Bill generated successfully!");
    }

    static void viewAll() {
        System.out.println("\n--- Patients ---");
        viewPatients();
        System.out.println("\n--- Doctors ---");
        if (doctors.isEmpty()) System.out.println("No doctors found.");
        else doctors.forEach(System.out::println);
        System.out.println("\n--- Appointments ---");
        if (appointments.isEmpty()) System.out.println("No appointments found.");
        else appointments.forEach(System.out::println);
        System.out.println("\n--- Bills ---");
        if (bills.isEmpty()) System.out.println("No bills found.");
        else bills.forEach(System.out::println);
    }

    static void loadData() {
        patients = loadPatients("patients.txt");
        doctors = loadDoctors("doctors.txt");
        appointments = loadAppointments("appointments.txt");
        bills = loadBills("bills.txt");
    }

    static void saveData() {
        savePatients("patients.txt", patients);
        saveDoctors("doctors.txt", doctors);
        saveAppointments("appointments.txt", appointments);
        saveBills("bills.txt", bills);
    }

    // Loading patients from text file
    static ArrayList<Patient> loadPatients(String filename) {
        ArrayList<Patient> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty patient list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: id,name,age,gender,disease
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String gender = parts[3];
                    String disease = parts[4];
                    list.add(new Patient(id, name, age, gender, disease));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void savePatients(String filename, ArrayList<Patient> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Patient p : list) {
                pw.println(p.id + "," + p.name + "," + p.age + "," + p.gender + "," + p.disease);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    // Loading doctors from text file
    static ArrayList<Doctor> loadDoctors(String filename) {
        ArrayList<Doctor> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty doctor list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: id,name,specialization
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String specialization = parts[2];
                    list.add(new Doctor(id, name, specialization));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void saveDoctors(String filename, ArrayList<Doctor> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Doctor d : list) {
                pw.println(d.id + "," + d.name + "," + d.specialization);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    // Loading appointments from text file
    static ArrayList<Appointment> loadAppointments(String filename) {
        ArrayList<Appointment> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty appointment list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: appointmentId,patientId,doctorId,date
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int appId = Integer.parseInt(parts[0]);
                    int pId = Integer.parseInt(parts[1]);
                    int dId = Integer.parseInt(parts[2]);
                    String date = parts[3];
                    list.add(new Appointment(appId, pId, dId, date));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void saveAppointments(String filename, ArrayList<Appointment> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Appointment a : list) {
                pw.println(a.appointmentId + "," + a.patientId + "," + a.doctorId + "," + a.date);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    // Loading bills from text file
    static ArrayList<Billing> loadBills(String filename) {
        ArrayList<Billing> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(filename + " not found, starting with empty billing list.");
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: billId,appointmentId,amount
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int billId = Integer.parseInt(parts[0]);
                    int appId = Integer.parseInt(parts[1]);
                    double amount = Double.parseDouble(parts[2]);
                    list.add(new Billing(billId, appId, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return list;
    }

    static void saveBills(String filename, ArrayList<Billing> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Billing b : list) {
                pw.println(b.billId + "," + b.appointmentId + "," + b.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }
}

// Model classes

class Patient {
    int id;
    String name;
    int age;
    String gender;
    String disease;

    public Patient(int id, String name, int age, String gender, String disease) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    @Override
    public String toString() {
        return "Patient [ID=" + id + ", Name=" + name + ", Age=" + age +
                ", Gender=" + gender + ", Disease=" + disease + "]";
    }
}

class Doctor {
    int id;
    String name;
    String specialization;

    public Doctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor [ID=" + id + ", Name=" + name + ", Specialization=" + specialization + "]";
    }
}

class Appointment {
    int appointmentId;
    int patientId;
    int doctorId;
    String date;

    public Appointment(int appointmentId, int patientId, int doctorId, String date) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment [ID=" + appointmentId + ", PatientID=" + patientId +
                ", DoctorID=" + doctorId + ", Date=" + date + "]";
    }
}

class Billing {
    int billId;
    int appointmentId;
    double amount;

    public Billing(int billId, int appointmentId, double amount) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Billing [BillID=" + billId + ", AppointmentID=" + appointmentId +
                ", Amount=" + amount + "]";
    }
}
