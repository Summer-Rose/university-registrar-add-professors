import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Professor {
  private int id;
  private String name;
  private String department;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDepartment() {
    return department;
  }

  public Professor(String name, String department) {
    this.name = name;
    this.department = department;
  }

  @Override
  public boolean equals(Object otherProfessor) {
    if(!(otherProfessor instanceof Professor)) {
      return false;
    } else {
      Professor newProfessor = (Professor) otherProfessor;
      return this.getName().equals(newProfessor.getName()) &&
             this.getId() == newProfessor.getId();
    }
  }

  public static List<Professor> all() {
    String sql = "SELECT id, name, department FROM professors ORDER BY name;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Professor.class);
    }
  }

  public static void editProfessor(int id, String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE professors SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO professors (name, department) VALUES (:name, :department);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("department", this.department)
      .executeUpdate()
      .getKey();
    }
  }

  public static Professor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM professors WHERE id=:id";
      Professor professor = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Professor.class);
      return professor;
    }
  }

  public void addStudent(Student student) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO professors_students (professor_id, student_id) VALUES (:professor_id, :student_id)";
      con.createQuery(sql)
        .addParameter("professor_id", this.getId())
        .addParameter("student_id", student.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT student_id FROM professors_students WHERE professor_id = :professor_id";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("professor_id", this.getId())
        .executeAndFetch(Integer.class);
      ArrayList<Student> students = new ArrayList<Student>();
      for (Integer studentId : studentIds) {
        String studentQuery = "SELECT * FROM students WHERE id = :studentId";
        Student student = con.createQuery(studentQuery)
          .addParameter("studentId", studentId)
          .executeAndFetchFirst(Student.class);
        students.add(student);
      }
      return students;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM professors WHERE id=:id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM professors_students WHERE professor_id=:professorId";
      con.createQuery(joinDeleteQuery)
        .addParameter("professorId", this.getId())
        .executeUpdate();
    }
  }

  public static List<Professor> search(String searchName) {
    String lowerCaseSearch = searchName.toLowerCase();
    String sql = "SELECT * FROM professors WHERE LOWER (professors.name) LIKE '%" + lowerCaseSearch + "%'";
    List<Professor> professorResults;
    try (Connection con = DB.sql2o.open()) {
      professorResults = con.createQuery(sql)
        .executeAndFetch(Professor.class);
    }
    return professorResults;
  }
}
