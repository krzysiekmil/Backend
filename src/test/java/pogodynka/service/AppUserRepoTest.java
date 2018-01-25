package pogodynka.service;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import pogodynka.model.AppUser;
import pogodynka.repository.AppUserRepository;
import pogodynka.repository.CityRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


//@RunWith(SpringRunner.class)
//@Transactional
//@SpringBootTest()
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class AppUserRepoTest {

//  @Autowired
//  protected TestEntityManager testEntityManager;

  @Autowired
  protected AppUserRepository appUserRepository;

  @Autowired
  protected CityRepository cityRepository;


  //  @Test
  public void shouldFindByUsername() throws Exception {

//  testEntityManager.persist(new AppUser("asd","password",null,null));
    AppUser appUserByUsername = this.appUserRepository.findByUsername("asd2");
    assertThat(appUserByUsername).isNotNull();
    assertThat(appUserByUsername).hasFieldOrPropertyWithValue("username", "asd2");
  }

  //  @Test
  public void shouldCheckByUserIdAndCityId() throws Exception {
    List testList = appUserRepository.check(202l, 26l);
    assertThat(testList).isNullOrEmpty();
    assertThat(testList.size()).isEqualTo(0);
    assertThat(appUserRepository.findByUsername("asd2").getCities().size()).isEqualTo(1);
    assertThat(appUserRepository.check(5L, 1L).size()).isEqualTo(1);
  }

  //  @Test
  public void addNewUser() {
    appUserRepository.save(new AppUser("test", "test", Collections.emptySet(), Lists.emptyList()));
    assertThat(appUserRepository.findByUsername("test")).isNotNull();
    assertThat(appUserRepository.findByUsername("test").getId()).isGreaterThanOrEqualTo(1l);
  }

  //  @Test
  public void removeCityConnectionToUser() {
    try {
      appUserRepository.deleteCity(5L, 1L);

    } catch (JpaSystemException e) {
      assertThat(appUserRepository.findByUsername("asd2").getCities().size()).isEqualTo(0);
    }
  }

  //  @Test
  public void deleteAllCityReferences() {
    try {
      appUserRepository.deleteAllCityReferences(1L);
    } catch (JpaSystemException e) {
      assertThat(appUserRepository.findByUsername("asd2").getCities().size()).isEqualTo(0);
      assertThat(appUserRepository.check(5L, 1L)).isNullOrEmpty();
    }
  }
}
