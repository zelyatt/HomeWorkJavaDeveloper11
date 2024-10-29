<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome to Time Servlet Application</h1>
<p>Click the link below to see the current time:</p>
<a href="<%= request.getContextPath() %>/time">View Current Time</a>
</body>
</html>
