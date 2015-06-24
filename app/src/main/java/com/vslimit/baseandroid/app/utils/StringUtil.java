package com.vslimit.baseandroid.app.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vslimit on 14-7-17.
 */
public class StringUtil {
    public static boolean blank(String paramStr) {
        return null == paramStr || paramStr.trim().length() == 0;
    }

    public static final Map<String,String> map = new HashMap<String,String>() ;

    static {
        map.put("female","女") ;
        map.put("male","男") ;
        map.put("unkown","保密") ;
    }

    private static final double EARTH_RADIUS = 6378137.0;
    public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public static long gps2km(double lat_a, double lng_a, double lat_b, double lng_b) {
        return  Math.round(gps2m(lat_a,lng_a,lat_b,lng_b) /1000);
    }

}
