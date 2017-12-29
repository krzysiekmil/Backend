package pogodynka.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pogodynka.model.AppUser;
import pogodynka.model.AppUserRole;
import pogodynka.model.City;
import pogodynka.model.CityData;
import pogodynka.repository.AppUserRepository;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;
import pogodynka.repository.RoleRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
public class Controller {


  @Autowired
  JobLauncher jobLauncher;

  @Autowired
  CityDataRepository cityDataRepository;


  @Autowired
  Job job;

  @Autowired
  Job updateData;

  @Autowired
  CityRepository cityRepository;

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  @GetMapping("/user")
  public List getUsers(@RequestParam(value = "name", required = false) String name) {
    if (name == null) {
      return appUserRepository.findAll();
    } else {
      return new ArrayList<>(appUserRepository.findByUsername(name).getCities());
    }
  }

  @PostMapping("/user")
  public void AddUser(@RequestBody AppUser appUser, @RequestParam(value = "city", required = false) String city) throws JpaSystemException {
    List<AppUserRole> roles = new ArrayList<>();
    Set<City> citySet = new LinkedHashSet<>();
    if (city == null) {
      roles.add(roleRepository.findByRoleName("STANDARD_USER"));
      if (appUserRepository.findByUsername(appUser.getUsername()) == null) {
        appUserRepository.save(new AppUser(appUser.getUsername(), passwordEncoder.encode(appUser.getPassword()), citySet, roles));
      }
    } else {
      if (cityRepository.findByName(city) != null) {
        try {
          appUserRepository.check(
            appUserRepository.findByUsername(appUser.getUsername()).getId(),
            cityRepository.findByName(city).getId());
            cityRepository.addCity(
              appUserRepository.findByUsername(appUser.getUsername()).getId(),
              cityRepository.findByName(city).getId()
            );

        } catch (JpaSystemException e) {
        }
      }
    }
  }


  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  @DeleteMapping("/user")
  public void DeleteUser(@RequestParam(value = "name", required = true) String username) {
    appUserRepository.delete(appUserRepository.findByUsername(username));
  }

  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  @DeleteMapping("/user/{username}")
  public void DeleteUserCity(@PathVariable String username, @RequestParam(value = "city", required = true) String city) {
      AppUser u = appUserRepository.findByUsername(username);
      City c = cityRepository.findByName(city);
      if (c != null && u != null)
        try {
          appUserRepository.deleteCity(
            u.getId(),
            c.getId());
        } catch (JpaSystemException e) {


        }
  }


  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  @DeleteMapping(value = "/city")
  public void deleteCity(@RequestParam String name) {
    City city = cityRepository.findByName(name);
    if (city != null) {
      try {
        cityRepository.removeCity(city.getId());
      } catch (JpaSystemException e) {
        cityRepository.delete(city);
      }
    }
  }

  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  @GetMapping(value = "/city")
  public List<City> getAll() {
    return cityRepository.findAll();
  }

  @PreAuthorize("hasAuthority('ADMIN_USER')")
  @PostMapping(value = "/city")
  public void addCity(@RequestParam String name){
    if(!cityRepository.existsByName(name))
      cityRepository.save(new City(name));
  }

  @PreAuthorize("hasAuthority('STANDARD_USER')")
  @GetMapping(value = "/cityData/{name}")
    public List<CityData> showLast(@PathVariable String name ,@RequestParam Long value){
        return cityDataRepository.getLastValueTemp(name,value);
    }

  @PreAuthorize("hasAuthority('STANDARD_USER')")
  @GetMapping(value = "/cityDatat/{name}")
    public List<CityData> give(@PathVariable String name){
        return cityDataRepository.findByName(name);
    }

  @PreAuthorize("hasAuthority('STANDARD_USER')")
  @GetMapping(value = "/cityData")
    public List<CityData> getAllData(){
        return cityDataRepository.findAll();
    }

  @PreAuthorize("hasAuthority('STANDARD_USER')")
  @Scheduled(fixedRate = 300000)
  @PostMapping(value = "/cityData")
    public void updateCities() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters =  new JobParametersBuilder()
                .addLong("time", System.nanoTime())
                .toJobParameters();
        jobLauncher.run(job,jobParameters);

    }

    @PostMapping(value = "/cityData/{name}")
    public void downloadData(@PathVariable String name) throws Exception{
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.nanoTime())
                    .addString("city", name)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
    }


}
