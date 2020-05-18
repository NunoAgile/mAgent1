package com.example.magentdev.API_Operations;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.magentdev.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class WsrBtrn_Authorisation {

    public static void wsrBtrnAuthRequest(String dtk, String stk, int tid, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"TID\":"+tid+",\"AMT\":\"X\"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnAuthRequest";

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
                try {
                    volleyCallback.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public static void wsrBtrnAuthReply(String dtk, String stk, int tid, String acd, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"TID\":"+tid+",\"ACD\":\""+acd+"\"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnAuthReply";

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
                try {
                    volleyCallback.onError(error.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}
