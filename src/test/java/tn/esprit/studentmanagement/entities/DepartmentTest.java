package tn.esprit.studentmanagement.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void testDepartmentCreation() {
        Department department = new Department();
        department.setName("Computer Science");
        department.setLocation("Building A");
        department.setPhone("123456789");
        department.setHead("Dr. Smith");

        // Vérification
        assertEquals("Computer Science", department.getName());
        assertEquals("Building A", department.getLocation());
        assertEquals("123456789", department.getPhone());
        assertEquals("Dr. Smith", department.getHead());
    }
}
