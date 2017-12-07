package pogodynka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pogodynka.model.AppUser;
import pogodynka.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    AppUser user = appUserRepository.findByUsername(s);
    if (user == null)
      throw new UsernameNotFoundException(String.format("NI MA ", s));
    List<GrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
    });
    UserDetails userDetails = new org.springframework
      .security.core.userdetails
      .User(user.getUsername(), user.getPassword(), authorities);
    return userDetails;
  }
}
