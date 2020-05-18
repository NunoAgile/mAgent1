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

public class WsrBtrn_Control {

    public static void wsrBtrnOpen(String dtk, String stk, String btt, String btc, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"BTT\":\""+btt+"\",\"BTC\":\""+btc+"\"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnOpen";

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

    public static void wsrBtrnCompute(String dtk, String stk, int tid, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"TID\":"+tid+"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnCompute";

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

    public static void wsrBtrnValidate(String dtk, String stk, int tid, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"TID\":"+tid+"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnValidate";

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

    public static void wsrBtrnClose(String dtk, String stk, int tid, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"TID\":"+tid+"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnClose";

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

    public static void wsrBtrnAbort(String dtk, String stk, int tid, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\":\"XXX.YYY.Z\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"TID\":"+tid+"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_btrn/api/wsrBtrnAbort";

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
