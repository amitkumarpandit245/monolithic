<!DOCTYPE html>
<html>

<head>
	<title>Add Course</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Staffordshire University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Syllabus</h3>
		
		<form action="SyllabusControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Syllabus name:</label></td>
						<td><input type="text" name="syllabusName" /></td>
					</tr>

					<tr>
						<td><label>Description:</label></td>
						<td><input type="text" name="syllabusDesc" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="SyllabusControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











