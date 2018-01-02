package pogodynka.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pogodynka.model.CityData;

import java.util.List;


public interface CityDataRepository extends JpaRepository<CityData,Long> {
    @Query(value = "SELECT temp FROM city_data WHERE  name=?1 ORDER BY id DESC LIMIT 7", nativeQuery = true)
    List<String> findTempByName(String name);
    @Query(value = "SELECT * FROM city_data WHERE name=?1 ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<CityData> findByName(String name);
    @Query(value = "SELECT * FROM city_data  WHERE  name =?1 ORDER BY id DESC FETCH  FIRST 1 ROWS ONLY" ,  nativeQuery = true)
    CityData lastTemp(String cityName);
    @Query(value = "SELECT * FROM city_data WHERE name=?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    public List<CityData> getLastValueTemp(String name, Long value);
}
