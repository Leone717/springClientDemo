package com.sebok.springClientDemo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ClientDataModelImpl implements ClientDataModel {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> findByNameStartingWith(String namePrefix) {
        return entityManager.createQuery("select c from Client c where c.name like :namePrefix", Client.class).setParameter("namePrefix", namePrefix + "%").getResultList();
    }
}
