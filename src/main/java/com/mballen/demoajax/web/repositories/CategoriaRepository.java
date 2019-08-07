package com.mballen.demoajax.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mballen.demoajax.web.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
