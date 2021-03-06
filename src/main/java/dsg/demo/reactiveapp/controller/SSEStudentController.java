package dsg.demo.reactiveapp.controller;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dsg.demo.reactiveapp.controller.parameterparser.SortParameterParser;
import dsg.demo.reactiveapp.controller.parameterparser.StudentSorting;
import dsg.demo.reactiveapp.model.Student;
import dsg.demo.reactiveapp.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/students-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
@Slf4j
public class SSEStudentController {
  
  private static final int DELAY_PER_ITEM_MS = 500;

    @Autowired
    private StudentService studentService;
    @Autowired
    private SortParameterParser sortParameterParser;
    
    @GetMapping
    public Flux<Student> getStudentsByParams(
        @RequestParam(name = "sort", required = false) String sort, 
        @RequestParam(name = "page", required = false) Integer page, 
        @RequestParam(name = "size", required = false) Integer size) {
      log.info("Sort param is {}", sort);
      log.info("Page param is {}", page);
      log.info("Size param is {}", size);
      
      List<StudentSorting> sortBy = sortParameterParser.parseSortParameter(sort);
      
      Optional<Integer> pageOpt = Optional.ofNullable(page);
      Integer pageNum = null;
      if (pageOpt.isPresent()) {
        pageNum = pageOpt.get();
      }
      else {
        log.info("Page number is empty or null. Defaulting to {}", pageNum);
        pageNum = 0;
      }
      
      Optional<Integer> sizeOpt = Optional.ofNullable(size);
      Integer sizeNum = null;
      if (sizeOpt.isPresent()) {
        sizeNum = sizeOpt.get();
      }
      else {
        log.info("Size number is empty or null. Defaulting to {}", sizeNum);
        sizeNum = 10;
      }
      
      return studentService.findStudentsByParams(sortBy, pageNum, sizeNum)
          .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

}