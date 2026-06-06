package miu.rahul;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import miu.rahul.data.MockDatabase;
import miu.rahul.model.Appointment;
import miu.rahul.model.Patient;
import miu.rahul.utility.DateUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ADSAppTest
    extends TestCase
{
    public ADSAppTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( ADSAppTest.class );
    }

    public void testMockDatabaseHasSeededAppointments()
    {
        assertEquals(4, MockDatabase.appointments.size());
        assertEquals(4, MockDatabase.patients.size());
        assertEquals(3, MockDatabase.dentists.size());
        assertEquals(3, MockDatabase.surgeries.size());
        assertEquals(4, MockDatabase.bills.size());

        Appointment appointment = MockDatabase.appointments.get(0);
        assertEquals(Long.valueOf(1), appointment.getId());
        assertEquals(LocalDateTime.of(2026, 2, 28, 10, 5), appointment.getDateTime());
        assertNotNull(appointment.getPatient());
        assertEquals("John", appointment.getPatient().getFirstName());
        assertEquals("Smith", appointment.getPatient().getLastName());
        assertEquals("(641) 001-1234", appointment.getPatient().getPhoneNumber());
        assertEquals(LocalDate.of(1987, 1, 19), appointment.getPatient().getDateOfBirth());
        assertNotNull(appointment.getDentist());
        assertEquals("David", appointment.getDentist().getFirstName());
        assertEquals("Smith", appointment.getDentist().getLastName());
        assertNotNull(appointment.getSurgery());
        assertEquals("Fairfield Dental Surgery", appointment.getSurgery().getName());
    }

    public void testNextQuarterCalculationForJune2026()
    {
        LocalDate june2026 = LocalDate.of(2026, 6, 6);

        assertTrue(DateUtility.isInNextQuarter(june2026, LocalDate.of(2026, 7, 1)));
        assertTrue(DateUtility.isInNextQuarter(june2026, LocalDate.of(2026, 9, 30)));
        assertFalse(DateUtility.isInNextQuarter(june2026, LocalDate.of(2026, 6, 30)));
        assertFalse(DateUtility.isInNextQuarter(june2026, LocalDate.of(2026, 10, 1)));
    }

    public void testNextQuarterCalculationAcrossYearBoundary()
    {
        LocalDate november2026 = LocalDate.of(2026, 11, 6);

        assertTrue(DateUtility.isInNextQuarter(november2026, LocalDate.of(2027, 1, 1)));
        assertTrue(DateUtility.isInNextQuarter(november2026, LocalDate.of(2027, 3, 31)));
        assertFalse(DateUtility.isInNextQuarter(november2026, LocalDate.of(2026, 12, 31)));
    }

    public void testPatientCalculatesAge()
    {
        Patient patient = MockDatabase.patients.get(0);

        assertEquals(39, patient.getAge());
    }
}
