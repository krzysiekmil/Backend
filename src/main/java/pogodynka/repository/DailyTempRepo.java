package pogodynka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pogodynka.model.DailyTemp;

import java.util.List;

public interface DailyTempRepo extends JpaRepository<DailyTemp, Long> {

  @Query(value = "SELECT * FROM dailyTemp WHERE month=1?", nativeQuery = true)
  List<DailyTemp> showTemp(Long month);
}
