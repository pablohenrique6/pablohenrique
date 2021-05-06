package br.com.texoit.pablohenrique.repository.jdbc.impl;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Producer;
import br.com.texoit.pablohenrique.model.Studio;
import br.com.texoit.pablohenrique.model.Win;
import br.com.texoit.pablohenrique.repository.jdbc.dao.ProducerDAO;
import br.com.texoit.pablohenrique.repository.jdbc.dao.StudioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudioDAOImpl implements StudioDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void inserir(List<Studio> Studios) throws PabloHenriquePersistenceException {
        try {
            StringBuilder sqlStudio = new StringBuilder();
            sqlStudio.append("INSERT INTO TEXOIT.STUDIO ");
            sqlStudio.append("(NAME) ");
            sqlStudio.append("VALUES ");
            sqlStudio.append("(?) ");

            StringBuilder sqlMovieStudio = new StringBuilder();
            sqlMovieStudio.append("INSERT INTO TEXOIT.MOVIE_STUDIO ");
            sqlMovieStudio.append("(ID_MOVIE, ");
            sqlMovieStudio.append(" ID_STUDIO)");
            sqlMovieStudio.append("VALUES ");
            sqlMovieStudio.append("(?,?) ");

            for (Studio studio : Studios) {
                //INSERT STUDIO
                KeyHolder keyHolderProducer = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        new PreparedStatementCreator() {
                            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                PreparedStatement ps =
                                        connection.prepareStatement(sqlStudio.toString(), new String[]{"ID"});
                                ps.setString(1, studio.getNome());

                                return ps;
                            }
                        },
                        keyHolderProducer);
                Long idStudio = keyHolderProducer.getKey().longValue();

                //INSERT MOVIESTUDIO
                KeyHolder keyHolderMovieProducer = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        new PreparedStatementCreator() {
                            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                PreparedStatement ps =
                                        connection.prepareStatement(sqlMovieStudio.toString(), new String[]{"ID"});
                                ps.setLong(1, studio.getIdMovie());
                                ps.setLong(2, idStudio);

                                return ps;
                            }
                        },
                        keyHolderMovieProducer);
                Long idMovieStudio = keyHolderProducer.getKey().longValue();
            }
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }
}
