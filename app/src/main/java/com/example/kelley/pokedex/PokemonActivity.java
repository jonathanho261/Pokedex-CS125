package com.example.kelley.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        TextView id = findViewById(R.id.tv_id);
        TextView name = findViewById(R.id.tv_name);
        TextView type1 = findViewById(R.id.type_1);
        TextView type2 = findViewById(R.id.type_2);
        TextView height = findViewById(R.id.height);
        TextView weight = findViewById(R.id.weight);
        ImageView sprite = findViewById(R.id.imageView);
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        try {
            JSONObject infoJSON = new JSONObject(info);
          
            weight.setText(String.format("%.2f", infoJSON.getInt("weight") * 0.1) + " kg");
            height.setText(String.format("%.2f", infoJSON.getInt("height") * 0.1) + " m");

            id.setText("#" + Integer.toString(infoJSON.getInt("id")));

            String nameString = infoJSON.getString("name");
            nameString = nameString.substring(0, 1).toUpperCase() + nameString.substring(1);
            name.setText(nameString);
            setTitle("Pokemon Info");

            JSONArray types = infoJSON.getJSONArray("types");
            String type = types.getJSONObject(0).getJSONObject("type").getString("name");

            int colorId = this.getResources().getIdentifier(type, "color", this.getPackageName());
            type1.setBackgroundColor(getResources().getColor(colorId));
            type1.setText(type.substring(0, 1).toUpperCase() + type.substring(1));

            if (types.length() >= 2) {
                type2.setVisibility(View.VISIBLE);
                type = types.getJSONObject(1).getJSONObject("type").getString("name");
                colorId = this.getResources().getIdentifier(type, "color", this.getPackageName());
                type2.setBackgroundColor(getResources().getColor(colorId));
                type2.setText(type.substring(0, 1).toUpperCase() + type.substring(1));
            }
          
            String spriteURL = infoJSON.getJSONObject("sprites").getString("front_default");
            sprite.setImageBitmap(new GetImagesTask().execute(new String[]{ spriteURL }).get());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class GetImagesTask extends AsyncTask<String, Void, Bitmap> {
        String image_url;
        URL myFileUrl = null;
        Bitmap bmImg = null;
        protected Bitmap doInBackground(String... args) {
            try {
                myFileUrl = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("error", "there was an error");
            }
            return bmImg;
        }
    }
}
