package pogodynka.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import pogodynka.config.BatchConfig;
import pogodynka.model.City;
import pogodynka.model.CityData;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;
import pogodynka.step.cityStep.CityDataDto;
import pogodynka.step.cityStep.Processor;
import pogodynka.step.cityStep.Reader;
import pogodynka.step.cityStep.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BatchConfig.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class StepScopeTest {

  @MockBean
  CityDataRepository cityDataRepository;
  @MockBean
  CityRepository cityRepository;
  @MockBean
  Processor processor;
  @Autowired
  Reader reader;
  @Autowired
  Writer writer;
  @Mock
  String weatherUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Warszawa%2C%20pol%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
  @Mock
  String jsonResponse = "{\"query\":{\"count\":1,\"created\":\"2018-01-05T12:29:59Z\",\"lang\":\"pl\",\"results\":{\"channel\":{\"item\":{\"condition\":{\"code\":\"26\",\"date\":\"Fri, 05 Jan 2018 12:00 PM CET\",\"temp\":\"47\",\"text\":\"Cloudy\"}}}}}}";

  @Test
  public void test() {
    assertEquals(true, true);
  }
  @Test
  public void getWeaterUrlReader() {
    assertEquals(reader.getWeatherUrl("Warszawa"), weatherUrl);
  }
  @Test
  public void readScope() throws Exception {
    List<City> cityList = new ArrayList<>();
    cityList.add(new City("Warszawa", 1L));
    MetaDataInstanceFactory metaDataInstanceFactory = new MetaDataInstanceFactory();
    StepExecution execution = MetaDataInstanceFactory.createStepExecution();
    int count = StepScopeTestUtils.doInStepScope(execution,
      () -> {

        int count1 = 0;

        while (reader.read() != null) {
          count1++;
        }
        return count1;
      });
    assertNotNull(count);
  }
  @Test
  public void getTempDataProcessor() throws IOException {
    processor = new Processor();
    Map<String, Object> mapm = new ObjectMapper().readValue(jsonResponse, new TypeReference<Map<String, Object>>() {
    });
    Object test = mapm.get("query");
    assertEquals(processor.getTempData(test), "47");
  }
  @Test
  public void processorEmpty() throws Exception {
    processor = new Processor();
    assertNull(processor.process(new CityDataDto("", Collections.emptyMap())));
  }
  @Test
  public void processor() throws Exception {
    processor = new Processor();
    CityDataDto dto = new CityDataDto("Warszawa", new ObjectMapper().readValue(jsonResponse, new TypeReference<Map<String, Object>>() {
    }));
    assertNotNull(processor.process(dto));
  }
  @Test
  public void celToFahProcessor() {
    processor = new Processor();
    assertEquals(processor.celToFah("46"), "7");
  }
  @Test
  public void writerScope() throws Exception {
    List<CityData> cityDataList = new ArrayList<>();
    cityDataList.add(new CityData("Warszawa", "3", "12:00"));
    StepExecution execution = MetaDataInstanceFactory.createStepExecution();
    assertNotNull(StepScopeTestUtils.doInStepScope(execution,
      () -> {
        writer.write(cityDataList);
        return true;
      }));
  }
  @Test
  public void writerScopeEmpty() throws Exception {
    List<CityData> cityDataList = new ArrayList<>();
    cityDataList.add(new CityData("Warszawa", "3", "12:00"));
    StepExecution execution = MetaDataInstanceFactory.createStepExecution();
    verify(writer.cityDataRepository.save(cityDataList), times(1));

  }
}
