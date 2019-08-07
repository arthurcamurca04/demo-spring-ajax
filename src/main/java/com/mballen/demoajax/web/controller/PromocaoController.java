package com.mballen.demoajax.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.mballen.demoajax.web.model.Categoria;
import com.mballen.demoajax.web.model.Promocao;
import com.mballen.demoajax.web.repositories.CategoriaRepository;
import com.mballen.demoajax.web.repositories.PromocaoRepository;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {
	
	private static Logger log = LoggerFactory.getLogger(PromocaoController.class);

	@Autowired
	private PromocaoRepository promocaoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@ModelAttribute("categorias")
	public List<Categoria> getCategorias(){
		return categoriaRepository.findAll();
	}	
	
	@GetMapping("/add")
	public ModelAndView abrirCadastroPromocao(Promocao promocao) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/promo-add");
		return mv;		
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastro(@Valid Promocao promocao, BindingResult result) {
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.unprocessableEntity().body(errors);
		}else {
		
			log.info("Promocao {}", promocao.toString());
			promocao.setDtCadastro(LocalDateTime.now());
			promocaoRepository.save(promocao);
			return ResponseEntity.ok().build();
		}
	}
}