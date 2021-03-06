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

public class WsrCash_Information {

    public static void wsrCashierShiftList(String dtk, String stk, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"ACO.I.CSL.I\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GL\":{\"V\":\"1\",\"R\":\"string\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"}}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_cash/api/wsrCashierShiftList";

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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public static void wsrCashierCashBalance(String dtk, String stk, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"ACO.I.CCB.I\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GL\":{\"V\":\"1\",\"R\":\"string\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"}}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_cash/api/wsrCashierCashBalance";

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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}
