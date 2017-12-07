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
import java.util.List;

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

  @PostMapping("/user")
  public void AddUser(@RequestBody AppUser appUser) {
    List<AppUserRole> roles = new ArrayList<>();
    roles.add(roleRepository.findByRoleName("STANDARD_USER"));
    appUserRepository.save(new AppUser(appUser.getUsername(), passwordEncoder.encode(appUser.getPassword()), null, roles));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN_USER') or hasAnyAuthority('STANDARD_USER')")
  @DeleteMapping(value = "/city")
  public void deleteCity(@RequestParam String name){
    City city =  cityRepository.findByName(name);
    cityRepository.delete(city);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN_USER') or hasAnyAuthority('STANDARD_USER')")
  @GetMapping(value = "/city")
  public List<City> getAll() {
    return cityRepository.findAll();
  }

  @PreAuthorize("hasAnyAuthority('ADMIN_USER')")
  @PostMapping(value = "/city")
  public void addCity(@RequestParam String name){
    if(!cityRepository.existsByName(name))
      cityRepository.save(new City(name));
  }

  @PreAuthorize("hasAnyAuthority('STANDARD_USER')")
  @GetMapping(value = "/cityData/{name}")
    public List<CityData> showLast(@PathVariable String name ,@RequestParam Long value){
        return cityDataRepository.getLastValueTemp(name,value);
    }

  @PreAuthorize("hasAnyAuthority('STANDARD_USER')")
  @GetMapping(value = "/cityDatat/{name}")
    public List<CityData> give(@PathVariable String name){
        return cityDataRepository.findByName(name);
    }

  @PreAuthorize("hasAnyAuthority('STANDARD_USER')")
  @GetMapping(value = "/cityData")
    public List<CityData> getAllData(){
        return cityDataRepository.findAll();
    }

  @PreAuthorize("hasAnyAuthority('STANDARD_USER')")
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
//cityData/Warszawa?value=100&sort=desc
//City?letter=W
//Cars?color=red
