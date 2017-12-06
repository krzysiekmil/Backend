package pogodynka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pogodynka.repository.AppUserRepository;

@Component
public class AppUserService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return null;
  }
}
