package tn.esprit.studentmanagement.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    void testEnrollmentCreation() {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.of(2025, 12, 4));
        enrollment.setGrade(95.0);
        enrollment.setStatus(Status.ACTIVE);

        Student student = new Student();
        student.setFirstName("Alice");
        enrollment.setStudent(student);

        Course course = new Course();
        course.setName("Math");
        enrollment.setCourse(course);

        // Vérification
        assertEquals(LocalDate.of(2025, 12, 4), enrollment.getEnrollmentDate());
        assertEquals(95.0, enrollment.getGrade());
        assertEquals(Status.ACTIVE, enrollment.getStatus());
        assertEquals("Alice", enrollment.getStudent().getFirstName());
        assertEquals("Math", enrollment.getCourse().getName());
    }
}
