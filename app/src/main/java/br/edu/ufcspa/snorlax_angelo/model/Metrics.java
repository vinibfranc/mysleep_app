package br.edu.ufcspa.snorlax_angelo.model;

/**
 * Created by icaromsc on 19/06/2017.
 *
 * Classe modelo de Metricas para plotagem no grafico
 *
 */

public class Metrics {
    private int nRespEvents;
    private float hoursPeriod;




    public Metrics(int nRespEvents, float hoursPeriod) {
        this.nRespEvents = nRespEvents;
        this.hoursPeriod = hoursPeriod;
    }


    public int getnRespEvents() {
        return nRespEvents;
    }

    public void setnRespEvents(int nRespEvents) {
        this.nRespEvents = nRespEvents;
    }

    public float getHoursPeriod() {
        return hoursPeriod;
    }

    public void setHoursPeriod(float hoursPeriod) {
        this.hoursPeriod = hoursPeriod;
    }

    public Metrics(){

    }



}
