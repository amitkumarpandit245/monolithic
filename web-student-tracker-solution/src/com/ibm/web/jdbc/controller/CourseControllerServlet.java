package com.ibm.web.jdbc.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ibm.web.jdbc.CourseDbUtil;
import com.ibm.web.jdbc.model.Course;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/CourseControllerServlet")
public class CourseControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CourseDbUtil courseDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			courseDbUtil = new CourseDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listCourses(request, response);
				break;
				
			case "ADD":
				addCourse(request, response);
				break;
				
			case "LOAD":
				loadCourses(request, response);
				break;
				
			case "UPDATE":
				updateCourse(request, response);
				break;
			
			case "DELETE":
				deleteCourse(request, response);
				break;
				
			default:
				listCourses(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student id from form data
		String theCourseId = request.getParameter("courseId");
		
		// delete student from database
		courseDbUtil.deleteCourse(theCourseId);
		
		// send them back to "list students" page
		listCourses(request, response);
	}

	private void updateCourse(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student info from form data
		int id = Integer.parseInt(request.getParameter("courseId"));
		String courseName = request.getParameter("courseName");
		String courseDesc = request.getParameter("courseDesc");
		
		// create a new student object
		Course theCourse = new Course(id, courseName, courseDesc);
		
		// perform update on database
		courseDbUtil.updateCourse(theCourse);
		
		// send them back to the "list students" page
		listCourses(request, response);
		
	}

	private void loadCourses(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read student id from form data
		String theCourseId = request.getParameter("courseId");
		
		// get student from database (db util)
		Course theCourse = courseDbUtil.getCourse(theCourseId);
		
		// place student in the request attribute
		request.setAttribute("THE_COURSE", theCourse);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-course-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String courseName = request.getParameter("courseName");
		String courseDesc = request.getParameter("courseDesc");		
		
		// create a new student object
		Course theCourse = new Course(courseName,  courseDesc);
		
		// add the student to the database
		courseDbUtil.addCourse(theCourse);
				
		// send back to main page (the student list)
		listCourses(request, response);
	}

	private void listCourses(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Course> courses = courseDbUtil.getCourse();
		
		// add students to the request
		request.setAttribute("COURSE_LIST", courses);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-courses.jsp");
		dispatcher.forward(request, response);
	}

}













