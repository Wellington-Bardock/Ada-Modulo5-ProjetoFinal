package br.com.ada.projetomodulo5.repository;

import br.com.ada.projetomodulo5.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT g FROM Produto g WHERE g.uuid = :uuid")
    List<Produto> findByUuid(@Param("uuid") String nome);

}
