package br.patrimony.system.repositories;

import br.patrimony.system.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PatrimonyRepositoryTest {
    @Autowired
    private PatrimonyRepository patrimonyRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("encontrar patrimonio pelo nome")
    void findByObject() {
        var predio = new Building("predio", null);
        buildingRepository.save(predio);

        var departamento = new Department("teste", predio, null);
        departmentRepository.save(departamento);

        var user = new User("login", "senha", UserRole.ADMIN);
        userRepository.save(user);

        var objeto = new Patrimony("teste", Category.ELECTRONICS, predio, departamento, user.getRole(), LocalDate.now(),
                LocalDate.now().plusYears(1));
        patrimonyRepository.save(objeto);

        var resultado = patrimonyRepository.findByObject("teste");
        assertNotNull(resultado);
        assertEquals("teste", resultado.getObject());
    }
}