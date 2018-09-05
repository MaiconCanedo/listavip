package br.com.alura.listavip.repository;

import br.com.alura.listavip.model.Convidado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConvidadoRepository extends CrudRepository<Convidado, Long> {

    Iterable<Convidado> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT c FROM Convidado c WHERE UPPER(c.nome) LIKE %:pNome%")
    Iterable<Convidado> findCustomByNome(@Param("pNome")String nome);
}