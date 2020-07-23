package dsg.demo.reactiveapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import dsg.demo.reactiveapp.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StudentReactiveRepository extends ReactiveMongoRepository<Student, String> {

  Mono<Student> findByStudentNumber(long studentNumber);

  Mono<Student> findByEmail(String email);

  Flux<Student> findAllByOrderByGpa();

  Flux<Student> findAllBy(Pageable pageable);

}
