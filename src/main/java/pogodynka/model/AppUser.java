package pogodynka.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_cities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
  private Set<City> cities;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<AppUserRole> roles;

  public List<AppUserRole> getRoles() {
        return roles;
    }

  public void setRoles(List<AppUserRole> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  public Set<City> getCities() {
    return cities;
  }

  public void setCities(Set<City> cities) {
    this.cities = cities;
  }

  public AppUser() {
  }

  public AppUser(String username, String password, Set<City> cities, List<AppUserRole> roles) {
        this.username = username;
        this.password = password;
        this.cities = cities;
        this.roles=roles;
    }
}
