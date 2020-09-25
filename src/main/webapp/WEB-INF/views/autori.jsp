<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>I miei autori</title>
<c:url var="resources" value="/resources" />
<link rel="stylesheet"
	href="${ resources }/bootstrap/4.4.1/css/bootstrap.css" type="text/css">
</head>
<body>
	<div class="container">
		<h1>Pagina degli autori</h1>
		<p>Qualcosa da scrivere.</p>
		<%-- I button sono gli unici elementi interattivi HTML che possono esistere fuori da una form --%>
		<%--
		<button type="button" class="btn btn-primary" id="getAllButton">GET
			(all)</button> --%>
		<p>Elenco generato dinamicamente:
		<ul id="view"></ul>
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
			// prima versione: eseguo doGetAll in modalità differita
//			$("#getAllButton").click(doGetAll)
			// seconda versione: eseguo doGetAll in modalità immediata
			// JavaScript completa automaticamente l'elenco degli argomenti
			// nel caso in cui io ne metta di meno; usa il valore undefined
			doGetAll()
			// vedi sotto l'esercizio
			doGetSchedaAutore()
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
			// {"resources": [{"id":..., "firstName":..., "lastName":...}]}
			// jQuery mi mette a disposizione come argomento data l'oggetto JavaScript
			// corrispondente al documento JSON ricevuto
			// Questa espressione concatena nome e cognome del primo elemento di resources
			// data.resources[0].firstName + ' ' + data.resources[0].lastName
//			$("#out0").html(data.resources[0].firstName + ' ' + data.resources[0].lastName)
//			$("#out1").html(data.resources[1].firstName + ' ' + data.resources[1].lastName)
			// Una cosa del tipo <p id="out0"></p> si può ottenere con questa espressione
			// Posso generare un elemento XHTML (quindi occhio allo / finale) mettendolo
			// come argomento della function $; posso usare un secondo elemento che 
			// mi permette di dichiarare gli attributi sotto forma di oggetto JavaScript
//			$("<p />", {"id": "out0"})
			// ho creato l'elemento XHTML p con id=out0, adesso lo devo inserire nel mio 
			// documento HTML; uno dei modi è il metodo .appendTo che prende come argomento
			// il selettore jQuery che identifica l'elemento in cui voglio inserire la mia "p"
//			                          .appendTo($("#view"))
//			$("#out0").html(data.resources[0].firstName + ' ' + data.resources[0].lastName)
			// wow, posso farlo diventare finalmente un ciclo:
			for (var i in data.resources) {
				$("<li />", {"id": "out" + i}).appendTo($("#view"))
				$("#out" + i).html(data.resources[i].firstName + ' ' + data.resources[i].lastName)
			}
			// dopo l'esercizio di GET AJAX HTML
			for (var i in data.resources) {
				// uso il valore di schedaAutore come generatore di HTML
				// devo salvare il risultato di $(schedaAutore)
				var jqScheda = $(schedaAutore)
				// cerco l'elemento con class="first-name" e imposto il contenuto
				// impariamo un nuovo selettore: .<nome della class>
				// il metodo JavaScript per trovare l'elemento è find
				jqScheda.find(".first-name").html(data.resources[i].firstName)
				jqScheda.find(".last-name").html(data.resources[i].lastName)
				// decoriamo il nostro elemento HTML con la class col-4
				// il metodo JavaScript per aggiungere una class è addClass
				jqScheda.addClass("col-4")
				// a questo punto posso inserire il mio elemento HTML jqScheda
				// nella div con class="row"
				jqScheda.appendTo($(".row"))
			}
		}
		function getAllFail() {
			alert(':(')
		}
		// esercizio GET AJAX HTML
		var schedaAutore
		function doGetSchedaAutore() {
			// chiamata Ajax senza contenuto in request con contenuto in response
			$.ajax({
				url: "./resources/author.html?",
				method: "get",
				contentType: false,
				dataType: "html"
			}).done(getSchedaAutoreDone).fail(getSchedaAutoreFail)
		}
		function getSchedaAutoreDone(data) {
			schedaAutore = data
			// torniamo alla function getAllDone
		}
		function getSchedaAutoreFail() {
			alert(':(')
		}
	</script>
</body>
</html>
