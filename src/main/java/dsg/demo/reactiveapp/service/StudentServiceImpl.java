package dsg.demo.reactiveapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import dsg.demo.reactiveapp.model.Student;
import reactor.core.publisher.Flux;

@Service
@Profile("!dev")
public class StudentServiceImpl extends AbstractStudentServiceImpl {

  @Override
  public Flux<Student> findAll() {
    throw new RuntimeException();
  }

}
