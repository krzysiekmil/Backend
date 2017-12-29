package pogodynka.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pogodynka.repository.RoleRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest()
public class RoleRepositoryTest {
  @Autowired
  RoleRepository roleRepository;

  @Test
  public void findByRoleName() {
    assertThat(roleRepository.findByRoleName("admin").getRoleName()).isEqualTo("admin");
  }
}
