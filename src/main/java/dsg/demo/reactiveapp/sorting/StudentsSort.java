package dsg.demo.reactiveapp.sorting;

public enum StudentsSort {
  GPA("gpa"),EMAIL("email");

  private String value;
  
  StudentsSort(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return this.value;
  }

}
