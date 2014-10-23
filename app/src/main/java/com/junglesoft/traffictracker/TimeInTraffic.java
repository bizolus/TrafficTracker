package com.junglesoft.traffictracker;

import com.orm.SugarRecord;

import org.joda.time.DateTime;

/**
 * Created by Abisolu.Adekoya on 09/10/2014.
 */
public class TimeInTraffic extends SugarRecord<TimeInTraffic> {

    DateTime startTime;
    DateTime endTime;
    //the day being monitored
    String trafficDay;
    double hoursSpent;

    //period is either morning or evening
    String trafficPeriod;

    public TimeInTraffic() {
    }

    public TimeInTraffic(String trafficDay, DateTime startTime,
                         DateTime endTime, String trafficPeriod, double hoursSpent) {

        this.trafficDay = trafficDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trafficPeriod = trafficPeriod;
        this.hoursSpent = hoursSpent;
    }
    public double getHoursSpent() {
        return this.hoursSpent;
    }

    public void setHoursSpent(double value) {
        this.hoursSpent = value;
    }

    public String getTrafficDay() {
        return this.trafficDay;
    }

    public void setTrafficDay(String value) {
        this.trafficDay = value;
    }
}
