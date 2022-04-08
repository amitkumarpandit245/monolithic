package com.ibm.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ibm.web.jdbc.model.Book;

public class BookDbUtil {

	private DataSource dataSource;

	public BookDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Book> getBooks() throws Exception {

		List<Book> books = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from book order by book_name";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				String BookName = myRs.getString("book_name");
				String bookAuthor = myRs.getString("book_author");

				// create new student object
				Book tempBook = new Book(id, BookName, bookAuthor);

				// add it to the list of students
				books.add(tempBook);
			}

			return books;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addBook(Book theBook) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create sql for insert
			String sql = "insert into book " + "(book_name, book_author) " + "values (?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the student
			myStmt.setString(1, theBook.getBookName());
			myStmt.setString(2, theBook.getBookAuthor());

			// execute sql insert
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Book getBook(String theBookId) throws Exception {

		Book theBook = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int bookId;

		try {
			// convert student id to int
			bookId = Integer.parseInt(theBookId);

			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to get selected student
			String sql = "select * from book where id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, bookId);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String bookName = myRs.getString("book_name");
				String bookAuthor = myRs.getString("book_author");

				// use the studentId during construction
				theBook = new Book(bookId, bookName, bookAuthor);
			} else {
				throw new Exception("Could not find Book id: " + bookId);
			}

			return theBook;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void updateBook(Book theBook) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "update book " + "set book_name=?, book_author=? " + "where id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theBook.getBookName());
			myStmt.setString(2, theBook.getBookAuthor());
			myStmt.setInt(3, theBook.getId());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteBook(String theBookId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// convert student id to int
			int bookId = Integer.parseInt(theBookId);

			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to delete student
			String sql = "delete from book where id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, bookId);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}
	}
}
