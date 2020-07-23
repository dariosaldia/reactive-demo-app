package dsg.demo.reactiveapp.controller.parameterparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import dsg.demo.reactiveapp.sorting.Direction;
import dsg.demo.reactiveapp.sorting.StudentsSort;

@Component
public class SimpleSortParameterParser implements SortParameterParser {

  /**
   * To make it simply, assume sort parameter value format as a comma separated FIELD:DIRECTION
   * Example: field1:asc,field2:desc
   */
  @Override
  public List<StudentSorting> parseSortParameter(String... sortParameter) {
    if (sortParameter == null || sortParameter[0] == null)
      return new ArrayList<>();

    List<String> sortBy = Arrays.asList(sortParameter[0].split(","));
    List<String[]> sortAndDirection = sortBy.stream()
        .map(sort -> sort.split(":"))
        .collect(Collectors.toList());

    validateArgument(sortAndDirection);
    
    return sortAndDirection.stream()
        .map(sortAndDir -> new StudentSorting(StudentsSort.valueOf(sortAndDir[0]), Direction.valueOf(sortAndDir[1])))
        .collect(Collectors.toList());
  }

  private void validateArgument(List<String[]> sortAndDirection) {
    // Kind of a validation
    boolean validLength = sortAndDirection.stream()
        .allMatch(sorting -> sorting.length == 2);
    
    if (!validLength)
      throw new IllegalArgumentException("Parameter value format must be FIELD:DIRECTION");

    boolean validSortArg = sortAndDirection.stream()
        .allMatch(sorting -> Arrays.stream(StudentsSort.values())
            .anyMatch(value -> value.getValue().equals(sorting[0])));
    
    if (!validSortArg) {
      String errorMsg = String.format("First parameter value format must be one of %s", 
          Stream.of(StudentsSort.values()).map(StudentsSort::getValue).collect(Collectors.toList()));
      throw new IllegalArgumentException(errorMsg);
    }
    
    boolean validDirArg = sortAndDirection.stream()
        .allMatch(sorting -> Arrays.stream(Direction.values())
            .anyMatch(value -> value.getValue()
                .equals(sorting[1])));
    
    if (!validDirArg) {
      String errorMsg = String.format("Second parameter value format must be one of %s", 
          Stream.of(Direction.values()).map(Direction::getValue).collect(Collectors.toList()));
      throw new IllegalArgumentException(errorMsg);
    }
  }

}
