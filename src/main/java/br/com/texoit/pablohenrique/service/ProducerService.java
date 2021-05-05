package br.com.texoit.pablohenrique.service;

import br.com.texoit.pablohenrique.bean.MaxBean;
import br.com.texoit.pablohenrique.bean.MinBean;
import br.com.texoit.pablohenrique.exception.PabloHenriqueException;

public interface ProducerService {
    MaxBean maiorIntervalo() throws PabloHenriqueException;
    MinBean menorIntervalo() throws PabloHenriqueException;
}
