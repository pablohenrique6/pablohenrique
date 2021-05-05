package br.com.texoit.pablohenrique.bean;

import java.util.ArrayList;
import java.util.List;

public class RetornoIntervalosPremioBean {
    private List<MinBean> min = new ArrayList<>();
    private List<MaxBean> max = new ArrayList<>();

    public List<MinBean> getMin() {
        return min;
    }

    public void setMin(List<MinBean> min) {
        this.min = min;
    }

    public List<MaxBean> getMax() {
        return max;
    }

    public void setMax(List<MaxBean> max) {
        this.max = max;
    }
}
