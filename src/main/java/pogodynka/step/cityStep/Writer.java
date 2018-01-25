package pogodynka.step.cityStep;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import pogodynka.model.CityData;
import pogodynka.repository.CityDataRepository;
import pogodynka.repository.CityRepository;

import java.util.List;

public class Writer implements ItemWriter<CityData> {
  @Autowired
  public CityDataRepository cityDataRepository;
    @Autowired
    CityRepository cityRepository;

    @Override
    public void write(List<? extends CityData> listOfList) {

      for(CityData cityData:listOfList){
          CityData test = cityData;

        cityDataRepository.save(listOfList);
      }
    }


}

