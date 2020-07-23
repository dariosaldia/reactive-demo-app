package dsg.demo.reactiveapp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import dsg.demo.reactiveapp.controller.parameterparser.StudentSorting;
import dsg.demo.reactiveapp.model.Student;
import dsg.demo.reactiveapp.repository.StudentReactiveRepository;
import dsg.demo.reactiveapp.sorting.Direction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractStudentServiceImpl implements StudentService {
  
  @Autowired
  protected StudentReactiveRepository studentReactiveRepository;
  
  @Override
  public Mono<Student> findByStudentNumber(long studentNumber) {
    return studentReactiveRepository.findByStudentNumber(studentNumber);
  }

  @Override
  public Mono<Student> findByEmail(String email) {
    return studentReactiveRepository.findByEmail(email);
  }

  @Override
  public Flux<Student> findAllByOrderByGpa() {
    return studentReactiveRepository.findAllByOrderByGpa();
  }

  @Override
  public Mono<Student> saveOrUpdateStudent(Student student) {
    return studentReactiveRepository.save(student);
  }

  @Override
  public Mono<Void> deleteStudent(Student student) {
    return studentReactiveRepository.delete(student);
  }

  @Override
  public Flux<Student> findStudentsByParams(List<StudentSorting> sortBy, Integer page,
      Integer size) {
    
    List<Order> orders = sortBy.stream().map(sortAndDir -> {
      if (sortAndDir.getDirection().equals(Direction.ASC)) {
        return Order.asc(sortAndDir.getSorting().getValue());
      } else {
        return Order.desc(sortAndDir.getSorting().getValue());
      }
    })
    .collect(Collectors.toList());
    
    return studentReactiveRepository.findAllBy(PageRequest.of(page, size, Sort.by(orders)));
    
  }
  
}
