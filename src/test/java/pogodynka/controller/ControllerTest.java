package pogodynka.controller;


import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pogodynka.config.ResourceConfig;
import pogodynka.model.City;
import pogodynka.model.CityData;
import pogodynka.repository.AppUserRepository;
import pogodynka.repository.CityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(ResourceConfig.class)
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

  @MockBean
  Controller controller;

  @Autowired
  MockMvc mvc;

  @MockBean
  AppUserRepository appUserRepository;

  @MockBean
  CityRepository cityRepository;


  @Test
  public void getUserError() throws Exception {
    List<City> list = new ArrayList<>();
    list.add(new City("Warszawa"));
    given(controller.getAll()).willReturn(list);
    mvc.perform(get("/cityasdads").contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void getUser() throws Exception {
    List<City> list = new ArrayList<>();
    list.add(new City("Warszawa"));
    given(controller.getAll()).willReturn(list);
    mvc.perform(get("/city").contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].name", is("Warszawa")));
  }

  @Test
  public void showLast() throws Exception {
    List<CityData> list = new ArrayList<>();
    list.add(new CityData("Warszawa", "3", "12:42"));
    given(controller.showLast("Warszawa", 1l)).willReturn(list);
    mvc.perform(get("/cityData/Warszawa").contentType(MediaType.APPLICATION_JSON_UTF8).pathInfo("/Warszawa").param("value", "1"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].name", is("Warszawa")))
      .andExpect(jsonPath("$[0].time", is("12:42")))
      .andExpect(jsonPath("$[0].temp", is("3")));
  }

  @Test
  public void give() throws Exception {
    List<CityData> list = new ArrayList<>();
    list.add(new CityData("Warszawa", "3", "12:42"));
    given(controller.give("Warszawa")).willReturn(list);
    mvc.perform(get("/cityDatat/Warszawa").contentType(MediaType.APPLICATION_JSON_UTF8).pathInfo("/Warszawa"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].name", is("Warszawa")))
      .andExpect(jsonPath("$[0].time", is("12:42")))
      .andExpect(jsonPath("$[0].temp", is("3")));
  }

  @Test
  public void showLastError() throws Exception, NotFoundException {
//    when(controller.showLast("Warszawa", 1l)).thenThrow(new Exception() );
    mvc.perform(get("/cityData/{name}", "Warszawa").contentType(MediaType.APPLICATION_JSON_UTF8).param("values", "1"))
      .andExpect(status().isBadRequest());
    verifyNoMoreInteractions(controller);
  }

  @Test
  public void deleteCity() throws Exception {
    mvc.perform(delete("/city").param("name", "Warszawa"))
      .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void deleteCityBadRequest() throws Exception {
    mvc.perform(delete("/city").param("names", "Warszawa"))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void deleteUserCity() throws Exception {
    mvc.perform(delete("/user/{username}", "User").param("city", "Warszawa"))
      .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void deleteUserCityBadRequest() throws Exception {
    mvc.perform(delete("/user/{username}", "User"))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void AddUser() throws Exception {
    String body = "{\"username\":\"user\",\"password\":\"password\"}";
    mvc.perform(post("/user").param("city", "City")
      .content(body)
      .contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void AddUserBadRequest() throws Exception {
    mvc.perform(post("/user").param("city", "City")
      .contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void AddCity() throws Exception {
    mvc.perform(post("/city").param("name", "City"))
      .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void AddCityBadRequest() throws Exception {
    mvc.perform(post("/city").param("names", "City"))
      .andExpect(status().isBadRequest());
  }

}
