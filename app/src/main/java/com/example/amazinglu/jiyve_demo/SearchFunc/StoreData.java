package com.example.amazinglu.jiyve_demo.SearchFunc;

import java.util.ArrayList;
import java.util.List;

public class StoreData {
    private static List<String> stores ;
    static {
        stores =  new ArrayList<String>();
        stores.add("Clancy's bar");
        stores.add("Original Fish Co.");
        stores.add("Red Wok");
        stores.add("Boondocks");
        stores.add("cassies irish Pub");
    }

    public static List<String> getStores(){
            return stores;
        }
}
