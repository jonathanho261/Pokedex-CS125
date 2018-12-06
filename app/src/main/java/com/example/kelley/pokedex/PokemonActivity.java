package com.example.kelley.pokedex;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PokemonActivity extends AppCompatActivity {

    // TODO: add layout
    // TODO: Change title of page to name of pokemon?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        TextView weight = findViewById(R.id.weight);
        TextView id = findViewById(R.id.tv_id);
        TextView name = findViewById(R.id.tv_name);
        TextView type1 = findViewById(R.id.type_1);
        TextView type2 = findViewById(R.id.type_2);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        try {
            JSONObject infoJSON = new JSONObject(info);
            id.setText(Integer.toString(infoJSON.getInt("id")));
            name.setText(infoJSON.getString("name"));
            JSONArray types = infoJSON.getJSONArray("types");
            String type = types.getJSONObject(0).getJSONObject("type").getString("name");
            type1.setBackgroundColor(Color.parseColor(type));
            // TODO: get color string correspond with the colors in xml

            weight.setText(Double.toString(infoJSON.getInt("weight") * 0.1) + " kg");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        setTitle("Butterfree");
    }
}
