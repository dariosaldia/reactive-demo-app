package dsg.demo.reactiveapp.service;

import java.util.List;
import dsg.demo.reactiveapp.controller.parameterparser.StudentSorting;
import dsg.demo.reactiveapp.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

  Flux<Student> findAll();
  Flux<Student> findStudentsByParams(List<StudentSorting> sortBy, Integer skipParam, Integer limitParam);
  Mono<Student> findByStudentNumber(long studentNumber);
  Mono<Student> findByEmail(String email);
  Flux<Student> findAllByOrderByGpa();
  Mono<Student> saveOrUpdateStudent(Student student);
  Mono<Void> deleteStudent(Student student);

}
