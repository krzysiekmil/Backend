package pogodynka.controller;

import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pogodynka.config.ResourceConfig;
import pogodynka.model.CityData;
import pogodynka.repository.AppUserRepository;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;
import pogodynka.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(ResourceConfig.class)
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTestNoMock {
  @Autowired
  JobLauncher jobLauncher;


  @MockBean
  Job job;

  @Autowired
  Job updateData;


  @MockBean
  RoleRepository roleRepository;

  @MockBean
  PasswordEncoder passwordEncoder;

  @Autowired
  MockMvc mvc;

  @MockBean
  AppUserRepository appUserRepository;

  @MockBean
  CityRepository cityRepository;

  @MockBean
  CityDataRepository cityDataRepository;

  @Test
  public void showLastBadRequest() throws Exception, NotFoundException {
    mvc.perform(get("/cityData/{name}", "Warszawa").contentType(MediaType.APPLICATION_JSON_UTF8).param("valuej", "1"))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void showLast() throws Exception, NotFoundException {
    List<CityData> list = new ArrayList<>();
    list.add(new CityData("Warszawa", "3", "12:00"));
    when(cityDataRepository.getLastValueTemp("Warszawa", 1L)).thenReturn(list);
    mvc.perform(get("/cityData/{name}", "Warszawa").contentType(MediaType.APPLICATION_JSON_UTF8).param("value", "1"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(content().string("[{\"id\":null,\"name\":\"Warszawa\",\"time\":\"12:00\",\"temp\":\"3\"}]"));
  }

  @Test
  public void showLastNotFound() throws NotFoundException, NullPointerException, Exception {
    when(cityDataRepository.getLastValueTemp("Warszawa", 1L)).thenThrow(new RuntimeException());
    mvc.perform(get("/cityData/{name}", "Warszawa").contentType(MediaType.APPLICATION_JSON_UTF8).param("value", "1L"))
      .andExpect(status().is4xxClientError());
  }
}
