package pogodynka;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class PogodynkaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PogodynkaApplication.class, args);
	}
}
