package pogodynka.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String[] cities;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns
    = @JoinColumn(name = "user_id",
    referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",
      referencedColumnName = "id"))
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

    public String[] getCities() {
        return cities;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
    }

  public AppUser(String username, String password, String[] cities, List<AppUserRole> roles) {
        this.username = username;
        this.password = password;
        this.cities = cities;
        this.roles=roles;
    }
}
