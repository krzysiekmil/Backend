package pogodynka.batch;

import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pogodynka.config.BatchConfig;
import pogodynka.model.City;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;
import pogodynka.step.cityStep.Processor;
import pogodynka.step.cityStep.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BatchConfig.class)
public class BatchTest {

  @Autowired
  Reader reader;
  @Autowired
  Job job;
  @Autowired
  JobLauncher jobLauncher;
  @Autowired
  JobRepository jobRepository;
  @MockBean
  Processor processor;
  @MockBean
  CityRepository cityRepository;
  @MockBean
  CityDataRepository cityDataRepository;

  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void test() {
    assertEquals(true, true);
  }

  @Test
  public void testJobExecution() throws Exception {

    JobParameters jobParameters = new JobParametersBuilder()
      .addLong("time", System.nanoTime())
      .addString("city", "Warszawa")
      .toJobParameters();
    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJob(this.job);
    jobLauncherTestUtils.setJobLauncher(this.jobLauncher);
    JobExecution execution = jobLauncherTestUtils.launchJob(jobParameters);
    assertEquals(execution.getJobParameters().getString("city"), "Warszawa");
    assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
  }

  @Test
  public void testStepExecution() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
      .addLong("time", System.nanoTime())
      .addString("city", "Warszawa")
      .toJobParameters();
    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJob(this.job);
    jobLauncherTestUtils.setJobLauncher(this.jobLauncher);
    jobLauncherTestUtils.setJobRepository(this.jobRepository);
    JobExecution execution = jobLauncherTestUtils.launchStep("cityStep", jobParameters);
    assertEquals(execution.getJobParameters().getString("city"), "Warszawa");
    assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
  }

  @Test
  public void testStepExecutionWithNoParameters() {
    List<City> response = new ArrayList<>();
    response.add(new City("Warszawa", 1l));
    response.add(new City("Lodz", 2l));
    given(cityRepository.findAll()).willReturn(response);
    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJob(this.job);
    jobLauncherTestUtils.setJobLauncher(this.jobLauncher);
    jobLauncherTestUtils.setJobRepository(this.jobRepository);
    JobExecution execution = jobLauncherTestUtils.launchStep("cityStep");
    assertEquals(cityRepository.findAll().isEmpty(), false);
    assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
  }

  @Test
  public void testReader() throws IOException, JSONException {
    assertNotNull(reader.read());
  }
}
