package tn.esprit.studentmanagement.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void testCourseCreation() {
        Course course = new Course();
        course.setName("Computer Science");
        course.setCode("CS101");
        course.setCredit(5);
        course.setDescription("Introduction to CS");

        assertEquals("Computer Science", course.getName());
        assertEquals("CS101", course.getCode());
        assertEquals(5, course.getCredit());
        assertEquals("Introduction to CS", course.getDescription());
    }
}
