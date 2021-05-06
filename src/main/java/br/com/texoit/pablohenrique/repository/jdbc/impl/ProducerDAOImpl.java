package br.com.texoit.pablohenrique.repository.jdbc.impl;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Movie;
import br.com.texoit.pablohenrique.model.Producer;
import br.com.texoit.pablohenrique.model.Studio;
import br.com.texoit.pablohenrique.model.Win;
import br.com.texoit.pablohenrique.repository.jdbc.dao.MovieDAO;
import br.com.texoit.pablohenrique.repository.jdbc.dao.ProducerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProducerDAOImpl implements ProducerDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Producer> listarWins() throws PabloHenriquePersistenceException {
        List<Producer> producers = new ArrayList<>();
        List<String> producersWins = producersWins();

        for (String p : producersWins) {
            Producer producer = new Producer();

            producer.setNome(p);

            List<Integer> wins = wins(producer);

            Integer interval = 0;
            Integer previousWin = null;
            Integer followingWin = null;

            for (Integer w : wins) {
                if (previousWin == null) {
                    previousWin = w;
                    continue;
                }

                followingWin = w;
                interval = followingWin - previousWin;

                Win win = new Win();

                win.setInterval(interval);
                win.setPreviousWin(previousWin);
                win.setFollowingWin(followingWin);

                producer.getWins().add(win);

                previousWin = followingWin;
            }

            Win winMaior = producer.maiorIntervalo();
            Win winMenor = producer.menorIntervalo();

            producers.add(producer);
        }
        return producers;
    }

    @Override
    public void inserir(List<Producer> producers) throws PabloHenriquePersistenceException {
        try {
            StringBuilder sqlProducer = new StringBuilder();
            sqlProducer.append("INSERT INTO TEXOIT.PRODUCER ");
            sqlProducer.append("(NAME) ");
            sqlProducer.append("VALUES ");
            sqlProducer.append("(?) ");

            StringBuilder sqlMovieProducer = new StringBuilder();
            sqlMovieProducer.append("INSERT INTO TEXOIT.MOVIE_PRODUCER ");
            sqlMovieProducer.append("(ID_MOVIE, ");
            sqlMovieProducer.append(" ID_PRODUCER) ");
            sqlMovieProducer.append("VALUES ");
            sqlMovieProducer.append("(?,?) ");

            for (Producer producer : producers) {
                //INSERT PRODUCER
                KeyHolder keyHolderProducer = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        new PreparedStatementCreator() {
                            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                PreparedStatement ps =
                                        connection.prepareStatement(sqlProducer.toString(), new String[]{"ID"});
                                ps.setString(1, producer.getNome());

                                return ps;
                            }
                        },
                        keyHolderProducer);
                Long idProducer = keyHolderProducer.getKey().longValue();

                //INSERT MOVIEPRODUCER
                KeyHolder keyHolderMovieProducer = new GeneratedKeyHolder();
                jdbcTemplate.update(
                        new PreparedStatementCreator() {
                            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                PreparedStatement ps =
                                        connection.prepareStatement(sqlMovieProducer.toString(), new String[]{"ID"});
                                ps.setLong(1, producer.getIdMovie());
                                ps.setLong(2, idProducer);

                                return ps;
                            }
                        },
                        keyHolderMovieProducer);
                Long idMovieProducer = keyHolderMovieProducer.getKey().longValue();
            }
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }


    private List<String> producersWins() throws PabloHenriquePersistenceException {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("    P.NAME ");
            sql.append("FROM ");
            sql.append("    TEXOIT.MOVIE M ");
            sql.append("    INNER JOIN TEXOIT.MOVIE_PRODUCER MS ");
            sql.append("        ON (M.ID = MS.ID_MOVIE) ");
            sql.append("    INNER JOIN TEXOIT.PRODUCER P ");
            sql.append("        ON (MS.ID_PRODUCER = P.ID) ");
            sql.append("WHERE ");
            sql.append("    M.WINNER = TRUE ");
            sql.append("GROUP BY ");
            sql.append("    P.NAME HAVING COUNT(*) > 1 ");

            List<String> producers = jdbcTemplate.queryForList(sql.toString(), String.class);
            return producers;
        } catch (EmptyResultDataAccessException erdae) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }

    private List<Integer> wins(Producer producer) throws PabloHenriquePersistenceException {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("    M.YEAR ");
            sql.append("FROM ");
            sql.append("    TEXOIT.MOVIE M ");
            sql.append("    INNER JOIN TEXOIT.MOVIE_PRODUCER MS ");
            sql.append("        ON (M.ID = MS.ID_MOVIE) ");
            sql.append("    INNER JOIN TEXOIT.PRODUCER P ");
            sql.append("        ON (MS.ID_PRODUCER = P.ID) ");
            sql.append("WHERE ");
            sql.append("    M.WINNER = TRUE ");
            sql.append("    AND P.NAME = ? ");

            List<Integer> wins = jdbcTemplate.queryForList(sql.toString(), Integer.class, producer.getNome());
            return wins;
        } catch (EmptyResultDataAccessException erdae) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }
}
