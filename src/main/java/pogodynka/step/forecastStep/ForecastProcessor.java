package pogodynka.step.forecastStep;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ForecastProcessor implements ItemProcessor<Object, Object> {
  @Override
  public Object process(Object o) throws Exception {
    return null;
  }

  public Object getObject() {
    Object result, chanel, item, condition;
  }

  public String celToFah(Object temp) {
    Long tmp = ((Long.valueOf(temp.toString()) - 32) * 5 / 9);
    return tmp.toString();
  }
}
