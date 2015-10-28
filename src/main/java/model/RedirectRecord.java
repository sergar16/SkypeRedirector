package model;

import java.io.Serializable;

/**
 * Created by Serhii on 10/26/2015.
 */
public class RedirectRecord implements Serializable{
    public String from;
    public String to;

    public RedirectRecord(String from, String to) {
        this.from = from;
        this.to = to;
    }

}
