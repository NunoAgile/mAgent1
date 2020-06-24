package com.example.magentdev;

import java.util.HashMap;


public class UpdateCashierCash {
    private static HashMap<String, Integer> cash_map;

    public static HashMap getCash_map() {
        return cash_map;
    }

    public static void setCash_map(HashMap cash_map) {
        UpdateCashierCash.cash_map = cash_map;
    }



    public static void updateCash(int amount, String crr, String opType){
        for (String entry : cash_map.keySet()) {
            if(crr.equals(entry)){
                if(opType.equals("CW")){
                    int currAmt = cash_map.get(entry);
                    int newAmount = currAmt - amount;
                    cash_map.put(entry,newAmount);
                }else if(opType.equals("CD")){
                    int currAmt = cash_map.get(entry);
                    int newAmount = currAmt + amount;
                    cash_map.put(entry,newAmount);
                }
            }

        }
    }

}
