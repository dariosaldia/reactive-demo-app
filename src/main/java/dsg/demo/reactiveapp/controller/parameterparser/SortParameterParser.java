package dsg.demo.reactiveapp.controller.parameterparser;

import java.util.List;

public interface SortParameterParser {
  
  List<StudentSorting> parseSortParameter(String... sortParameter);

}
