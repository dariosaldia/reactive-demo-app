package dsg.demo.reactiveapp.controller.parameterparser;

import dsg.demo.reactiveapp.sorting.Direction;
import dsg.demo.reactiveapp.sorting.StudentsSort;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentSorting {

  private StudentsSort sorting;
  private Direction direction;
  
}
