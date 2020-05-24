package saloon.com.saloon.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saloon.com.saloon.R;
import saloon.com.saloon.common.Utils;
import saloon.com.saloon.control.SquareImageView;
import saloon.com.saloon.entites.SaloonMainEntity;

public class AdvertisementActivity extends AppCompatActivity {
    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed
    String AdverApiURL = "http://archthreading.com.au/apiapp/advt.php";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    ProgressDialog progressDoalog;

    private Utils mUtils;

    private ImageView imageHome, imgCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement);


        imageHome = (ImageView) findViewById(R.id.imageHome);
        imgCancle = (ImageView) findViewById(R.id.imgCancle);

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.results);

        mUtils = new Utils();

        adverApiCall();

        imgCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdvertisementActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void adverApiCall() {
        if (mUtils.isInternetConnected()) {

            progressDoalog = new ProgressDialog(AdvertisementActivity.this);
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.setMessage("Loading.........");
            progressDoalog.setIndeterminate(false);
            progressDoalog.show();
            // Creating the JsonObjectRequest class called obreq, passing required parameters:
            //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, AdverApiURL,
                    // The third parameter Listener overrides the method onResponse() and passes
                    //JSONObject as a parameter
                    new Response.Listener<JSONObject>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressDoalog.dismiss();
                                JSONObject jsonObject = response.optJSONObject("data");
                                String strAdverUrl = jsonObject.optString("originalimg");
                                Picasso.get().load(strAdverUrl).into(imageHome);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                progressDoalog.dismiss();
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
                            progressDoalog.dismiss();
                        }
                    }
            );
            // Adds the JSON object request "obreq" to the request queue
            requestQueue.add(obreq);

        } else {
            Toast.makeText(AdvertisementActivity.this, getResources().getString(R.string.msg_internet_not_connected_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AdvertisementActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
