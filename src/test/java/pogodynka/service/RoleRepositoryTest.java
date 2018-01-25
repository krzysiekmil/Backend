package pogodynka.service;

import org.springframework.beans.factory.annotation.Autowired;
import pogodynka.repository.RoleRepository;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@Transactional
//@SpringBootTest()
public class RoleRepositoryTest {
  @Autowired
  RoleRepository roleRepository;

  //  @Test
  public void findByRoleName() {
    assertThat(roleRepository.findByRoleName("admin").getRoleName()).isEqualTo("admin");
  }
}
