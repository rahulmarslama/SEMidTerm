package miu.rahul;

import miu.rahul.data.MockDatabase;
import miu.rahul.service.AppointmentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ADSApp {

    private static AppointmentService appointmentService;

    public static void main(String[] args) {
        setupDependenciesAndData();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to Adventist Dental Surgeries (ADS) System");

        while (!exit) {
            System.out.println("\nOptions:");
            System.out.println("1. Book Appointment (Clerk/Patient)");
            System.out.println("2. Cancel Appointment (Patient)");
            System.out.println("3. Change Appointment (Patient)");
            System.out.println("4. View Appointments by Dentist");
            System.out.println("5. View Appointments by Patient");
            System.out.println("6. View Patients");
            System.out.println("7. View Dentists");
            System.out.println("8. View All Appointments as JSON");
            System.out.println("9. View Quarterly Upcoming Appointments as JSON (Director)");
            System.out.println("10. Exit");

            int choice = readInt(scanner, "Select an option: ");

            switch (choice) {
                case 1:
                    Long patientIdForBooking = readLong(scanner, "Enter Patient ID: ");
                    Long dentistIdForBooking = readLong(scanner, "Enter Dentist ID: ");
                    Long surgeryIdForBooking = readLong(scanner, "Enter Surgery ID: ");
                    LocalDateTime appointmentDateTime = readDateTime(scanner, "Enter Date and Time (YYYY-MM-DDTHH:MM, e.g., 2026-06-15T10:00): ");
                    appointmentService.bookAppointment(patientIdForBooking, dentistIdForBooking, surgeryIdForBooking, appointmentDateTime);
                    break;
                case 2:
                    Long appointmentIdForCancel = readLong(scanner, "Enter Appointment ID to cancel: ");
                    appointmentService.cancelAppointment(appointmentIdForCancel);
                    break;
                case 3:
                    Long appointmentIdForChange = readLong(scanner, "Enter Appointment ID to change: ");
                    Long newSurgeryId = readLong(scanner, "Enter New Surgery ID: ");
                    LocalDateTime newDateTime = readDateTime(scanner, "Enter New Date and Time (YYYY-MM-DDTHH:MM, e.g., 2026-07-15T10:00): ");
                    appointmentService.changeAppointment(appointmentIdForChange, newSurgeryId, newDateTime);
                    break;
                case 4:
                    Long dentistId = readLong(scanner, "Enter Dentist ID: ");
                    appointmentService.viewAppointmentsByDentist(dentistId);
                    break;
                case 5:
                    Long patientId = readLong(scanner, "Enter Patient ID: ");
                    appointmentService.viewAppointmentsByPatient(patientId);
                    break;
                case 6:
                    appointmentService.viewAllPatients();
                    break;
                case 7:
                    appointmentService.viewAllDentists();
                    break;
                case 8:
                    appointmentService.displayAllAppointmentsAsJson();
                    break;
                case 9:
                    appointmentService.displayQuarterlyUpcomingAppointments();
                    break;
                case 10:
                    exit = true;
                    System.out.println("Exiting System...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 10.");
            }
        }
        scanner.close();
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static Long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ID number.");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDateTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date/time. Please use format YYYY-MM-DDTHH:MM, for example 2026-07-15T10:00.");
            }
        }
    }

    private static void setupDependenciesAndData() {
        appointmentService = new AppointmentService();
    }
}
