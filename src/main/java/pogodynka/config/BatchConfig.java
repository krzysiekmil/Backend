package pogodynka.config;

import org.codehaus.jettison.json.JSONException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pogodynka.model.CityData;
import pogodynka.step.cityStep.CityDataDto;
import pogodynka.step.cityStep.Processor;
import pogodynka.step.cityStep.Reader;
import pogodynka.step.cityStep.Writer;

import java.io.IOException;


@EnableBatchProcessing
@Configuration
public class BatchConfig {


  @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job(Step cityStep) throws IOException, JSONException {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(cityStep)
                .end()
                .build();
    }

    @Bean
    @StepScope
    public Reader getCityReader(
            @Value("#{jobParameters['city']}") String city
            ) throws IOException, JSONException {
            Reader reader = new Reader();
            reader.setCity(city);
            return reader;
    }

    @Bean
    public Writer getCityWriter() throws  IOException , JSONException{
        Writer writer = new Writer();
        return writer;
    }

    @Bean
    public Step cityStep(Reader reader, Processor processor, Writer writer) throws IOException, JSONException {
        return stepBuilderFactory.get("cityStep")
                .<CityDataDto, CityData> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
