package br.com.texoit.pablohenrique.repository.jdbc.dao;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Movie;

import java.util.List;

public interface MovieDAO {
    void inserir(List<Movie> movies) throws PabloHenriquePersistenceException;
}
