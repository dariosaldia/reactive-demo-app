package dsg.demo.reactiveapp.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

  @Id
  private String id;
  private String name;
  private long studentNumber;
  private String email;
  private List<String> courseList;
  private float gpa;

}
