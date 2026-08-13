<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>1ilsang.blog.me</title>
</head>
<body>
    <h1>Upload with Ajax</h1>
    <div class="uploadDiv">
        <input type="file" name="uploadFile" multiple>
    </div>
    <div class="uploadResult">
        <ul>

        </ul>
    </div>
    <button id="uploadBtn">Upload</button>
    <div class="bigPictureWrapper">
        <div class="bigPicture"></div>
    </div>
</body>
</html>
<script
        src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
<script>
$(document).ready(function () {
    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    var maxSize = 5242880; // 5MB

    function checkExtension(fileName, fileSize) {
        if(fileSize >= maxSize) {
            alert("?뚯씪 ?ъ씠利?珥덇낵");
            return false;
        }
        if(regex.test(fileName)) {
            alert("?대떦 醫낅쪟???뚯씪? ?낅줈?쒗븷 ???놁뒿?덈떎.");
            return false;
        }
        return true;
    }// end checkExtension()

    var cloneObj = $(".uploadDiv").clone();

    $("#uploadBtn").on("click", function (e) {
        var formData = new FormData();
        var inputFile = $("input[name='uploadFile']");
        var files = inputFile[0].files;
        console.log(files);

        //add filedate to formdata
        for(var i = 0; i < files.length; i++){
            if(!checkExtension(files[i].name, files[i].size)) {
                return false;
            }
            formData.append("uploadFile", files[i]);
        }
        $.ajax({
            url : '${pageContext.request.contextPath}/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                dataType: 'json',
                success: function (result) {
                    // alert("Uploaded");
                    console.log('?ㅽ썑 8:13', 'uploadAjax.jsp:64', result);
                    showUploadedFile(result);
                    $(".uploadDiv").html(cloneObj.html());
                }
        }); //$.ajax
    }); // uploadBtn click function

    // page 522
    var uploadResult = $(".uploadResult ul");
    function showUploadedFile(uploadResultArr) {
        var str = "";
        $(uploadResultArr).each(function (i, obj) {
            if (!obj.image) {
                var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
                var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
                str += "<li><div><a href='/download?fileName=" + fileCallPath + "'><img src='/resources/img/attach.png'>"
                    + obj.fileName + "</a><span data-file=\'" + fileCallPath + "\' data-type='file'> x </span></div></li>";
            } else {
                var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
                var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
                originPath = originPath.replace(new RegExp(/\\/g), "/");
                str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName=" + fileCallPath + "'></a>"
                    +"<span data-file=\'" + fileCallPath + "\' data-type='image'> x </span></li>";
            }
        });
        uploadResult.append(str);
    }// end showUploadedFile()

    $(".uploadResult").on("click", "span", function (e) {
        var targetFile = $(this).data("file");
        var type = $(this).data("type");
        // li 由ъ뒪?몃? ?쒓굅?섏쭊 ?딆쓬!!
        $.ajax({
            url: '/deleteFile',
            data: {fileName: targetFile, type: type},
            dataType: 'text',
            type: 'POST',
            success: function (result) {
                alert(result);
            }
        });
    });

    // $(".bigPictureWrapper").on("click", function (e) {
    //     $(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
    //     setTimeout(() => {
    //         $(this).hide();
    //     }, 1000);
    // });
    $(".bigPictureWrapper").on("click", function (e) {
        $(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
        setTimeout(function () {
            $(".bigPictureWrapper").hide();
        }, 1000);
    });
});

//page 540
function showImage(fileCallPath) {
    // alert(fileCallPath);
    $(".bigPictureWrapper").css("display", "flex").show();
    $(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>")
        .animate({width: '100%', height: '100%'}, 1000);
}
</script>

<style>
.uploadResult {
    width:100%;
    background-color: gray;
}
.uploadResult ul{
    display: flex;
    flex-flow: row;
    justify-content: center;
    align-items: center;
}
.uploadResult ul li{
    list-style: none;
    padding: 10px;
    align-content: center;
    text-align: center;
}
.uploadResult ul li img{
    width: 100px;
}
.uploadResult ul li span{
    color: white;
}
.bigPictureWrapper {
    position: absolute;
    display: none;
    justify-content: center;
    align-items: center;
    top: 0%;
    width: 100%;
    height: 100%;
    background-color: gray;
    z-index: 100;
    background: rgba(255,255,255,0.5);
}
.bigPicture{
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
}
.bigPicture img{
    width: 600px;
}
</style>
