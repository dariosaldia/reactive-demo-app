package dsg.demo.reactiveapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import dsg.demo.reactiveapp.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentReactiveRepository extends ReactiveMongoRepository<Student, String> {
  
  Mono<Student> findByStudentNumber(long studentNumber);
  Mono<Student> findByEmail(String email);
  Flux<Student> findAllByOrderByGpa();

}
