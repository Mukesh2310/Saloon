package saloon.com.saloon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import saloon.com.saloon.R;

public class HomeDetailScreenActivity extends AppCompatActivity {

    private ImageView imgMain;
    private TextView txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detail_screen);


        Intent intent = getIntent();
        String desc = intent.getStringExtra("desc");
        String Name = intent.getStringExtra("Name");
        String sId = intent.getStringExtra("sId");
        String originalimg = intent.getStringExtra("originalimg");
        String img = intent.getStringExtra("img");
        String thumbImage = intent.getStringExtra("thumbImage");
        String smallImage = intent.getStringExtra("smallImage");


        imgMain=(ImageView)findViewById(R.id.imgMain);
        txtDesc=(TextView)findViewById(R.id.txtDesc);
        txtDesc.setText(Html.fromHtml(desc));

        Picasso.get().load(originalimg).into(imgMain);

    }


}
