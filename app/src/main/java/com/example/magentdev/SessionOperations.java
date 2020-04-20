package com.example.magentdev;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;



import org.json.JSONException;
import org.json.JSONObject;


public class SessionOperations {


    public static void wsrAuthSessionClose(String dtk, String stk, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {

        String data = "{\"V\":\"1\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\"}";
        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_auth/api/wsrAuthSessionClose";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            volleyCallback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public static void wsrAuthReg(boolean hasDtk, String username, String password, String dtk, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        final String data;
        if(!hasDtk) {
            data = "{\"V\":\"1\",\"C\":{\"V\":\"1\",\"AMT\":\"P\",\"AMT.P\":{\"V\":\"1\",\"USR\":\""+username+"\",\"PWD\":\""+password+"\"},\"AMT.C\":{\"V\":\"1\",\"CST\":\"string\",\"CFT\":\"string\",\"CET\":\"string\",\"CKA\":\"string\",\"CRT\":\"string\"}},\"D\":{\"V\":\"1\",\"DTP\":\"A\",\"DTP.P\":{\"V\":\"1\",\"BID\":0,\"PID\":0,\"SID\":0,\"DID\":0,\"DSM\":\"string\",\"DSN\":\"string\",\"DSV\":\"string\",\"DAM\":\"string\",\"DAN\":\"string\",\"DAV\":\"string\",\"DAD\":\"string\"},\"DTP.A\":{\"V\":\"1\",\"BID\":0,\"PID\":0,\"SID\":0,\"DID\":0,\"DFS\":\"string\",\"DFM\":\"string\",\"DFN\":\"string\",\"DFV\":\"string\",\"DFD\":\"string\"},\"DTP.M\":{\"V\":\"1\",\"DFF\":\"P\",\"DMI\":\"string\",\"DSS\":\"string\",\"DSM\":\"string\",\"DSN\":\"string\",\"DSV\":\"string\",\"DSD\":\"string\",\"DHR\":\"string\"},\"DTP.B\":{\"V\":\"1\",\"IPA\":\"string\",\"HUA\":\"string\",\"DMI\":\"string\",\"DSS\":\"string\",\"DSM\":\"string\",\"DSN\":\"string\",\"DSV\":\"string\",\"DBS\":\"string\",\"DBM\":\"string\",\"DBN\":\"string\",\"DBV\":\"string\",\"DBR\":\"string\",\"DBD\":\"string\",\"DHR\":\"string\"},\"DMS\":\"string\",\"DMB\":\"string\",\"DMM\":\"string\",\"DMV\":\"string\"},\"W\":{\"V\":\"1\",\"CAS\":\"string\",\"CAM\":\"string\",\"CAN\":\"string\",\"CAV\":\"string\",\"CAD\":\"string\",\"CA\":[{\"V\":\"1\",\"AST\":\"string\",\"ASC\":\"string\",\"ASV\":\"string\",\"ASD\":\"string\"}]},\"LD\":{\"V\":\"1\",\"DCC\":\"string\",\"DLC\":\"string\",\"TMZ\":\"string\",\"LNF\":\"string\",\"LCF\":\"string\",\"LDF\":\"string\"},\"GL\":{\"V\":\"1\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"}}";
        }else{
            data = "{\"V\":\"1\",\"C\":{\"V\":\"1\",\"AMT\":\"P\",\"AMT.P\":{\"V\":\"1\",\"USR\":\""+username+"\",\"PWD\":\""+password+"\"},\"AMT.C\":{\"V\":\"1\",\"CST\":\"string\",\"CFT\":\"string\",\"CET\":\"string\",\"CKA\":\"string\",\"CRT\":\"string\"}},\"D\":{\"V\":\"1\",\"DTP\":\"A\",\"DTP.P\":{\"V\":\"1\",\"BID\":0,\"PID\":0,\"SID\":0,\"DID\":0,\"DSM\":\"string\",\"DSN\":\"string\",\"DSV\":\"string\",\"DAM\":\"string\",\"DAN\":\"string\",\"DAV\":\"string\",\"DAD\":\"string\"},\"DTP.A\":{\"V\":\"1\",\"BID\":0,\"PID\":0,\"SID\":0,\"DID\":0,\"DFS\":\"string\",\"DFM\":\"string\",\"DFN\":\"string\",\"DFV\":\"string\",\"DFD\":\"string\"},\"DTP.M\":{\"V\":\"1\",\"DFF\":\"P\",\"DMI\":\"string\",\"DSS\":\"string\",\"DSM\":\"string\",\"DSN\":\"string\",\"DSV\":\"string\",\"DSD\":\"string\",\"DHR\":\"string\"},\"DTP.B\":{\"V\":\"1\",\"IPA\":\"string\",\"HUA\":\"string\",\"DMI\":\"string\",\"DSS\":\"string\",\"DSM\":\"string\",\"DSN\":\"string\",\"DSV\":\"string\",\"DBS\":\"string\",\"DBM\":\"string\",\"DBN\":\"string\",\"DBV\":\"string\",\"DBR\":\"string\",\"DBD\":\"string\",\"DHR\":\"string\"},\"DMS\":\"string\",\"DMB\":\"string\",\"DMM\":\"string\",\"DMV\":\"string\"},\"W\":{\"V\":\"1\",\"CAS\":\"string\",\"CAM\":\"string\",\"CAN\":\"string\",\"CAV\":\"string\",\"CAD\":\"string\",\"CA\":[{\"V\":\"1\",\"AST\":\"string\",\"ASC\":\"string\",\"ASV\":\"string\",\"ASD\":\"string\"}]},\"LD\":{\"V\":\"1\",\"DCC\":\"string\",\"DLC\":\"string\",\"TMZ\":\"string\",\"LNF\":\"string\",\"LCF\":\"string\",\"LDF\":\"string\"},\"GL\":{\"V\":\"1\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"},\"DTK\":\""+dtk+"\"}";
        }

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_auth/api/wsrAuthRegistration";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            volleyCallback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public static void wsrAuthSessionOpen(String dtk, String rtk, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {

        String data = "{\"V\":\"1\",\"DTK\":\""+dtk+"\",\"RTK\":\""+rtk+"\",\"LD\":{\"V\":\"1\",\"DCC\":\"string\",\"DLC\":\"string\",\"TMZ\":\"string\",\"LNF\":\"string\",\"LCF\":\"string\",\"LDF\":\"string\"},\"GL\":{\"V\":\"1\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"}}";
        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_auth/api/wsrAuthSessionOpen";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            volleyCallback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
