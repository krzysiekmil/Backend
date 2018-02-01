package pogodynka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long Id;
  private String title;
  private String text;

  public Notification(String title, String text) {
    this.title = title;
    this.text = text;
  }

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
