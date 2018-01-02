package pogodynka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String time;
    public  String temp;

    public CityData(){};

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityData(String name, String temp, String time) {
        this.name = name;
        this.time = time;
        this.temp = temp;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CityData cityData = (CityData) o;

    if (id != null ? !id.equals(cityData.id) : cityData.id != null) return false;
    if (name != null ? !name.equals(cityData.name) : cityData.name != null) return false;
    if (time != null ? !time.equals(cityData.time) : cityData.time != null) return false;
    return temp != null ? temp.equals(cityData.temp) : cityData.temp == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (temp != null ? temp.hashCode() : 0);
    return result;
  }
}
