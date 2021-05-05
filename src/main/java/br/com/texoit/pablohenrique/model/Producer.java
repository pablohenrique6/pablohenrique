package br.com.texoit.pablohenrique.model;

import java.util.ArrayList;
import java.util.List;

public class Producer {
    private String nome;
    private List<Win> wins = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Win> getWins() {
        return wins;
    }

    public void setWins(List<Win> wins) {
        this.wins = wins;
    }

    public Win maiorIntervalo() {
        if (this.getWins().size() == 1) {
            return this.getWins().get(0);
        } else {
            Integer interval = 0;
            Win aux = null;

            for (Win w : this.getWins()) {
                if (w.getInterval() >= interval) {
                    interval = w.getInterval();
                    aux = w;
                }
            }

            return aux;
        }
    }

    public Win menorIntervalo() {
        if (this.getWins().size() == 1) {
            return this.getWins().get(0);
        } else {
            Integer interval = this.getWins().get(0).getInterval();
            Win aux = null;

            for (Win w : this.getWins()) {
                if (w.getInterval() <= interval) {
                    interval = w.getInterval();
                    aux = w;
                }
            }

            return aux;
        }
    }
}
