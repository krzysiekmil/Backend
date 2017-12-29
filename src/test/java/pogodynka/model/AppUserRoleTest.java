package pogodynka.model;

import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AppUserRoleTest {
  private Validator createValidator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.afterPropertiesSet();
    return localValidatorFactoryBean;
  }

  @Test
  public void shouldNotValidateWhenRoleNameNull() {
    AppUserRole appUserRole = new AppUserRole();
    Validator validator = createValidator();
    Set<ConstraintViolation<AppUserRole>> constraintViolations = validator.validate(appUserRole);
    assertThat(constraintViolations.size()).isEqualTo(1);
    assertThat(appUserRole.getRoleName()).isNullOrEmpty();
    ConstraintViolation<AppUserRole> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).isEqualTo("roleName");
    assertThat(violation.getMessage()).isEqualTo("may not be null");
  }
}
