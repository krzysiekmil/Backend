package pogodynka.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pogodynka.config.BatchConfig;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;
import pogodynka.step.cityStep.Processor;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BatchConfig.class)
public class BatchTest {

  @Autowired
  Job job;
  @Autowired
  JobLauncher jobLauncher;

  @MockBean
  Processor processor;
  @MockBean
  CityRepository cityRepository;
  @MockBean
  CityDataRepository cityDataRepository;

  JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void test() {
    assertEquals(true, true);
  }

  @Test
  public void testExecutionWithJavaConfig() throws Exception {


    JobLauncherTestUtils testUtils = new JobLauncherTestUtils();

    JobParameters jobParameters = new JobParametersBuilder()
      .addLong("time", System.nanoTime())
      .addString("city", "Warszawa")
      .toJobParameters();

    JobExecution execution = jobLauncher.run(job, jobParameters);
//    JobExecution execution = jobLauncherTestUtils.launchJob(jobParameters);
    assertEquals(execution.getJobParameters().getString("city"), "Warszawa");
    assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
    System.out.println(execution.getExitStatus());

  }
}
