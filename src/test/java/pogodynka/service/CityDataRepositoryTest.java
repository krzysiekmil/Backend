package pogodynka.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pogodynka.repository.CityDataRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest()
public class CityDataRepositoryTest {
  @Autowired
  CityDataRepository cityDataRepository;

  @Test
  public void findTempByName() {
    assertThat(cityDataRepository.findTempByName("City").size()).isEqualTo(4);
    assertThat(cityDataRepository.findTempByName("SuperCity").size()).isEqualTo(2);
  }

  @Test
  public void findByName() {
    assertThat(cityDataRepository.findByName("City").size()).isEqualTo(4);
    assertThat(cityDataRepository.findByName("SuperCity").size()).isEqualTo(2);
  }

  @Test
  public void lastTemp() {
    assertThat(cityDataRepository.lastTemp("City").getTemp()).isEqualTo("2");
    assertThat(cityDataRepository.lastTemp("SuperCity").getTemp()).isEqualTo("3");
  }

  @Test
  public void getLastValueTemp() {
    assertThat(cityDataRepository.getLastValueTemp("City", 2l).size()).isEqualTo(2);
    assertThat(cityDataRepository.getLastValueTemp("SuperCity", 2l).size()).isEqualTo(2);
//    assertThat(cityDataRepository.getLastValueTemp("City",2l)).;
//    assertThat(cityDataRepository.getLastValueTemp("SuperCity",2l)).isEqualTo(2);
  }

}
