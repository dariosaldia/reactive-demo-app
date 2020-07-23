package dsg.demo.reactiveapp.service;

import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dsg.demo.reactiveapp.model.Student;
import dsg.demo.reactiveapp.repository.StudentReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({MockitoExtension.class})
public class StudentServiceImplTest {

  @Mock
  private StudentReactiveRepository studentReactiveRepository;

  @InjectMocks
  private StudentService studentService = new StudentServiceImpl();

  //@formatter:off
  private List<Student> students = Arrays.asList(
      new Student(null, "Joan Salomon", 100, "jsalomon@email.com", Arrays.asList("Maths, Science"), 7.5f),
      new Student(null, "Justeen Carney", 50, "jcarney@email.com", Arrays.asList("Music"), 9f),
      new Student(null, "Breanna Mueller", 30, "bmueller@email.com", Arrays.asList("It, Geography"), 6f),
      new Student(null, "Lacey Carter", 150, "lcarter@email.com", Arrays.asList("Art"), 8f)
      );
  //@formatter:on

  @BeforeEach
  public void init() {
    
  }
  
  @Test
  public void findAllTest() {
    // when
    when(studentReactiveRepository.findAll()).thenReturn(Flux.fromIterable(students));

    StepVerifier.create(studentService.findAll()
        .log("FIND_ALL"))
        .expectSubscription()
        .expectNextCount(4)
        .expectComplete()
        .verify();

  }
  
  @Test
  public void findStudentsByParamsTest() {
    // when
    when(studentReactiveRepository.findAll()).thenReturn(Flux.fromIterable(students));

    StepVerifier.create(studentService.findAll()
        .log("FIND_ALL"))
        .expectSubscription()
        .expectNextCount(4)
        .expectComplete()
        .verify();

  }
  
  @Test
  public void findByStudentNumberTest() {
    Student student = new Student("123", "Lacey Carter", 150, "lcarter@email.com", Arrays.asList("Art"), 8f);
    
    //when(studentReactiveRepository.findAllBy(any)).thenReturn(Mono.just(student));
    
    StepVerifier.create(studentService.findByStudentNumber(150).log("FIND_BY_STUDENT_NUMBER"))
        .expectSubscription()
        .expectNextMatches(actualStudent -> actualStudent.getId().equals("123"))
        .expectComplete()
        .verify();
  }
  
  @Test
  public void findAllByOrderByGpaTest() {
    List<Student> students = Arrays.asList(
        new Student(null, "Breanna Mueller", 30, "bmueller@email.com", Arrays.asList("It, Geography"), 6f),
        new Student(null, "Joan Salomon", 100, "jsalomon@email.com", Arrays.asList("Maths, Science"), 7.5f),
        new Student(null, "Lacey Carter", 150, "lcarter@email.com", Arrays.asList("Art"), 8f),
        new Student(null, "Justeen Carney", 50, "jcarney@email.com", Arrays.asList("Music"), 9f)
        );
    when(studentReactiveRepository.findAllByOrderByGpa()).thenReturn(Flux.fromIterable(students));
    
    StepVerifier.create(studentService.findAllByOrderByGpa().log("FIND_BY_STUDENT_NUMBER"))
    .expectSubscription()
    .expectNextMatches(actualStudent -> actualStudent.getGpa() == 6)
    .expectNextMatches(actualStudent -> actualStudent.getGpa() == 7.5)
    .expectNextMatches(actualStudent -> actualStudent.getGpa() == 8)
    .expectNextMatches(actualStudent -> actualStudent.getGpa() == 9)
    .expectComplete()
    .verify();
  }

}
