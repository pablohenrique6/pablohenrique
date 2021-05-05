package br.com.texoit.pablohenrique.controller;

import br.com.texoit.pablohenrique.bean.MaxBean;
import br.com.texoit.pablohenrique.bean.MinBean;
import br.com.texoit.pablohenrique.bean.RetornoIntervalosPremioBean;
import br.com.texoit.pablohenrique.exception.PabloHenriqueException;
import br.com.texoit.pablohenrique.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/produtor")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @GetMapping(value = "/premio/intervalos")
    ResponseEntity<RetornoIntervalosPremioBean> intervalosPremio(HttpServletRequest request,
                                                          HttpServletResponse response) throws PabloHenriqueException {
        RetornoIntervalosPremioBean retornoIntervalosPremioBean = new RetornoIntervalosPremioBean();

        MinBean minBean = producerService.menorIntervalo();
        retornoIntervalosPremioBean.getMin().add(minBean);
        MaxBean maxBean = producerService.maiorIntervalo();
        retornoIntervalosPremioBean.getMax().add(maxBean);

        return new ResponseEntity(retornoIntervalosPremioBean,  HttpStatus.OK);
    }
}
