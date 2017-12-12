package pogodynka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pogodynka.model.AppUser;

import java.util.List;
import java.util.Objects;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  public AppUser findByUsername(String username);

  @Query(value = "INSERT INTO user_cities VALUES (?1,?2) ", nativeQuery = true)
  public void addCity(Long user_id, Long city_id);

  @Query(value = "DELETE FROM user_cities WHERE user_id = ?1 AND city_id = ?2 ", nativeQuery = true)
  public void deleteCity(Long user_id, Long city_id);

  @Query(value = "SELECT * FROM user_cities WHERE user_id = ?1 AND city_id = ?2", nativeQuery = true)
  public List<Objects> check(Long user_id, Long city_id);
}
