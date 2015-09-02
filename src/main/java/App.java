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
      response.redirect("/course/" + courseId);
      return null;
    });


  }
}
