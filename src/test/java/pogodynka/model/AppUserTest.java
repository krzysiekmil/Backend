package pogodynka.model;

import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AppUserTest {
  private Validator createValidator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.afterPropertiesSet();
    return localValidatorFactoryBean;
  }

  @Test
  public void shouldNotValidateWhenUsernameEmpty() {
    AppUser appUser = new AppUser();
    appUser.setPassword("password");
    Validator validator = createValidator();
    Set<ConstraintViolation<AppUser>> constraintViolations = validator.validate(appUser);
    assertThat(constraintViolations.size()).isEqualTo(1);
    assertThat(appUser.getUsername()).isNullOrEmpty();
    ConstraintViolation<AppUser> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).isEqualTo("username");
    assertThat(violation.getMessage()).isEqualTo("may not be null");
  }

  @Test
  public void shouldNotValidateWhenPasswordEmpty() {
    AppUser appUser = new AppUser();
    appUser.setUsername("username");
    Validator validator = createValidator();
    Set<ConstraintViolation<AppUser>> constraintViolations = validator.validate(appUser);
    assertThat(constraintViolations.size()).isEqualTo(1);
    assertThat(appUser.getPassword()).isNullOrEmpty();
    ConstraintViolation<AppUser> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
    assertThat(violation.getMessage()).isEqualTo("may not be null");
  }


}
