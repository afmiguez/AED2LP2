/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.aed2.project.inverted_index;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jtorres
 */
public class OSMNodeElementPOI {

    private String id;
    private double lat;
    private double lon;
    private HashMap<String, String> keyval; // key,val pairs

    public OSMNodeElementPOI(String id) {
        this.id = id;
        keyval = new HashMap();
    }

    public OSMNodeElementPOI(String id, double lat, double lon) {
        this(id);
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        String str = "OSMNodeElementPOI{" + "id=" + id + ",lat=" + lat + ",lon=" + lon;// + ", name=" + name + '}';
        if (keyval.size() > 0) {
            str += ",";
        }
        for (Map.Entry<String, String> entry : keyval.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            str += '(' + key + '=' + value + ')';
        }
        str += '}';
        return str;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * @return the value
     */
    public String getVal(String key) {
        return keyval.get(key);
    }

    public boolean containsKey(String key) {
        return keyval.containsKey(key);
    }

    public void putKeyVal(String key, String val) {
        keyval.put(key, val);
    }

    public Set keySet() {
        return keyval.keySet();
    }

}
