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

import com.ibm.web.jdbc.SyllabusDbUtil;
import com.ibm.web.jdbc.model.Syllabus;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/SyllabusControllerServlet")
public class SyllabusControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SyllabusDbUtil syllabusDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			syllabusDbUtil = new SyllabusDbUtil(dataSource);
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
				listSyllabus(request, response);
				break;
				
			case "ADD":
				addSyllabus(request, response);
				break;
				
			case "LOAD":
				loadSyllabus(request, response);
				break;
				
			case "UPDATE":
				updateSyllabus(request, response);
				break;
			
			case "DELETE":
				deleteSyllabus(request, response);
				break;
				
			default:
				listSyllabus(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteSyllabus(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student id from form data
		String theSyllabusId = request.getParameter("syllabusId");
		
		// delete student from database
		syllabusDbUtil.deleteSyllabus(theSyllabusId);
		
		// send them back to "list students" page
		listSyllabus(request, response);
	}

	private void updateSyllabus(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student info from form data
		int id = Integer.parseInt(request.getParameter("syllabusId"));
		String syllabusName = request.getParameter("syllabusName");
		String syllabusDesc = request.getParameter("syllabusDesc");
		
		// create a new student object
		Syllabus theSyllabus = new Syllabus(id, syllabusName, syllabusDesc);
		
		// perform update on database
		syllabusDbUtil.updateSyllabus(theSyllabus);
		
		// send them back to the "list students" page
		listSyllabus(request, response);
		
	}

	private void loadSyllabus(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read student id from form data
		String theSyllabusId = request.getParameter("syllabusId");
		
		// get student from database (db util)
		Syllabus theSyllabus = syllabusDbUtil.getSyllabus(theSyllabusId);
		
		// place student in the request attribute
		request.setAttribute("THE_SYLLABUS", theSyllabus);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-syllabus-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addSyllabus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String SyllabusName = request.getParameter("syllabusName");
		String SyllabusDesc = request.getParameter("syllabusDesc");		
		
		// create a new student object
		Syllabus theSyllabus = new Syllabus(SyllabusName,  SyllabusDesc);
		
		// add the student to the database
		syllabusDbUtil.addSyllabus(theSyllabus);
				
		// send back to main page (the student list)
		listSyllabus(request, response);
	}

	private void listSyllabus(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Syllabus> syllabus = syllabusDbUtil.getSyllabus();
		
		// add students to the request
		request.setAttribute("SYLLABUS_LIST", syllabus);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-syllabus.jsp");
		dispatcher.forward(request, response);
	}

}













