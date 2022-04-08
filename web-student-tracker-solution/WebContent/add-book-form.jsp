<!DOCTYPE html>
<html>

<head>
	<title>Add Book</title>

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
		<h3>Add Book</h3>
		
		<form action="BookControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Book name:</label></td>
						<td><input type="text" name="bookName" /></td>
					</tr>

					<tr>
						<td><label>Author Name:</label></td>
						<td><input type="text" name="bookAuthor" /></td>
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
			<a href="BookControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











