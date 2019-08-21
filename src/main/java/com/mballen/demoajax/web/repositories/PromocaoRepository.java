package com.mballen.demoajax.web.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mballen.demoajax.web.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{
	
	@Query("SELECT COUNT(p.id) as count, max(p.dtCadastro) as lastDate from Promocao p WHERE p.dtCadastro > :data")
	Map<String, Object> totalAndUltimaPromocaoByCadastro(@Param("data") LocalDateTime data);
	
	@Query("SELECT p.dtCadastro FROM Promocao p")
	Page<LocalDateTime> findUltimaDataDePromocao(Pageable pageable);
	
	@Query("SELECT p.likes FROM Promocao p WHERE p.id = :id")
	int findLikesById(@Param("id") Long id);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE Promocao p SET p.likes = p.likes+1 WHERE p.id = :id")
	void updateLikes(@Param("id") Long id);
	
	@Query("SELECT DISTINCT p.site FROM Promocao p WHERE p.site LIKE %:site%")
	List<String> findSitesByTerm(@Param("site") String site);
	
	@Query("SELECT p FROM Promocao p where p.site like :site")
	Page<Promocao> findBySite(@Param("site") String site, Pageable pageable);
	
	@Query("SELECT p FROM Promocao p WHERE p.titulo LIKE %:search% or p.site LIKE %:search% or p.categoria.titulo LIKE %:search%")
	Page<Promocao> findByTituloOrSiteOrCategoria(@Param("search") String search, Pageable pageable);
	
	@Query("SELECT p FROM Promocao p WHERE p.preco = :preco")
	Page<Promocao> findByPreco(@Param("preco") BigDecimal preco, Pageable pageable);
}
