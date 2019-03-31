package com.itt.tds.coordinator.db.repository;

import java.util.*;

import com.itt.tds.TDSExceptions.DatabaseTransactionException;
import com.itt.tds.core.Client;

/**
 * 
 */
public interface ClientRepository {

    public int Add(Client client) throws DatabaseTransactionException;

    public void Modify(Client client) throws DatabaseTransactionException;

    public void Delete(int clientId) throws DatabaseTransactionException;

    public List<Client> GetClients()throws DatabaseTransactionException;
}