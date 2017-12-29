package pogodynka.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

  @GetMapping("/tesdt")
  public String test() {
    return "WORK";
  }
}
