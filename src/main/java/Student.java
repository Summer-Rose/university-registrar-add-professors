import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Student {
  private int id;
  private String name;
  private String enroll_date;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEnrollDate() {
    return enroll_date;
  }

  public Student(String name, String enroll_date) {
    this.name = name;
    this.enroll_date = enroll_date;
  }

  @Override
  public boolean equals(Object otherStudent) {
    if(!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
             this.getId() == newStudent.getId();
    }
  }

  public static List<Student> all() {
    String sql = "SELECT id, name, enroll_date FROM students ORDER BY name ASC;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public static void editStudent(int id, String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, enroll_date) VALUES (:name, :enroll_date);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("enroll_date", this.enroll_date)
      .executeUpdate()
      .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students WHERE id=:id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void addCourse(Course course) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_students_professors (course_id, student_id) VALUES (:course_id, :student_id)";
      con.createQuery(sql)
        .addParameter("course_id", course.getId())
        .addParameter("student_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Course> getCourses() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT course_id FROM courses_students_professors WHERE student_id = :student_id";
      List<Integer> courseIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);
      ArrayList<Course> courses = new ArrayList<Course>();
      for (Integer courseId : courseIds) {
        String studentQuery = "SELECT * FROM courses WHERE id = :courseId";
        Course course = con.createQuery(studentQuery)
          .addParameter("courseId", courseId)
          .executeAndFetchFirst(Course.class);
        courses.add(course);
      }
      return courses;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM students WHERE id=:id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM courses_students_professors WHERE student_id=:studentId";
      con.createQuery(joinDeleteQuery)
        .addParameter("studentId", this.getId())
        .executeUpdate();
    }
  }

  public static List<Student> search(String searchName) {
    String lowerCaseSearch = searchName.toLowerCase();
    String sql = "SELECT * FROM students WHERE LOWER (students.name) LIKE '%" + lowerCaseSearch + "%'";
    List<Student> studentResults;
    try (Connection con = DB.sql2o.open()) {
      studentResults = con.createQuery(sql)
        .executeAndFetch(Student.class);
    }
    return studentResults;
  }


    public void addProfessor(Professor professor) {
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO professors_students (professor_id, student_id) VALUES (:professor_id, :student_id)";
        con.createQuery(sql)
          .addParameter("professor_id", professor.getId())
          .addParameter("student_id", this.getId())
          .executeUpdate();
      }
    }

  public ArrayList<Professor> getProfessors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT professor_id FROM professors_students WHERE student_id = :student_id";
      List<Integer> professorIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);
      ArrayList<Professor> professors = new ArrayList<Professor>();
      for (Integer professorId : professorIds) {
        String professorQuery = "SELECT * FROM professors WHERE id = :professorId";
        Professor professor = con.createQuery(professorQuery)
          .addParameter("professorId", professorId)
          .executeAndFetchFirst(Professor.class);
        professors.add(professor);
      }
      return professors;
    }
  }
}
