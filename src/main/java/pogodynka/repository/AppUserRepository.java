package pogodynka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pogodynka.model.AppUser;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  public AppUser findByUsername(String username);


  @Query(value = "SELECT username, id FROM app_user ", nativeQuery = true)
  public List getUserData();

  @Query(value = "DELETE FROM user_cities WHERE user_id = ?1 AND city_id = ?2 ", nativeQuery = true)
  public AppUser deleteCity(Long user_id, Long city_id);

  @Query(value = "SELECT * FROM user_cities WHERE user_id = ?1 AND city_id = ?2", nativeQuery = true)
  public List check(Long user_id, Long city_id);

  @Query(value = "DELETE FROM user_cities WHERE city_id = ?1", nativeQuery = true)
  public void deleteAllCityReferences(Long city_id);
}
