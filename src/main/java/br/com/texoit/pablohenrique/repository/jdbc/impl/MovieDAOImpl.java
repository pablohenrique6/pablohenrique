package br.com.texoit.pablohenrique.repository.jdbc.impl;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Movie;
import br.com.texoit.pablohenrique.repository.jdbc.dao.MovieDAO;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void inserir(List<Movie> movies) throws PabloHenriquePersistenceException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO TEXOIT.MOVIE ");
            sql.append("(YEAR, ");
            sql.append(" TITLE,");
            sql.append(" STUDIOS, ");
            sql.append(" PRODUCERS, ");
            sql.append(" WINNER) ");
            sql.append("VALUES ");
            sql.append("(?,?,?,?,?) ");

            for (Movie movie : movies) {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        new PreparedStatementCreator() {
                            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                PreparedStatement ps =
                                        connection.prepareStatement(sql.toString(), new String[]{"ID"});
                                ps.setInt(1, movie.getYear());
                                ps.setString(2, movie.getTitle());
                                ps.setString(3, movie.getStudios());
                                ps.setString(4, movie.getProducers());
                                ps.setBoolean(5, movie.isWinner());

                                return ps;
                            }
                        },
                        keyHolder);
                Long id = keyHolder.getKey().longValue();
            }
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }
}
