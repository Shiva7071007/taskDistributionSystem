package com.itt.tds.coordinator.db.repository;

import java.util.*;

/**
 * 
 */
public interface ClientRepository {

    /**
     * @param client 
     * @return
     */
    public int Add(Client client);

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