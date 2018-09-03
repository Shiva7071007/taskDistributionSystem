package com.itt.tds.coordinator.db.repository;

import java.util.*;

/**
 * 
 */
public interface ClientRepository {

    /**
     * @param client
     */
    public void Add(Client client);

    /**
     * @param client
     */
    public void Modify(Client client);

    /**
     * @param client
     */
    public void Delete(Client client);

    /**
     * @return
     */
    public List<Client> GetClients();

}