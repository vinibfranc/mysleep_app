package br.edu.ufcspa.snorlax_angelo.model;

/**
 * Created by icaromsc on 19/06/2017.
 */

public class Metrics {
    private int nRespEvents;
    private float hoursPeriod;
    public double[] events=new double[]{0.183352784,
            0.590931412,
            0.592215602,
            0.593141219,
            0.594262799,
            0.659443726,
            0.663829982,
            0.695535897,
            0.697545572,
            0.699196674,
            0.700251543,
            0.701452343,
            0.715991194,
            0.718221844,
            0.719122449,
            0.720227349,
            0.722678981,
            0.723704667,
            0.724822077,
            0.725856097,
            0.728170144,
            0.730238183,
            0.731247191,
            0.733344419,
            0.734440986,
            0.736642454,
            0.74454355,
            0.745552557};;



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
