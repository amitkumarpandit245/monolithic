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
			<h2 align="center">Syllabus List</h2>
			<a href="/university-tracker-solution/">Home</a>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Student -->
			
			<input type="button" value="Add Syllabus" 
				   onclick="window.location.href='add-syllabus-form.jsp'; return false;"
				   class="add-student-button"
			/>
			
			<table>
			
				<tr>
					<th>Syllabus Name</th>
					<th>Description</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempSyllabus" items="${SYLLABUS_LIST}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="SyllabusControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="syllabusId" value="${tempSyllabus.id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="SyllabusControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="syllabusId" value="${tempSyllabus.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempSyllabus.syllabusName} </td>
						<td> ${tempSyllabus.syllabusDesc} </td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this Syllabus?'))) return false">
							Delete</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








