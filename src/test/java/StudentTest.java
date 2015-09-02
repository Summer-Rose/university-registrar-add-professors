import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Student firstStudent = new Student("Bart Simpson", "9-12-2011");
    Student secondStudent = new Student("Bart Simpson", "9-12-2011");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    assertTrue(Student.all().get(0).equals(myStudent));
  }

  @Test
  public void save_assignsIdToObject() {
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertEquals(myStudent.getId(), savedStudent.getId());
  }

  @Test
  public void find_findsStudentInDatabase_true() {
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void addCourse_addsCourseToStudent() {
    Course myCourse = new Course("Architecture", 101);
    myCourse.save();
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    myStudent.addCourse(myCourse);
    Course savedCourse = myStudent.getCourses().get(0);
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void getCourses_returnAllCourses_ArrayList() {
    Course myCourse = new Course("Architecture", 101);
    myCourse.save();
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    myStudent.addCourse(myCourse);
    List savedCourses = myStudent.getCourses();
    assertEquals(savedCourses.size(), 1);
  }

  @Test
  public void delete_deletesAllStudentsAndListAssociations() {
    Course myCourse = new Course("Architecture", 101);
    myCourse.save();
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    myStudent.addCourse(myCourse);
    myStudent.delete();
    assertEquals(myCourse.getStudents().size(), 0);
  }

  @Test
  public void edit_newStudentName() {
    Course myCourse = new Course("Architecture", 101);
    myCourse.save();
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    Student editStudent = new Student("Lisa Simpson", "9-12-2012");
    myStudent.editStudent(editStudent.getId(), editStudent.getName());
    assertTrue(editStudent.getName() == "Lisa Simpson");
  }

  @Test
  public void search_filtersStudentsByName() {
    Student myStudent = new Student("Bart Simpson", "9-12-2011");
    myStudent.save();
    List searchResult = Student.search("B");
    assertTrue(myStudent.equals(searchResult.get(0)));
  }

}
