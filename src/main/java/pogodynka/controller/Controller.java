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
import org.springframework.web.bind.annotation.*;
import pogodynka.model.City;
import pogodynka.model.CityData;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;

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

  @GetMapping(value = "/welcome")
  public String name() {
    return "DZIALAM";
  }


    @DeleteMapping(value = "/city")
    public void deleteCity(@RequestParam String name){
        City city =  cityRepository.findByName(name);
        cityRepository.delete(city);
    }


  @PostMapping(value = "/city")
    public void addCity(@RequestParam String name){
        if(!cityRepository.existsByName(name))
            cityRepository.save(new City(name));
    }


  @GetMapping(value = "/cityData/{name}")
    public List<CityData> showLast(@PathVariable String name ,@RequestParam Long value){
        return cityDataRepository.getLastValueTemp(name,value);
    }

    @GetMapping(value="/cityDatat/{name}")
    public List<CityData> give(@PathVariable String name){
        return cityDataRepository.findByName(name);
    }

    @GetMapping(value = "/cityData")
    public List<CityData> getAllData(){
        return cityDataRepository.findAll();
    }

    @GetMapping(value="/city")
    public List<City> getAll(){
        return cityRepository.findAll();
    }

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
