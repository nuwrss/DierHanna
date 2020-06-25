package com.digitaldreamsapps.dierhanna.models;


public class Measurements {
   private String obsTimeLocal;
   private double solarRadiation;
   private double humidity;

    public String getObsTimeLocal() {
        return obsTimeLocal;
    }

    public void setObsTimeLocal(String obsTimeLocal) {
        this.obsTimeLocal = obsTimeLocal;
    }

    public double getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(double solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    private Metric metric;

    public int getTemp(){
        return  metric.getTemp();
    }
    public double getPressure(){
        return  metric.getPressure();
    }
    public double getWindSpeed(){
        return  metric.getWindSpeed();
    }
}
