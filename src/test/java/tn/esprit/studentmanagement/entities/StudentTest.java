package tn.esprit.studentmanagement.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentCreation() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("1234567890");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));
        student.setAddress("123 Main St");

        // Vérifier que les valeurs sont correctement définies
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals("1234567890", student.getPhone());
        assertEquals(LocalDate.of(2000, 1, 1), student.getDateOfBirth());
        assertEquals("123 Main St", student.getAddress());
    }
}
