package pogodynka.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pogodynka.repository.AppUserRepository;
import pogodynka.repository.CityRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest()
public class CityRepositoryTest {
  @Autowired
  CityRepository cityRepository;
  @Autowired
  AppUserRepository appUserRepository;

  @Test
  public void existByName() {
    boolean check = cityRepository.existsByName("City");
    assertThat(check).isTrue();
  }

  @Test
  public void getAll() {
    List testList = cityRepository.findAll();
    assertThat(testList.size()).isEqualTo(2);
  }

  @Test
  public void addConnection() {
    try {
      cityRepository.addCity(5L, 2L);
    } catch (JpaSystemException e) {
      assertThat(appUserRepository.findOne(5L).getCities().size()).isEqualTo(2);
    }
  }

}
