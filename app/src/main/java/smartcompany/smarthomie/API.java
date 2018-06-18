package smartcompany.smarthomie;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class API {
    static private RequestQueue rQueue = null;
    static private boolean alreadyInit = false;
    static private Gson gson;
    static private Context curretContext;
    static private String baseUrl;
    static private HashMap<String,Device> devicesMap;


    public static void initAPIConnection(Context context){
        if(!alreadyInit) {
            ChangeContext(context);
            alreadyInit = true;
            gson = new Gson();
            devicesMap = new HashMap<>();
        }
    }

    public static void ChangeContext(Context context) {
        curretContext = context;
        baseUrl = context.getString(R.string.BaseUrl);
        rQueue = Volley.newRequestQueue(context);
    }

    public static void SendAndRequestAllDevices() {
        if(rQueue != null) {
            String requestUrl = baseUrl + "/devices";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                Type listType = new TypeToken<List<Device>>(){}.getType();
                                List<Device> devices = gson.fromJson(response.getJSONArray("devices").toString(),listType);
                                for(Device d: devices) {
                                    Device castedDevice = Device.DeviceFactory(d);
                                    if (castedDevice != null)
                                        devicesMap.put(castedDevice.getName(), castedDevice);
                                }
                            }catch (Exception e){
                                Log.d(e.toString(),e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                }});

            rQueue.add(jsonObjectRequest);
            rQueue.start();
        }
    }

    public static HashMap<String, Device> getDevicesMap() {
        return devicesMap;
    }

    public static void SendEvent(Device d, String eventName) {
        if(rQueue != null) {
            String requestUrl = baseUrl + "/devices/" + d.getId() + "/" + eventName;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                Log.d("event",response.toString());
                            }catch (Exception e){
                                Log.d(e.toString(),e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }});

            rQueue.add(jsonObjectRequest);
            rQueue.start();
        }
    }

    public static void SendEventWithParameters(Device d, String eventName, List<Object> parameters) {
        if(rQueue!=null) {
            String requestUrl = baseUrl + "/devices/" + d.getId() + "/" + eventName;

            Toast.makeText(curretContext,"",Toast.LENGTH_LONG).show();

            JSONArray arrayParameters = new JSONArray();
            arrayParameters.put(parameters);

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.PUT, requestUrl, arrayParameters, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            rQueue.add(request);
            rQueue.start();
        }
    }
}
