package com.mballen.demoajax.web.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mballen.demoajax.web.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{
	
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
}
