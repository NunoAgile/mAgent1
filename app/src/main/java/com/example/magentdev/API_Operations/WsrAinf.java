package com.example.magentdev.API_Operations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.magentdev.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class WsrAinf {

    public static void wsrIAccountList(String dtk, String stk, String gid, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data = "{\"V\":\"1\",\"R\": \"AIO.A.IAL.I\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GID\":\""+gid+"\"}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_ainf/api/wsrIAccountList";

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
