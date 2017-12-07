package pogodynka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pogodynka.model.AppUserRole;

public interface RoleRepository extends JpaRepository<AppUserRole, Long> {
  public AppUserRole findByRoleName(String role_name);
}
