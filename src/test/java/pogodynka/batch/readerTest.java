package pogodynka.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:src/main/java/pogodynka/config/BatchConfig.class")
public class readerTest {
//  @Autowired
//  BatchConfig batchConfig;

  @Autowired
  Job job;
  @Autowired
  JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void testJobStep() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchStep("cityStep");
    Assert.assertEquals("COMPLETED", jobExecution.getStatus());
  }
}
