package pogodynka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pogodynka.model.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long>
{
     boolean existsByName(String name);
     @Query(value = "SELECT * FROM city",nativeQuery = true)
     List<City> findAll();
     City findByName(String name);

  @Query(value = "DELETE FROM user_cities WHERE city_id = ?1", nativeQuery = true)
  void removeCity(Long city_id);

  @Query(value = "INSERT INTO user_cities VALUES (?1,?2) ", nativeQuery = true)
  void addCity(Long user_id, Long city_id);

  @Query(value = "SELECT id,name FROM city ", nativeQuery = true)
  List<City> listCity();
}
