import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class ProfessorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Professor.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Professor firstProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    Professor secondProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    assertTrue(firstProfessor.equals(secondProfessor));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    assertTrue(Professor.all().get(0).equals(myProfessor));
  }

  @Test
  public void save_assignsIdToObject() {
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    Professor savedProfessor = Professor.all().get(0);
    assertEquals(myProfessor.getId(), savedProfessor.getId());
  }

  @Test
  public void find_findsProfessorInDatabase_true() {
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    Professor savedProfessor = Professor.find(myProfessor.getId());
    assertTrue(myProfessor.equals(savedProfessor));
  }

  @Test
  public void addStudent_addsStudentToProfessor() {
    Student myStudent = new Student("Kitty Pryde", "2014-02-10");
    myStudent.save();
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    myProfessor.addStudent(myStudent);
    Student savedStudent = myProfessor.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnAllStudents_ArrayList() {
    Student myStudent = new Student("Kitty Pryde", "2014-02-10");
    myStudent.save();
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    myProfessor.addStudent(myStudent);
    List savedStudents = myProfessor.getStudents();
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void delete_deletesAllProfessorsAndListAssociations() {
    Student myStudent = new Student("Kitty Pryde", "2014-02-10");
    myStudent.save();
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    myProfessor.addStudent(myStudent);
    myProfessor.delete();
    assertEquals(myStudent.getProfessors().size(), 0);
  }

  @Test
  public void edit_newProfessorName() {
    Student myStudent = new Student("Kitty Pryde", "2014-02-10");
    myStudent.save();
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    Professor editProfessor = new Professor("Huge Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.editProfessor(editProfessor.getId(), editProfessor.getName());
    assertTrue(editProfessor.getName() == "Huge Jackman");
  }

  @Test
  public void search_filtersProfessorsByName() {
    Professor myProfessor = new Professor("Hugh Jackman", "Tegestology", "/bin/nedflanders.jpeg");
    myProfessor.save();
    List searchResult = Professor.search("hUg");
    assertTrue(myProfessor.equals(searchResult.get(0)));
  }

}
