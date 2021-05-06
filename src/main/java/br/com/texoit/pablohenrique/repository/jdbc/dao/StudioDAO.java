package br.com.texoit.pablohenrique.repository.jdbc.dao;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Studio;

import java.util.List;

public interface StudioDAO {
    void inserir(List<Studio> Studio) throws PabloHenriquePersistenceException;
}
