package saloon.com.saloon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saloon.com.saloon.R;
import saloon.com.saloon.entites.SaloonMainEntity;


/**
 * Created by mukesh.thakur on 12-07-2016.
 */
public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private int SPLASH_TIME_OUT = 500;
    private Handler mHandler;

    String JsonURL = "http://archthreading.com.au/apiapp/services.php";
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    final List<SaloonMainEntity> mBlackWhiteListDataService = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


//        apiCall();


        proceedHome();




    }//End of OnCreate

    private void apiCall() {
        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mBlackWhiteListDataService.clear();
                            JSONArray jsonArray = response.optJSONArray("data");
                            // Retrieves the string labeled "colorName" and "description" from
                            // the response JSON Object
                            //and converts them into javascript objects

                            for (int i = 0; i < jsonArray.length(); i++) {
                                SaloonMainEntity mServiceListResponseEntity = new SaloonMainEntity();
                                JSONObject objectMain = jsonArray.getJSONObject(i);
                                String  objName = objectMain.optString("name");
                                String  objDesc = objectMain.optString("desc");
                                String  objSId = objectMain.optString("sId");
                                String  objOriginalimg = objectMain.optString("originalimg");
                                String  objImg = objectMain.optString("img");
                                String  objThumbImage = objectMain.optString("thumbImage");
                                String  objSmallImage = objectMain.optString("smallImage");

                                mServiceListResponseEntity.setName(objName);
                                mServiceListResponseEntity.setDesc(objDesc);
                                mServiceListResponseEntity.setsId(objSId);
                                mServiceListResponseEntity.setOriginalimg(objOriginalimg);
                                mServiceListResponseEntity.setImg(objImg);
                                mServiceListResponseEntity.setThumbImage(objThumbImage);
                                mServiceListResponseEntity.setSmallImage(objSmallImage);

                                mBlackWhiteListDataService.add(mServiceListResponseEntity);
                            }
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

    private void proceedHome() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                    Intent i = new Intent(SplashActivity.this, AdvertisementActivity.class);
                    startActivity(i);
                    finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
