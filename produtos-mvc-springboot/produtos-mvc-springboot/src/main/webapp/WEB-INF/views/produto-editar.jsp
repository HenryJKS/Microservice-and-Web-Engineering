<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
		<meta charset="ISO-8859-1">
		<title>Produtos</title>
		
		<!-- ATALHO PARA TRAZER A URL DE CONTEXTO DO PROJETO -->
		<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
		
		<!-- ATALHOS PARA OS ARQUIVOS ESTATICOS DO WEBJAR -->
		<spring:url value="${contextPath}/webjars/bootstrap/css" var="css" />
		<spring:url value="${contextPath}/webjars/jquery" var="jquery" />
		<spring:url value="${contextPath}/webjars/bootstrap/js" var="js" />

		<!-- APONTAMENTO PARA O CSS DO BOOTSTRAP -->
		<link href="${css}/bootstrap.min.css" rel="stylesheet">
		
		<!-- CSS PARA NOSAS CUSTOMIZACOES -->
		<link href="/css/style.css" rel="stylesheet">

		<!-- LINKS PARA USAR FONTE CUSTOMIZAVEL DO GOOGLE FONTES -->
		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap" rel="stylesheet">
				
	</head>
	<body>
		<header>
			<nav class="navbar navbar-dark bg-dark">
			  <div class="container-fluid">
			    <a class="navbar-brand fonte-titulo" href="${contextPath}/produtos">Produtos</a>
			  </div>
			</nav>
		</header>
    
    <main class="container">
	
		<section id="formulario">
	        <!-- Call to Action Well -->
	        <div class="row">
	            <div class="col-lg-12">
	                <div class="well">
						
						<h2 class="fonte-titulo texto-cor-especial">Produto</h2>
						
						<form action="${contextPath}/produto/update" method="post">
						
							<div class="form-group">
								<input type="hidden" name="id" id="id" value="${produto.id}" />
	                        </div>
							
							<div class="form-group">
								<label class="control-label" for="nome">Nome:</label>
								<input type="text" name="nome" id="nome" value="${produto.nome}" class="form-control" maxlength="50" size="50" />
	                        </div>
	                        <div class="form-group">
								<label class="control-label" for="nome">SKU:</label>
								<input type="text" name="sku" id="sku" value="${produto.sku}" class="form-control" maxlength="50" size="50" />
							</div>
							
							<div class="form-group">
								<label class="control-label" for="mesnagem">Descrição:</label>
								<textarea id="descricao" class="form-control" name="descricao" rows="4" cols="100">${produto.descricao}</textarea>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="preco">Preço:</label>
								<input type="text" name="preco" id="preco" value="${produto.preco}" class="form-control" maxlength="14" size="15" />
							</div>
							
							<div class="form-group">
								<label class="control-label" for="mesnagem">Características:</label>
								<textarea id="mensagem" class="form-control" name="caracteristicas" rows="4" cols="100">${produto.caracteristicas}</textarea>
							</div>
							<hr>
							
							<a class="btn btn-secondary btn-sm" href="${contextPath}/produtos">Cancelar</a>
							<button type="submit" class="btn btn-primary btn-sm">Gravar</button>
						</form>
	                </div>
	            </div>
	        </div>
	    </section>
	</main>
	
	<!-- APONTAMENTO PARA AS BIBLIOTECAS E JAVASCRIPT DO JQUERY E BOOTSTRAP -->
	<script src="${jquery}/jquery.min.js"></script>
	<script src="${js}/bootstrap.min.js"></script>

</body>
</html>