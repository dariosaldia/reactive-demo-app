package dsg.demo.reactiveapp.initialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dsg.demo.reactiveapp.model.Student;
import dsg.demo.reactiveapp.repository.StudentReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Profile("dev")
@Component
@Slf4j
public class StudentDataInitializer implements CommandLineRunner {

  @Autowired
  private StudentReactiveRepository studentReactiveRepository;

  private List<Student> students() throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper om = new ObjectMapper();
    InputStream in = this.getClass()
        .getClassLoader()
        .getResourceAsStream("mock_students.json");
    return om.readValue(in, om.getTypeFactory()
        .constructCollectionType(List.class, Student.class));
  }

  @Override
  public void run(String... args) throws Exception {
    studentReactiveRepository.deleteAll()
        .thenMany(Flux.fromIterable(students()))
        .flatMap(studentReactiveRepository::save)
        .thenMany(studentReactiveRepository.findAll())
        .subscribe();

  }

}
