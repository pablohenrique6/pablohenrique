package br.com.texoit.pablohenrique.repository.jdbc.impl;

import br.com.texoit.pablohenrique.exception.PabloHenriquePersistenceException;
import br.com.texoit.pablohenrique.model.Movie;
import br.com.texoit.pablohenrique.model.Producer;
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


    private List<String> producersWins() throws PabloHenriquePersistenceException {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("    PRODUCERS ");
            sql.append("FROM ");
            sql.append("    TEXOIT.MOVIE ");
            sql.append("WHERE ");
            sql.append("    WINNER = TRUE ");
            sql.append("GROUP BY ");
            sql.append("    PRODUCERS HAVING COUNT(*) > 1 ");

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
            sql.append("    YEAR ");
            sql.append("FROM ");
            sql.append("    TEXOIT.MOVIE ");
            sql.append("WHERE ");
            sql.append("    PRODUCERS = ? ");
            sql.append("ORDER BY ");
            sql.append("    YEAR ASC ");

            List<Integer> wins = jdbcTemplate.queryForList(sql.toString(), Integer.class, producer.getNome());
            return wins;
        } catch (EmptyResultDataAccessException erdae) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new PabloHenriquePersistenceException(e.getMessage());
        }
    }
}
