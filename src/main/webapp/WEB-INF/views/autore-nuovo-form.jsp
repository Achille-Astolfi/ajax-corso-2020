<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:url var="resources" value="/resources" />
<link rel="stylesheet"
	href="${ resources }/bootstrap/4.4.1/css/bootstrap.css" type="text/css">
</head>
<body>
	<div class="container">
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
			<button type="button" class="btn btn-primary" id="postButton">Inserisci</button>
		</form:form>

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
			$("#formBean").submit(submitForm)
			$("#postButton").click(doPost)
			getAvviso()
		}
		// queste form servono per raccogliere dati per Ajax
		// non servono per inviare dati alla Web Application
		function submitForm(evt) {
			evt.preventDefault()
		}
		// gestione del button post
		function doPost(evt) {
			// chiamata Ajax con contenuto in request senza contenuto in response
			$.ajax({
				url: "/authors",
				method: "post",
				contentType: "application/json; charset=UTF-8",
				// questa espressione complicata trasforma gli elementi interattivi della form
				// con id="formBean" in un JSON che ha come chiavi le path dei campi e come
				// valori il corrisponde input digitato dall'utente
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
				getOneLocation()
			} else {
				$(avviso).appendTo($(".container"))
			}
		}
		var oneLocation
		// function di utilità
		function formData(jqForm) {
			var r = {}
			for (v of jqForm.serializeArray()) {
				r[v.name] = v.value
			}
			return r
		}
		// chiamata Ajax GET con uri oneLocation
		function getOneLocation() {
			$.ajax({
				url: oneLocation,
				method: "get",
				contentType: false,
				dataType: "json"
			}).done(getOneLocationDone).fail(getOneLocationFail)
		}
		function getOneLocationDone(data) {
			alert("L'autore " + data.firstName + " " + data.lastName + " è stato inserito con id " + data.id + ".")
			$("#formBean").trigger("reset")
		}
		function getOneLocationFail() {
			alert(':(')
		}
		// chiamata Ajax GET per recuperare HTML
		var avviso
		function getAvviso() {
			$.ajax({
				url: "./resources/alert.html",
				method: "get",
				contentType: false,
				dataType: "html"
			}).done(getAvvisoDone).fail(getAvvisoFail)
		}
		function getAvvisoDone(data) {
			avviso = data
		}
		function getAvvisoFail() {
			alert(':(')
		}
	</script>
</body>
</html>
