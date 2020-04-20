package com.example.magentdev;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {
    private static RequestQueueSingleton requestQueueSingleton;
    private RequestQueue requestQueue;


    private RequestQueueSingleton(Context ctx){
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    public static synchronized  RequestQueueSingleton getInstance(Context context){
        if (requestQueueSingleton == null){
            requestQueueSingleton = new RequestQueueSingleton(context);
        }
        return requestQueueSingleton;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
