package saloon.com.saloon.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import saloon.com.saloon.entites.SaloonSubResponseEntity;
import saloon.com.saloon.entites.SaloonMainEntity;

public class HomeActivity extends AppCompatActivity {
    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed
    String JsonURL = "http://archthreading.com.au/apiapp/services.php";
    String AdverApiURL = "http://archthreading.com.au/apiapp/advt.php";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue, getRequestQueue2;

    private GridView gridview;

    final List<SaloonMainEntity> mBlackWhiteListDataService = new ArrayList<>();

    ProgressDialog progressDoalog;

    private Utils mUtils;

    private ImageView imageHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        gridview = (GridView) findViewById(R.id.gridview);

        imageHome = (ImageView) findViewById(R.id.imageHome);

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.results);

        mUtils = new Utils();

        homeApiCall();

        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AdvertisementActivity.class);
                startActivity(intent);
            }
        });

    }

    private void homeApiCall() {
        if (mUtils.isInternetConnected()) {

            progressDoalog = new ProgressDialog(HomeActivity.this);
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.setMessage("Loading.........");
            progressDoalog.setIndeterminate(false);
            progressDoalog.show();

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
                                adverApiCall();
                                JSONArray jsonArray = response.optJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SaloonMainEntity mServiceListResponseEntity = new SaloonMainEntity();
                                    JSONObject objectMain = jsonArray.getJSONObject(i);
                                    String objName = objectMain.optString("name");
                                    String objDesc = objectMain.optString("desc");
                                    String objSId = objectMain.optString("sId");
                                    String objOriginalimg = objectMain.optString("originalimg");
                                    String objImg = objectMain.optString("img");
                                    String objThumbImage = objectMain.optString("thumbImage");
                                    String objSmallImage = objectMain.optString("smallImage");

                                    mServiceListResponseEntity.setName(objName);
                                    mServiceListResponseEntity.setDesc(objDesc);
                                    mServiceListResponseEntity.setsId(objSId);
                                    mServiceListResponseEntity.setOriginalimg(objOriginalimg);
                                    mServiceListResponseEntity.setImg(objImg);
                                    mServiceListResponseEntity.setThumbImage(objThumbImage);
                                    mServiceListResponseEntity.setSmallImage(objSmallImage);
                                    mBlackWhiteListDataService.add(mServiceListResponseEntity);
                                }
                                MyBaseAdapter adapter = new MyBaseAdapter(HomeActivity.this, mBlackWhiteListDataService);
                                gridview.setAdapter(adapter);


                            }
                            // Try and catch are included to handle any errors due to JSON
                            catch (Exception e) {
                                // If an error occurs, this prints the error to the log
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
            Toast.makeText(HomeActivity.this, getResources().getString(R.string.msg_internet_not_connected_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void adverApiCall() {
        if (mUtils.isInternetConnected()) {
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
                            // Try and catch are included to handle any errors due to JSON
                            catch (Exception e) {
                                // If an error occurs, this prints the error to the log
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
            Toast.makeText(HomeActivity.this, getResources().getString(R.string.msg_internet_not_connected_message), Toast.LENGTH_SHORT).show();
        }
    }

    public class MyBaseAdapter extends BaseAdapter {

        LayoutInflater inflater;
        Context context;
        List<SaloonMainEntity> rowItems;

        public MyBaseAdapter(Context context, List<SaloonMainEntity> items) {
            this.rowItems = items;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return rowItems.size();
        }

        @Override
        public Object getItem(int position) {
            return rowItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public List<SaloonMainEntity> getDataSet() {
            return rowItems;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_service, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            final SaloonMainEntity serviceListData = (SaloonMainEntity) getItem(position);
            mViewHolder.txtServiceName.setText(serviceListData.getName());

            Picasso.get().load(serviceListData.getThumbImage()).into(mViewHolder.squareImageView);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HomeDetailScreenActivity.class);
                    intent.putExtra("desc", serviceListData.getDesc().toString());
                    intent.putExtra("Name", serviceListData.getName().toString());
                    intent.putExtra("sId", serviceListData.getsId().toString());
                    intent.putExtra("originalimg", serviceListData.getOriginalimg().toString());
                    intent.putExtra("img", serviceListData.getImg().toString());
                    intent.putExtra("thumbImage", serviceListData.getThumbImage().toString());
                    intent.putExtra("smallImage", serviceListData.getSmallImage().toString());
                    startActivity(intent);
                }
            });

            return convertView;
        }

        private class MyViewHolder {
            TextView txtServiceName;
            TextView txtServiceIcon;
            LinearLayout linearSpeedDial;
            SquareImageView squareImageView;

            public MyViewHolder(View item) {
                txtServiceName = (TextView) item.findViewById(R.id.txtServiceName);
                txtServiceIcon = (TextView) item.findViewById(R.id.txtServiceIcon);
                linearSpeedDial = (LinearLayout) item.findViewById(R.id.linearSpeedDial);
                squareImageView = (SquareImageView) item.findViewById(R.id.imgSquareImage);
            }
        }
    }

}
