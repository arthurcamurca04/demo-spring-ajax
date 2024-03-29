//submeter formulario para o controller
$("#form-add-promo").submit(function(event){
	//bloquear o comportamento padrão do submit
	event.preventDefault();
	var promo = {};
	promo.link = $("#linkPromocao").val();
	promo.descricao = $("#descricao").val();
	promo.preco = $("#preco").val();
	promo.titulo = $("#titulo").val();
	promo.categoria = $("#categoria").val();
	promo.linkImagem = $("#linkImagem").attr("src");
	promo.site = $("#site").text();
	
	console.log('promo > ', promo);
	
	$.ajax({
		method: "POST",
		url: "/promocao/cadastrar",
		data: promo,
		beforeSend: function(){
			//removendo as mensagens
			$("span").closest(".error-span").remove();
			
			//removendo as bordas vermelhas
			$("#categoria").removeClass("is-invalid");
			$("#preco").removeClass("is-invalid");
			$("#linkPromocao").removeClass("is-invalid");
			$("#titulo").removeClass("is-invalid");
			
			$("#form-add-promo").hide();
			$("#loader-form").addClass("loader").show();
		},
		success: function(){
			$("#form-add-promo").each(function(){
				this.reset();
			});
			$("#linkImagem").attr("src", "/images/promo-dark.png");
			
			$("#alert").removeClass("alert alert-danger").addClass("alert alert-success").text("Promoção cadastrada com sucesso!");
		},
		
		statusCode:{
			422: function(xhr){
				console.log("status error: ", xhr.status);
				var errors = $.parseJSON(xhr.responseText);
				$.each(errors, function(key, val){
					$("#" + key).addClass("is-invalid");
					$("#error-" + key).addClass("invalid-feedback").append("<span class='error-span'>" + val + "</span>");
				})
				
				
			}
		},
		error: function(xhr){
			console.log("error > ", xhr.responseText);
			$("#alert").addClass("alert alert-danger").text("Não foi possível cadastrar esta promoção!")
		},
		complete: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#form-add-promo").fadeIn(250);
				$("#loader-form").removeClass("loader");
			})
		}
	})
	
});


//função java script para capturar as meta tags

$("#linkPromocao").on('change', function(){
	var url = $(this).val();
	if(url.length > 7){
		$.ajax({
			
			method: "POST",
			url: "/meta/info?url=" + url,
			cache: false,
			beforeSend: function(){
				$("#alert").removeClass("alert alert-danger alert-success").text("");
				$("#titulo").val("");
				$("#site").text("");
				$("#linkImagem").attr("src", "");
				$("#loader-img").addClass("loader");
			},
			success: function( data ){
				console.log(data);
				$("#titulo").val(data.title);
				$("#site").text(data.site.replace("@", ""));
				$("#linkImagem").attr("src", data.image);
				
			},
			statusCode: {
				404: function(){
					$("#alert").addClass("alert alert-danger").text("Nenhuma informação pode ser encontrada desta URL.")
					$("#linkImagem").attr("src", "/images/promo-dark.png");
				}
			},
			
			error: function(){
				$("#alert").addClass("alert alert-danger").text("Ops... Algo deu errado! Por favor, tente outra vez.")
				$("#linkImagem").attr("src", "/images/promo-dark.png");
			},
			complete: function(){
				$("#loader-img").removeClass("loader");
			}
		});
	}
});