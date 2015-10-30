package model;

import java.io.Serializable;

/**
 * Created by Serhii on 10/26/2015.
 */
public class RedirectRecord implements Serializable{
    public String from;
    public String to;
    public boolean doubleDirection;
    public RedirectRecord(String from, String to) {
        this.from = from;
        this.to = to;
        this.doubleDirection=false;
    }

    public RedirectRecord(String from, String to,boolean doubleDirection) {
        this.from = from;
        this.to = to;
        this.doubleDirection=doubleDirection;
    }

}
