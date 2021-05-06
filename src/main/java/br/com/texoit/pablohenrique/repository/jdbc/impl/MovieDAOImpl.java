package br.com.texoit.pablohenrique.repository.jdbc.impl;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Movie;
import br.com.texoit.pablohenrique.model.Producer;
import br.com.texoit.pablohenrique.model.Studio;
import br.com.texoit.pablohenrique.repository.jdbc.dao.MovieDAO;
import br.com.texoit.pablohenrique.repository.jdbc.dao.ProducerDAO;
import br.com.texoit.pablohenrique.repository.jdbc.dao.StudioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ProducerDAO producerDAO;
    @Autowired
    StudioDAO studioDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void inserir(List<Movie> movies) throws PabloHenriquePersistenceException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO TEXOIT.MOVIE ");
            sql.append("(YEAR, ");
            sql.append(" TITLE,");
            sql.append(" WINNER) ");
            sql.append("VALUES ");
            sql.append("(?,?,?) ");

            for (Movie movie : movies) {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        new PreparedStatementCreator() {
                            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                PreparedStatement ps =
                                        connection.prepareStatement(sql.toString(), new String[]{"ID"});
                                ps.setInt(1, movie.getYear());
                                ps.setString(2, movie.getTitle());
                                ps.setBoolean(3, movie.isWinner());

                                return ps;
                            }
                        },
                        keyHolder);
                Long id = keyHolder.getKey().longValue();
                movie.setId(id);

                for (Producer p : movie.getProducers()) {
                    p.setIdMovie(id);
                }
                for (Studio s : movie.getStudios()) {
                    s.setIdMovie(id);
                }

                studioDAO.inserir(movie.getStudios());
                producerDAO.inserir(movie.getProducers());
            }
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }
}
