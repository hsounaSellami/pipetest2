package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testCreateAndFindDepartment() {
        Department dept = new Department();
        dept.setName("Computer Science");
        dept.setLocation("Building A");
        dept.setPhone("123456789");
        dept.setHead("Dr. Smith");

        Department saved = departmentRepository.save(dept);

        Optional<Department> found = departmentRepository.findById(saved.getIdDepartment());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Computer Science");
    }
}
