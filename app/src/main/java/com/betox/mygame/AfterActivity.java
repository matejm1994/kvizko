package com.betox.mygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AfterActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "kvizkoPREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        TextView tv = (TextView)findViewById(R.id.tvTocke);
        TextView tvTop = (TextView)findViewById(R.id.tvTopVedno);
        TextView tvBolje = (TextView)findViewById(R.id.tvBoljeKotPrej);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int silent = settings.getInt("top", Info.tocke);



        tv.setText("Dosegli ste "+(int)(Info.tocke/10)+" tock");
        tvTop.setText("Najboljši rezultat do zdaj je: "+(int)(silent/10));
        tvBolje.setText(Info.tocke>Info.prej?"Boljši ste kot prej":"Slabši ste kot prej");


        Info.prej = Info.tocke;

        if(Info.tocke>silent){
            silent = Info.tocke;
        }


        SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("top", silent);

        // Commit the edits!
        editor.commit();


    }

    public void bIzhod_Clicked(View view) {
        System.exit(0);
    }

    public void bNovaIgra_Clicked(View view) {
        Info.tocke = 0;
        Info.life = 3;
        Intent i = new Intent(AfterActivity.this, Game.class);
        i.putExtra("znova", true);
    }
}
