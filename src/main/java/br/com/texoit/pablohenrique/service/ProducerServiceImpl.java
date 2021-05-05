package br.com.texoit.pablohenrique.service;

import br.com.texoit.pablohenrique.bean.MaxBean;
import br.com.texoit.pablohenrique.bean.MinBean;
import br.com.texoit.pablohenrique.exception.PabloHenriqueException;
import br.com.texoit.pablohenrique.model.Producer;
import br.com.texoit.pablohenrique.model.Win;
import br.com.texoit.pablohenrique.repository.jdbc.dao.ProducerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    ProducerDAO producerDAO;

    @Override
    public MaxBean maiorIntervalo() throws PabloHenriqueException {
        List<MaxBean> maxBeans = new ArrayList<>();
        List<Producer> producers = producerDAO.listarWins();

        for (Producer p : producers) {
            MaxBean maxBean = new MaxBean();
            Win win = p.maiorIntervalo();

            maxBean.setProducer(p.getNome());
            maxBean.setInterval(win.getInterval());
            maxBean.setPreviousWin(win.getPreviousWin());
            maxBean.setFollowingWin(win.getFollowingWin());

            maxBeans.add(maxBean);
        }

        MaxBean aux = null;
        if (!maxBeans.isEmpty()) {
            aux = maxBeans.get(0);

            for (MaxBean m : maxBeans) {
                if (m.getInterval() > aux.getInterval()) {
                    aux = m;
                }
            }
        }

        return aux;
    }

    @Override
    public MinBean menorIntervalo() throws PabloHenriqueException {
        List<MinBean> minBeans = new ArrayList<>();
        List<Producer> producers = producerDAO.listarWins();

        for (Producer p : producers) {
            MinBean minBean = new MinBean();
            Win win = p.menorIntervalo();

            minBean.setProducer(p.getNome());
            minBean.setInterval(win.getInterval());
            minBean.setPreviousWin(win.getPreviousWin());
            minBean.setFollowingWin(win.getFollowingWin());

            minBeans.add(minBean);
        }

        MinBean aux = null;
        if (!minBeans.isEmpty()) {
            aux = minBeans.get(0);

            for (MinBean m : minBeans) {
                if (m.getInterval() < aux.getInterval()) {
                    aux = m;
                }
            }
        }

        return aux;
    }
}
