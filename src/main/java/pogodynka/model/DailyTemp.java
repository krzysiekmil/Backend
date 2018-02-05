package pogodynka.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
public class DailyTemp {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private Long month;
  private Long day;
  private Long minTemp;
  private Long maxTemp;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "city_dailyTemp", joinColumns = @JoinColumn(name = "dailyTemp_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
  private Set<City> citySet;

  public DailyTemp(Long month, Long day, Long minTemp, Long maxTemp) {
    this.month = month;
    this.day = day;
    this.minTemp = minTemp;
    this.maxTemp = maxTemp;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMonth() {
    return month;
  }

  public void setMonth(Long month) {
    this.month = month;
  }

  public Long getDay() {
    return day;
  }

  public void setDay(Long day) {
    this.day = day;
  }

  public Long getMinTemp() {
    return minTemp;
  }

  public void setMinTemp(Long minTemp) {
    this.minTemp = minTemp;
  }

  public Long getMaxTemp() {
    return maxTemp;
  }

  public void setMaxTemp(Long maxTemp) {
    this.maxTemp = maxTemp;
  }


}
