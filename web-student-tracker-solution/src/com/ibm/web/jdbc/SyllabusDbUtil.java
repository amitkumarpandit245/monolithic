package com.ibm.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ibm.web.jdbc.model.Syllabus;

public class SyllabusDbUtil {

	private DataSource dataSource;

	public SyllabusDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Syllabus> getSyllabus() throws Exception {
		
		List<Syllabus> syllabus = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from syllabus order by syllabus_name";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String syllabusName = myRs.getString("syllabus_name");
				String syllabusDesc = myRs.getString("syllabus_desc");
				
				// create new student object
				Syllabus tempSyllabus = new Syllabus(id, syllabusName, syllabusDesc);
				
				// add it to the list of students
				syllabus.add(tempSyllabus);				
			}
			
			return syllabus;		
		}
		finally {
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
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addSyllabus(Syllabus theSyllabus) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into syllabus "
					   + "(syllabus_name, syllabus_desc) "
					   + "values (?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the student
			myStmt.setString(1, theSyllabus.getSyllabusName());
			myStmt.setString(2, theSyllabus.getSyllabusDesc());

			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Syllabus getSyllabus(String theSyllabusId) throws Exception {

		Syllabus theSyllabus = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int syllabusId;
		
		try {
			// convert student id to int
			syllabusId = Integer.parseInt(theSyllabusId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "select * from syllabus where id=?";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, syllabusId);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String syllabusName = myRs.getString("syllabus_name");
				String syllabusDesc = myRs.getString("syllabus_desc");
				
				// use the studentId during construction
				theSyllabus = new Syllabus(syllabusId, syllabusName, syllabusDesc);
			}
			else {
				throw new Exception("Could not find Syllabus id: " + syllabusId);
			}				
			
			return theSyllabus;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void updateSyllabus(Syllabus theSyllabus) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update syllabus "
						+ "set syllabus_name=?, syllabus_desc=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theSyllabus.getSyllabusName());
			myStmt.setString(2, theSyllabus.getSyllabusDesc());
			myStmt.setInt(3, theSyllabus.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteSyllabus(String theSyllabusId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert student id to int
			int syllabusId = Integer.parseInt(theSyllabusId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete student
			String sql = "delete from syllabus where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, syllabusId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}
}















