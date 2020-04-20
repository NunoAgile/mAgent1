package com.example.magentdev;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;



import org.json.JSONException;
    import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ShiftOperations {

    public static void wsrShiftOpen(String dtk, String stk, String asp, HashMap<String, String[]> ac, final VolleyCallback volleyCallback, RequestQueue requestQueue) throws JSONException {
        String data ="{\"V\":\"1\",\"DTK\":\""+dtk+"\",\"STK\":\""+stk+"\",\"GL\":{\"V\":\"1\",\"LAT\":0,\"LON\":0,\"ALT\":0,\"SPD\":0,\"BRN\":0,\"FDT\":\"yyyy-MM-ddTHH:mm:ssZ\"},\"ASP\":\""+asp+"\",\"OBS\":\"string\",\"AC\":[";
        Iterator acIterator = ac.entrySet().iterator();
        while(acIterator.hasNext()){
            Map.Entry mapElement = (Map.Entry)acIterator.next();
            data += "{\"V\":\"1\",\"CA\":{\"V\":\"1\",\"CRR\":\""+mapElement.getKey()+"\",\"AMT\":"+ac.get(mapElement.getKey())[0]+"},\"OBS\":\""+ac.get(mapElement.getKey())[1]+"\"},";
        }
        data = data.substring(0,data.length()-1);
        data += "]}";

        JSONObject object = new JSONObject(data);

        String url = "http://mmc.test.trulyplus.com/wsr_cash/api/wsrCashierShiftOpen";

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
