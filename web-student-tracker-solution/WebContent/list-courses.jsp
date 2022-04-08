<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Student Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2 align="center">Courses List</h2>
			<a href="/web-student-tracker-solution/">Home</a>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Student -->
			
			<input type="button" value="Add Course" 
				   onclick="window.location.href='add-course-form.jsp'; return false;"
				   class="add-student-button"
			/>
			
			<table>
			
				<tr>
					<th>Course Name</th>
					<th>Description</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempCourse" items="${COURSE_LIST}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="CourseControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="courseId" value="${tempCourse.id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="CourseControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="courseId" value="${tempCourse.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempCourse.courseName} </td>
						<td> ${tempCourse.courseDesc} </td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this course?'))) return false">
							Delete</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








