package dsg.demo.reactiveapp.repository;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import dsg.demo.reactiveapp.model.Student;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class StudentRepositoryTest {

  @Autowired
  private StudentReactiveRepository studentReactiveRepository;
  
  private List<Student> students = Arrays.asList(
      new Student(null, "Joan Salomon", 100, "jsalomon@email.com", Arrays.asList("Maths, Science"), 7.5f),
      new Student(null, "Justeen Carney", 50, "jcarney@email.com", Arrays.asList("Music"), 9f),
      new Student(null, "Breanna Mueller", 30, "bmueller@email.com", Arrays.asList("It, Geography"), 6f),
      new Student(null, "Lacey Carter", 150, "lcarter@email.com", Arrays.asList("Art"), 8f)
      );
  
  @BeforeEach
  public void setUp() {
    studentReactiveRepository.deleteAll()
      .thenMany(Flux.fromIterable(students))
      .flatMap(studentReactiveRepository::save)
      .doOnNext((student -> {
        System.out.println("Inserted item is " + student);
      }))
      .blockLast();
  }
  
  @Test
  public void findByStudentNumberTest() {
    StepVerifier.create(studentReactiveRepository.findByStudentNumber(30))
        .expectSubscription()
        .expectNextMatches(student -> student.getEmail().equals("bmueller@email.com"))
        .verifyComplete();
  }
  
  @Test
  public void findByStudentNumber_notExistsTest() {
    StepVerifier.create(studentReactiveRepository.findByStudentNumber(-10))
        .expectSubscription()
        .expectComplete()
        .verify();
  }
  
  @Test
  public void findByEmailTest() {
    StepVerifier.create(studentReactiveRepository.findByEmail("bmueller@email.com"))
      .expectSubscription()
      .expectNextMatches(student -> student.getStudentNumber() == 30)
      .verifyComplete();
  }
  
  @Test
  public void findAllByOrderByGpaTest() {
    StepVerifier.create(studentReactiveRepository.findAllByOrderByGpa())
      .expectSubscription()
      .expectNextMatches(student -> student.getGpa() == 6f)
      .expectNextMatches(student -> student.getGpa() == 7.5f)
      .expectNextMatches(student -> student.getGpa() == 8f)
      .expectNextMatches(student -> student.getGpa() == 9f)
      .verifyComplete();
  }
}
