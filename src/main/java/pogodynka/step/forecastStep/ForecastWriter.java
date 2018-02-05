package pogodynka.step.forecastStep;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ForecastWriter implements ItemWriter<Object> {

  @Override
  public void write(List<?> list) throws Exception {

  }
}
