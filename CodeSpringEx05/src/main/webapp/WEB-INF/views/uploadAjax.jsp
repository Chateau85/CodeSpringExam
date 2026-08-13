<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Upload with Ajax</h1>
	<div class='uploadDiv'>
		<input type='file' name='uploadFile' multiple>
	</div>
	<button id='uploadBtn'>Upload</button>

	<script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>

	<script>
		$(document).ready(function() {
			var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
			var maxSize = 5242880;//5MB

			function checkExtension(fileName, fileSize) {
				if (fileSize >= maxSize) {
					alert("?뚯씪 ?ъ씠利?珥덇낵");
					return false;
				}
				if (regex.test(fileName)) {
					alert("?대떦 醫낅쪟???뚯씪? ?낅줈?쒗븷 ???놁뒿?덈떎.");
					return false;
				}
				return true;
			}

			$("#uploadBtn").on("click", function(e) {
				var formData = new FormData();
				var inputFile = $("input[name='uploadFile']");
				var files = inputFile[0].files;
				console.log(files);

				for (var i = 0; i < files.length; i++) {
					if (!checkExtension(files[i].name, files[i].size)) {
						return false;
					}
					formData.append("uploadFile", files[i]);
				}

				$.ajax({
					url : '${pageContext.request.contextPath}/uploadAjaxAction',
					processData : false,
					contentType : false,
					data : formData,
					type : 'POST',
					success : function(result) {
						alert('Uploaded');
					}
				});
			});
		});
	</script>
</body>
</html>
