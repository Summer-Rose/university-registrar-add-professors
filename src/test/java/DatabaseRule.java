import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new
    Sql2o("jdbc:postgresql://localhost:5432/registrar_test", null, null);
  }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStudentsQuery = "DELETE FROM students *;";
      String deleteCoursesQuery = "DELETE FROM courses *;";
      String deleteProfessorsQuery = "DELETE FROM professors *;";
      String deleteCoursesStudentsProfessorsQuery = "DELETE FROM courses_students_professors *;";
      String deleteProfessorsStudentsQuery = "DELETE FROM professors_students *;";
      con.createQuery(deleteCoursesStudentsProfessorsQuery).executeUpdate();
      con.createQuery(deleteStudentsQuery).executeUpdate();
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteProfessorsQuery).executeUpdate();
      con.createQuery(deleteProfessorsStudentsQuery).executeUpdate();
    }
  }
}
