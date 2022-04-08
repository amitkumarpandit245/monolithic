<!DOCTYPE html>
<html>

<head>
<title>Update Course</title>

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
		<h3>Update Book</h3>

		<form action="BookControllerServlet" method="GET">

			<input type="hidden" name="command" value="UPDATE" /> <input
				type="hidden" name="bookId" value="${THE_BOOK.id}" />

			<table>
				<tbody>
					<tr>
						<td><label>Book Name:</label></td>
						<td><input type="text" name="bookName"
							value="${THE_BOOK.bookName}" /></td>
					</tr>

					<tr>
						<td><label>Author Name:</label></td>
						<td><input type="text" name="bookAuthor"
							value="${THE_BOOK.bookAuthor}" /></td>
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











