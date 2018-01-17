<%@ page import="com.yyq.controller.DealData" language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <script src="jquery-3.2.1.min.js"></script>
</head>
<body>
<h2>请上传.txt的文本文件</h2>
<form id="form1"  method="get" action="plane">
    请输入消息序列号:<input type="text" name="id" id="id" value=""/>
    <button type="submit" >验证</button>
</form>
<form action="plane" enctype="multipart/form-data" method="post">
    上传文件：<input type="file" name="file1">
    <%--请输入id:<input type="text" name="id" id="id" value=""/>--%>
    <button type="submit" >上传</button>
</form>
<h2>文件上传状态：<%=request.getAttribute("message")%></h2>
<h2>验证结果：<%=request.getAttribute("result")%></h2>

<%--<%--%>
    <%--DealData p = new DealData();--%>
    <%--p.ff();--%>
<%--%>--%>

</body>
</html>
