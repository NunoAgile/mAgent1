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

public class WsrAux {

    public static void wsrQryEntityGID(String dtk, String stk, String did, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"EI\":{\"V\":\"1\",\"DID\":\""+did+"\"}}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_aux/api/wsrQryEntityGID";

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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }



}
