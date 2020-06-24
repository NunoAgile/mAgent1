package com.example.magentdev.API_Operations;

import android.location.Location;
import android.location.LocationManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.magentdev.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class WsrCash_Client {

    public static void wsrCashLink(String dtk, String stk, int sid, int tid, String otp, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"ACO.C.CL.I\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GL\":{\"V\":\"1\",\"R\":\"string\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"},\"SID\":"+sid+",\"TID\":"+tid+",\"OTP\":\""+otp+"\"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_cash/api/wsrCashLink";

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

    public static void wsrCashWithdraw(String dtk, String stk, int sid, int tid, String amt,double[] coordinates, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"2\",\"R\":\"ACO.C.CW.I\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GL\":{\"V\":\"1\",\"R\":\"string\",\"LAT\":"+coordinates[0]+",\"LON\":"+coordinates[1]+",\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"},\"SID\":"+sid+",\"TID\":"+tid+",\"AMT\":"+amt+"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_cash/api/wsrCashWithdraw";


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

    public static void wsrCashDeposit(String dtk, String stk, int sid, int tid, String amt,double[] coordinates, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"2\",\"R\":\"ACO.C.CD.I\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GL\":{\"V\":\"1\",\"R\":\"string\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"},\"SID\":"+sid+",\"TID\":"+tid+",\"AMT\":"+amt+"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_cash/api/wsrCashDeposit";

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
