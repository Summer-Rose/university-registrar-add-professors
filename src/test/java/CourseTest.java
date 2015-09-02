import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.sql2o.*;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTitlesAreTheSame() {
    Course firstCourse = new Course("Human Sexuality", 101);
    Course secondCourse = new Course("Human Sexuality", 101);
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Course myCourse = new Course("Human Sexuality", 101);
    myCourse.save();
    assertTrue(Course.all().get(0).equals(myCourse));
  }

  @Test
  public void find_findCourseInDatabase_true() {
    Course myCourse = new Course("Human Sexuality", 101);
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Course myCourse = new Course("Human Sexuality", 101);
    myCourse.save();
    Student myStudent = new Student("Kristen Wiig", "9-12-2011");
    myStudent.save();
    myCourse.addStudent(myStudent);
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_ArrayList() {
    Course myCourse = new Course("Universal Syntax", 101);
    myCourse.save();
    Student myStudent = new Student("Louis CK", "9-12-2011");
    myStudent.save();
    myCourse.addStudent(myStudent);
    List savedStudents = myCourse.getStudents();
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void delete_deletesAllStudentAndListAssociations() {
    Course myCourse = new Course("Editorial Design", 101);
    myCourse.save();
    Student myStudent = new Student("David Carson", "9-12-2011");
    myStudent.save();
    myCourse.addStudent(myStudent);
    myCourse.delete();
    assertEquals(myStudent.getCourses().size(), 0);
  }

  @Test
  public void removeStudent_removesStudentFromJoinTable() {
    Course myCourse = new Course("Editorial Design", 101);
    myCourse.save();
    Student myStudent = new Student("David Carson", "9-12-2011");
    myStudent.save();
    myCourse.addStudent(myStudent);
    myCourse.removeStudent(myStudent.getId());
    assertFalse(myCourse.getStudents().contains("David Carson"));
  }

}
