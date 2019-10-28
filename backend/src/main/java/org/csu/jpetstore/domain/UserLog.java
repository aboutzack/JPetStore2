package org.csu.jpetstore.domain;

import java.util.Date;

public class UserLog {
    private Date timeStamp;
    private String log;

    public UserLog() {
    }

    public UserLog(Date timeStamp, String log) {
        this.timeStamp = timeStamp;
        this.log = log;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
