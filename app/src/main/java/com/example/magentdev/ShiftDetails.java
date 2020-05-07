package com.example.magentdev;

import java.util.HashMap;


public class ShiftDetails {
    private static HashMap curr_map;

    public static HashMap getCurr_map() {
        return curr_map;
    }

    public static void setCurr_map(HashMap<String, String[]> curr_map) {
        ShiftDetails.curr_map = curr_map;
    }



}
