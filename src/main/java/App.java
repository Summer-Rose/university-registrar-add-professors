import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.Arrays;


public class App {
    public static void main(String[] args) {
    	staticFileLocation("/public");
    	String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String course_search = request.queryParams("course_search");
      List<Course> courses = Course.search(course_search);
      model.put("courses", courses);
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      int course_number = Integer.parseInt(request.queryParams("course_number"));
      Course newCourse = new Course(title, course_number);
      newCourse.save();
      response.redirect("/courses");
      return new ModelAndView(model, layout);
    });

    post("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String enroll_date = request.queryParams("enroll_date");
      Student newStudent = new Student(name, enroll_date);
      newStudent.save();
      response.redirect("/students");
      return null;
   });

   get("/students", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     model.put("students", Student.all());
     model.put("template", "templates/students.vtl");
     return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());


    get("/students/search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String student_search = request.queryParams("student_search");
      List<Student> students = Student.search(student_search);
      model.put("students", students);
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/student/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer studentId = Integer.parseInt(request.params(":id"));
      Student student = Student.find(studentId);
      model.put("professors", Professor.all());
      model.put("studentsProfessors", student.getProfessors());
      model.put("student", student);
      model.put("studentsCourses", student.getCourses());
      model.put("courses", Course.all());
      model.put("template", "templates/student-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/delete/student/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int studentId = Integer.parseInt(request.params(":id"));
      Student deleteStudent = Student.find(studentId);
      deleteStudent.delete();
      response.redirect("/students");
      return null;
    });

    post("/student/:id/addcourse", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int courseId = Integer.parseInt(request.queryParams("course"));
      Course addedCourse = Course.find(courseId);
      Integer studentId = Integer.parseInt(request.params(":id"));
      Student student = Student.find(studentId);
      student.addCourse(addedCourse);
      response.redirect("/student/" + studentId);
      return null;
    });

    get("/course/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer courseId = Integer.parseInt(request.params(":id"));
      Course course = Course.find(courseId);
      model.put("course", course);
      model.put("students", course.getStudents());
      model.put("template", "templates/course-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/delete/course/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer courseId = Integer.parseInt(request.params(":id"));
      Course deletedCourse = Course.find(courseId);
      deletedCourse.delete();
      response.redirect("/courses");
      return null;
    });

    get("/remove/student/:id/course/:courseid", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer studentId = Integer.parseInt(request.params(":id"));
      Integer courseId = Integer.parseInt(request.params(":courseid"));
      Course course = Course.find(courseId);
      course.removeStudent(studentId);
      response.redirect("/student/" + studentId);
      return null;
    });

    get("/removestudent/student/:id/course/:courseid", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer studentId = Integer.parseInt(request.params(":id"));
      Integer courseId = Integer.parseInt(request.params(":courseid"));
      Course course = Course.find(courseId);
      course.removeStudent(studentId);
      response.redirect("/course/" + courseId);
      return null;
    });

    get("/search/results", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String mainSearch = request.queryParams("main-search");
      model.put("students", Student.search(mainSearch));
      model.put("courses", Course.search(mainSearch));
      model.put("template", "templates/search_results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/professors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("professors", Professor.all());
      model.put("template", "templates/professors.vtl");
      return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   post("/professors", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     String name = request.queryParams("name");
     String department = request.queryParams("department");
     Professor newProfessor = new Professor(name, department);
     newProfessor.save();
     response.redirect("/professors");
     return null;
  });

  get("/professors/search", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String professor_search = request.queryParams("professor_search");
    List<Professor> professors = Professor.search(professor_search);
    model.put("professors", professors);
    model.put("template", "templates/professors.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/professor/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    Integer professorId = Integer.parseInt(request.params(":id"));
    Professor professor = Professor.find(professorId);
    model.put("professor", professor);
    model.put("professorsStudents", professor.getStudents());
    model.put("students", Student.all());
    model.put("template", "templates/professor-info.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/professor/:id/addphoto", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String name = request.queryParams("name");
    String department = request.queryParams("department");
    Professor newProfessor = new Professor(name, department);
    newProfessor.save();
    response.redirect("/professors");
    return null;
  });

   get("/delete/professor/:id", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     Integer professorId = Integer.parseInt(request.params(":id"));
     Professor deletedProfessor = Professor.find(professorId);
     deletedProfessor.delete();
     response.redirect("/professors");
     return null;
   });

   post("/professor/:id/addstudent", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     int studentId = Integer.parseInt(request.queryParams("student"));
     Student addedStudent = Student.find(studentId);
     Integer professorId = Integer.parseInt(request.params(":id"));
     Professor professor = Professor.find(professorId);
     professor.addStudent(addedStudent);
     response.redirect("/professor/" + professorId);
     return null;
   });

   get("/remove/professor/:id/student/:studentid", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     Integer professorId = Integer.parseInt(request.params(":id"));
     Integer studentId = Integer.parseInt(request.params(":studentid"));
     Professor professor = Professor.find(professorId);
     professor.removeStudent(studentId);
     response.redirect("/professor/" + professorId);
     return null;
   });
  }
}
