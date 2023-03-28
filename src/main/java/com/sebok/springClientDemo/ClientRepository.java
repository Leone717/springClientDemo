package com.sebok.springClientDemo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c from Client c WHERE c.id= ?1 AND c.name = ?2 ORDER BY c.id")
    List<Client> list(long Id, String name);


    @Query("select distinct c from Client c")
    Optional<Client> findClientByClientname(String clientname);

}
