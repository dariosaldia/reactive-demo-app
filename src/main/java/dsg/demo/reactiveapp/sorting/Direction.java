package dsg.demo.reactiveapp.sorting;

public enum Direction {

  ASC("asc"), DESC("desc");

  private String value;

  private Direction(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
