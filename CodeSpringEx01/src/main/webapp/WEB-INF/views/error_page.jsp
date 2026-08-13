<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Request error</title></head>
<body>
    <h1>Request error</h1>
    <p><c:out value="${empty errorMessage ? 'An unexpected error occurred. Please try again later.' : errorMessage}" /></p>
</body>
</html>