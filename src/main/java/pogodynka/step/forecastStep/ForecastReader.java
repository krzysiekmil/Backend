package pogodynka.step.forecastStep;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pogodynka.model.City;
import pogodynka.repository.CityRepository;
import pogodynka.step.cityStep.CityDataDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ForecastReader implements ItemReader<CityDataDto> {
  @Autowired
  private CityRepository cityRepository;
  private Map<String, Object> cityData;
  public Iterator<String> cityIterator;
  public String currentCity;


  private String readAll(java.io.Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public String readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json.toString();
    } finally {
      is.close();
    }
  }

  public void initialize() {
    if (cityIterator == null) {
      List<City> cityList = cityRepository.findAll();
      if (cityList != null)
        cityIterator = cityList
          .stream()
          .map(City::getName)
          .collect(Collectors.toList())
          .iterator();
    }
  }

  public String getWeatherUrl(String currentCity) {
    return "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + currentCity + "%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
  }

  public boolean isInitialized() {
    return cityIterator != null;
  }

  @Override
  public CityDataDto read() throws Exception {
    if (!isInitialized())
      initialize();

    if (cityIterator.hasNext()) {
      currentCity = cityIterator.next();
      String url = getWeatherUrl(currentCity);
      ObjectMapper om = new ObjectMapper();
      cityData = om.readValue(readJsonFromUrl(url), new TypeReference<Map<String, Object>>() {
      });
      return new CityDataDto(currentCity, cityData);
    } else {
      cityIterator = null;
    }

    return null;
  }

}
