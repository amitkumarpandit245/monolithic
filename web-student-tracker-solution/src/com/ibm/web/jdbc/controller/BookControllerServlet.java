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

import com.ibm.web.jdbc.BookDbUtil;
import com.ibm.web.jdbc.model.Book;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/BookControllerServlet")
public class BookControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookDbUtil bookDbUtil;

	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		// create our student db util ... and pass in the conn pool / datasource
		try {
			bookDbUtil = new BookDbUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
				listBook(request, response);
				break;

			case "ADD":
				addBook(request, response);
				break;

			case "LOAD":
				loadBook(request, response);
				break;

			case "UPDATE":
				updateBook(request, response);
				break;

			case "DELETE":
				deleteBook(request, response);
				break;

			default:
				listBook(request, response);
			}

		} catch (Exception exc) {
			throw new ServletException(exc);
		}

	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		String theBookId = request.getParameter("bookId");

		// delete student from database
		bookDbUtil.deleteBook(theBookId);

		// send them back to "list students" page
		listBook(request, response);
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		int id = Integer.parseInt(request.getParameter("bookId"));
		String bookName = request.getParameter("bookName");
		String bookAuthor = request.getParameter("bookAuthor");

		// create a new student object
		Book theBook = new Book(id, bookName, bookAuthor);

		// perform update on database
		bookDbUtil.updateBook(theBook);

		// send them back to the "list students" page
		listBook(request, response);

	}

	private void loadBook(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		String theBookId = request.getParameter("bookId");

		// get student from database (db util)
		Book theBook = bookDbUtil.getBook(theBookId);

		// place student in the request attribute
		request.setAttribute("THE_BOOK", theBook);

		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-book-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String bookName = request.getParameter("bookName");
		String bookAuthor = request.getParameter("bookAuthor");

		// create a new student object
		Book theBook = new Book(bookName, bookAuthor);

		// add the student to the database
		bookDbUtil.addBook(theBook);

		// send back to main page (the student list)
		listBook(request, response);
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get students from db util
		List<Book> books = bookDbUtil.getBooks();

		// add students to the request
		request.setAttribute("BOOK_LIST", books);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-books.jsp");
		dispatcher.forward(request, response);
	}

}
