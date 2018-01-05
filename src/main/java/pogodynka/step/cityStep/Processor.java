package pogodynka.step.cityStep;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import pogodynka.model.CityData;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<CityDataDto, CityData> {
    @Override
    public CityData process(CityDataDto cityDataDto) throws Exception {
        Object query = cityDataDto.getCityData().get("query");
        Object temp = getTempData(query);

        if(temp != null) {
            return new CityData(cityDataDto.getCurrentCity(), celToFah(temp), LocalDateTime.now().toString());
        }

        return null;
    }

  public Object getTempData(Object query) {
        Object results = null;
        Object channel = null;
        Object item = null;
        Object condition = null;
        Object temp = null;

        if (query != null && query instanceof Map)
            results = ((Map) query).get("results");
        if (results != null && results instanceof Map)
            channel = ((Map) results).get("channel");
        if (channel != null && channel instanceof Map)
            item = ((Map) channel).get("item");
        if (item != null && item instanceof Map)
            condition = ((Map) item).get("condition");
        if (condition != null && condition instanceof Map)
            temp = ((Map) condition).get("temp");
        return temp;
    }

    public String celToFah(Object temp) {
        Long tmp = ((Long.valueOf(temp.toString()) - 32) * 5 / 9);
        return tmp.toString();
    }
}
