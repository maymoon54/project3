<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h1>�����Ϸ�</h1>
ȸ�����̵� : ${sessionScope.uID}<br>
ȸ���̸� :  ${sessionScope.name}<br>
��ǰ ���̵� : ${param.pdID}<br>
���� ���� : ${param.cnt}<br>
���pole : ${-param.score}<br>
��ȭ��ȣ : ${param.phone}<br>
����� : (${param.postcode})${param.address1} ${param.address3} ${param.address2} <br>
<hr>
�����ݾ� : ${param.total-(param.score*20)}<br>
</body>
</html>