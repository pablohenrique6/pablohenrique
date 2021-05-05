package br.com.texoit.pablohenrique.repository.jdbc.dao;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Producer;

import java.util.List;

public interface ProducerDAO {
    List<Producer> listarWins() throws PabloHenriquePersistenceException;
}
