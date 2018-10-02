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
     * @throws Exception 
     */
    public int Add(Client client) throws Exception;

    /**
     * @param client
     * @throws Exception 
     */
    public void Modify(Client client) throws Exception;

    /**
     * @param clientId
     * @throws Exception 
     */
    public void Delete(int clientId) throws Exception;

    /**
     * @return
     * @throws Exception 
     */
    public List<Client> GetClients() throws Exception;

}