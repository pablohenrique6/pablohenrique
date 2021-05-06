package br.com.texoit.pablohenrique.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    //ID
    private Long id;
    //year
    private int year;
    //title
    private String title;
    //winner
    private boolean winner;
    //studios
    private List<Studio> studios = new ArrayList<>();
    //producers
    private List<Producer> producers = new ArrayList<>();

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
