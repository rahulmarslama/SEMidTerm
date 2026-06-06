package miu.rahul.utility;

import miu.rahul.model.Appointment;

public class EmailUtility {
    public void sendConfirmationEmail(Appointment appointment) {
        System.out.println("----------------------------------------------------------");
        System.out.println("Email Sent to: " + appointment.getPatient().getEmail());
        System.out.println("Subject: Appointment Confirmation");
        System.out.println("Body: Dear " + appointment.getPatient().getFirstName() + ",");
        System.out.println("Your appointment with Dr. " + appointment.getDentist().getLastName() + 
                " is scheduled for " + appointment.getDateTime() + " at " + appointment.getSurgery().getName() + ".");
        System.out.println("----------------------------------------------------------");
    }
}
