import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Course {
  private int id;
  private String title;
  private int course_number;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getCourseNumber() {
    return course_number;
  }

  public Course(String title, int course_number) {
    this.title = title;
    this.course_number = course_number;
  }

  public static List<Course> all() {
    String sql = "SELECT id, title, course_number FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getTitle().equals(newCourse.getTitle());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses (title, course_number) VALUES (:title, :course_number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("course_number", course_number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course Course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return Course;
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_students_professors (course_id, student_id) VALUES (:course_id, :student_id);";
      con.createQuery(sql)
      .addParameter("course_id", this.getId())
      .addParameter("student_id", student.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT student_id FROM courses_students_professors WHERE course_id=:course_id";
      List<Integer> studentIds = con.createQuery(sql)
      .addParameter("course_id", this.getId())
      .executeAndFetch(Integer.class);

    ArrayList<Student> students = new ArrayList<Student>();

    for (Integer studentId : studentIds) {
      String studentQuery = "SELECT * FROM students WHERE id = :studentId;";
      Student student = con.createQuery(studentQuery)
        .addParameter("studentId", studentId)
        .executeAndFetchFirst(Student.class);

    students.add(student);
   }
   return students;
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM courses_students_professors WHERE course_id = :courseId";
      con.createQuery(joinDeleteQuery)
        .addParameter("courseId", this.getId())
        .executeUpdate();
    }
  }

  public void removeStudent(int student_id) {
    System.out.println(id + "");
    try (Connection con = DB.sql2o.open()) {
      String removeStudentQuery = "DELETE FROM courses_students_professors WHERE student_id = :student_id AND course_id = :id";
      con.createQuery(removeStudentQuery)
        .addParameter("student_id", student_id)
        .addParameter("id", id)
        .executeUpdate();
      }
  }
}
