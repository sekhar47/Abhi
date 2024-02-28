<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hello.....</title>
</head>
<body>
 <h1>SkillSage</h1>
 <form action="/register" method="post">
 <label for="name">Name: </label> <br/>
 <input type="text" name="name"/> <br/>
 
 <label for="username">UserName: </label> <br/>
 <input type="text" name="username"/> <br/>
 
 <label for="password">Password: </label> <br/>
 <input type="text" name="password"/> <br/>

 <button type="submit">Submit</button>
 </form>
</body> 
</html>