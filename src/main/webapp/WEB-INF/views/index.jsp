<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Spring + Ajax</title>
<c:url var="resources" value="/resources" />
<link rel="stylesheet"
	href="${ resources }/bootstrap/4.4.1/css/bootstrap.css" type="text/css">
</head>
<body>
	<div class="container">
		<h1>Spring + Ajax</h1>
		<p>Spring + Ajax</p>
		<form:form action="${ currentUrl }" method="post"
			modelAttribute="formBean">
			<div class="form-group">
				<form:label path="firstName">Nome</form:label>
				<form:input class="form-control" path="firstName"
					placeholder="Nome dell'autore" />
				<form:errors path="firstName" class="text-danger"></form:errors>
			</div>
			<div class="form-group">
				<form:label path="lastName">Cognome</form:label>
				<form:input class="form-control" path="lastName"
					placeholder="Cognome dell'autore" />
				<form:errors path="lastName" class="text-danger"></form:errors>
			</div>
			<button type="button" class="btn btn-primary" id="getAllButton">GET
				(all)</button>
			<button type="button" class="btn btn-warning" id="postButton">POST</button>
			<button type="button" class="btn btn-info" id="getOneButton">GET
				(one)</button>
		</form:form>
		<div class="row"></div>
	</div>

	<script type="text/javascript"
		src="${ resources }/jquery/3.4.1/jquery.js"></script>
	<script type="text/javascript"
		src="${ resources }/popper.js/1.14.7/popper.js"></script>
	<script type="text/javascript"
		src="${ resources }/bootstrap/4.4.1/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(document).ready(initPage)
		function initPage() {
			// diamo un senso a tutti gli elementi interattivi
			$("#formBean").submit(submitForm)
			$("#getAllButton").click(doGetAll)
			$("#postButton").click(doPost)
			$("#getOneButton").click(doGetOne)
		}
		// queste form servono per raccogliere dati per Ajax
		// non servono per inviare dati alla Web Application
		function submitForm(evt) {
			evt.preventDefault()
		}
		// gestione del button getAll
		function doGetAll(evt) {
			// chiamata Ajax senza contenuto in request con contenuto in response
			$.ajax({
				url: "/authors",
				method: "get",
				contentType: false,
				dataType: "json"
			}).done(getAllDone).fail(getAllFail)
		}
		function getAllDone(data) {
			console.log(data)
		}
		function getAllFail() {
			alert(':(')
		}
		// gestione del button post
		function doPost(evt) {
			// chiamata Ajax con contenuto in request senza contenuto in response
			$.ajax({
				url: "/authors",
				method: "post",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(formData($("#formBean"))),
				processData: false,
				dataType: "json"
			}).done(postDone).fail(postFail)
		}
		function postDone() {
			alert(':(')
		}
		function postFail(jqXhr) {
			if (jqXhr.status === 201) {
				console.log(jqXhr.getResponseHeader('location'))
				oneLocation = jqXhr.getResponseHeader('location')
			} else {
				alert(':(')
			}
		}
		var oneLocation
		// gestione del button getOne
		function doGetOne(evt) {
			// chiamata Ajax senza contenuto in request con contenuto in response
			$.ajax({
				url: oneLocation,
				method: "get",
				contentType: false
			}).done(getOneDone).fail(getOneFail)
		}
		function getOneDone(data) {
			console.log(data)
		}
		function getOneFail() {
			alert(':(')
		}
		// function di utilità
		function formData(jqForm) {
			var r = {}
			for (v of jqForm.serializeArray()) {
				r[v.name] = v.value
			}
			return r
		}
	</script>
</body>
</html>
