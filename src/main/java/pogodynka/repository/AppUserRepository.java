package pogodynka.repository;

import org.springframework.data.repository.CrudRepository;
import pogodynka.model.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser,Long> {
  public AppUser findByUsername(String username);
}
