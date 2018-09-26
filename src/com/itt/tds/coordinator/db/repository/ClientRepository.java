package com.itt.tds.coordinator.db.repository;

import java.util.*;

import com.itt.tds.client.Client;

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
     * @param clientId
     */
    public void Delete(int clientId);

    /**
     * @return
     */
    public List<Client> GetClients();

}