package smartcompany.smarthomie;


import android.app.DownloadManager;
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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class API {
    static private RequestQueue rQueue = null;
    static private boolean alreadyInit = false;
    static private Gson gson;
    static private Context currentContext;
    static private String baseUrl;
    static private HashMap<String,Device> devicesMap;
    static private Queue<Fridge> fridgesToGetState;
    static private Queue<Oven> ovensToGetState;
    static private Queue<Light> lampsToGetState;
    static private Queue<Door> doorsToGetState;
    static private Queue<Curtain> blidsToGetState;

    public static Context getContext(){
        return currentContext;
    }

    public static void initAPIConnection(Context context){
        if(!alreadyInit) {
            ChangeContext(context);
            alreadyInit = true;
            gson = new Gson();
            devicesMap = new HashMap<>();
            ovensToGetState = new LinkedList<>();
            lampsToGetState = new LinkedList<>();
            doorsToGetState = new LinkedList<>();
            blidsToGetState = new LinkedList<>();
            fridgesToGetState = new LinkedList<>();
        }
    }

    public static void ChangeContext(Context context) {
        currentContext = context;
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

            Toast.makeText(currentContext,"",Toast.LENGTH_LONG).show();

            JSONArray arrayParameters = new JSONArray();
            for (Object o: parameters) {
                arrayParameters.put(o);
            }

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.PUT, requestUrl, arrayParameters, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_11),Toast.LENGTH_LONG).show();
                }
            });
            rQueue.add(request);
            rQueue.start();
        }
    }

    public static void AddNewDevice(Device d) {
        String requestUrl = baseUrl + "/devices/";
        JSONObject requestJson = new JSONObject();
        try {
            requestJson.put("typeId" , d.getTypeId());
            requestJson.put("name",d.getName());
            if (d.getMeta() != null) requestJson.put("meta",d.getMeta());
        }catch (Exception e) {
            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_12),Toast.LENGTH_LONG).show();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, requestUrl, requestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_success_2),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_11),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonRequest);
        rQueue.start();
    }

    public static void RemoveDevice(Device d) {
        String requestUrl = baseUrl + "/devices/" + d.getId();
        devicesMap.remove(d.getName());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.DELETE, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getBoolean("result")){
                                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_success_1),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_10),Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_9),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_8),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonRequest);
        rQueue.start();
    }

    public static void RemoveDevice(String id) {
        String requestUrl = baseUrl + "/devices/" + id;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.DELETE, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getBoolean("result")){
                                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_success_1),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_7),Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_8),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_9),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonRequest);
        rQueue.start();
    }

    public static void OvenUpdateState(Oven o) {
        ovensToGetState.offer(o);
        String requestUrl = baseUrl + "/devices/" + o.getId() + "/getState";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Oven o = ovensToGetState.poll();
                        try {
                            JSONObject aux = response.getJSONObject("result");
                            o.setConvection(aux.getString("convection"));
                            o.setGrill(aux.getString("grill"));
                            o.setHeat(aux.getString("heat"));
                            o.setState(aux.getString("status"));
                            o.setTemperature(aux.getInt("temperature"));
                        }catch (Exception e) {
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_2),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_6),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonObjectRequest);
        rQueue.start();
    }

    public static void FridgeUpdateState(Fridge fridge) {
        fridgesToGetState.offer(fridge);
        String requestUrl = baseUrl + "/devices/" + fridge.getId() + "/getState";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Fridge f = fridgesToGetState.poll();
                        try {
                            JSONObject aux = response.getJSONObject("result");
                            f.setFreezerTemperature(aux.getInt("freezerTemperature"));
                            f.setMode(aux.getString("mode"));
                            f.setRefridgeratorTemperature(aux.getInt("temperature"));
                        }catch (Exception e) {
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_2),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_5),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonObjectRequest);
        rQueue.start();
    }

    public static void CurtainUpdateState(Curtain c) {
        blidsToGetState.offer(c);
        String requestUrl = baseUrl + "/devices/" + c.getId() + "/getState";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Curtain c = blidsToGetState.poll();
                        try {
                            JSONObject aux = response.getJSONObject("result");
                            if(aux.getString("status").equals("opened")){
                                c.setRaised(true);
                            }else {
                                c.setRaised(false);
                            }
                        }catch (Exception e) {
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_2),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_4),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonObjectRequest);
        rQueue.start();
    }

    public static void LightUpdateState(Light l) {
        lampsToGetState.offer(l);
        String requestUrl = baseUrl + "/devices/" + l.getId() + "/getState";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Light l = lampsToGetState.poll();
                        try {
                            JSONObject aux = response.getJSONObject("result");
                            l.setBrightness(aux.getInt("brightness"));
                            l.setColor(aux.getString("color"));
                            l.setState(aux.getString("status"));

                        }catch (Exception e) {
                            Log.d("JSONLight",e.getMessage());
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_2),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_3),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonObjectRequest);
        rQueue.start();
    }

    public static void DoorUpdateState(Door d) {
        doorsToGetState.offer(d);
        String requestUrl = baseUrl + "/devices/" + d.getId() + "/getState";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Door d = doorsToGetState.poll();
                        try {
                            JSONObject aux = response.getJSONObject("result");
                            d.setClosed(aux.getString("status").equals("close"));
                            d.setLocked(!aux.getString("lock").equals("unlocked"));
                        }catch (Exception e) {
                            Log.d("JSONDoor",e.getMessage());
                            Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_2),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(currentContext,currentContext.getResources().getText(R.string.toast_fail_1),Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(jsonObjectRequest);
        rQueue.start();
    }

}
