package pogodynka.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pogodynka.config.ResourceConfig;
import pogodynka.model.AppUser;
import pogodynka.repository.AppUserRepository;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(ResourceConfig.class)
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
@AutoConfigureMockRestServiceServer
public class ControllerTest {

  @MockBean
  Controller controller;

  @Autowired
  MockMvc mvc;

  @MockBean
  AppUserRepository appUserRepository;


  @Test
  public void test() throws Exception {
    List<AppUser> test = new LinkedList<>();
    test.add(new AppUser("asd", "asd", null, null));
    given(this.appUserRepository.findAll()).willReturn(test);
//    when(appUserRepository.findByUsername("asd")).thenCallRealMethod();
    mvc.perform(
      get("/user")
//      .param("name","asd2")
    )
      .andExpect(status().is2xxSuccessful())
      .andExpect(content().string("{\"id\":1,\"name\":\"Warszawa\"}"));

  }


}
