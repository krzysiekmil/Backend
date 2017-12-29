package pogodynka.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@Entity
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @NotNull
  private String username;
  @NotNull
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
    this.roles = roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AppUser appUser = (AppUser) o;

    if (id != null ? !id.equals(appUser.id) : appUser.id != null) return false;
    if (username != null ? !username.equals(appUser.username) : appUser.username != null) return false;
    if (password != null ? !password.equals(appUser.password) : appUser.password != null) return false;
    if (cities != null ? !cities.equals(appUser.cities) : appUser.cities != null) return false;
    return roles != null ? roles.equals(appUser.roles) : appUser.roles == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (cities != null ? cities.hashCode() : 0);
    result = 31 * result + (roles != null ? roles.hashCode() : 0);
    return result;
  }
}
