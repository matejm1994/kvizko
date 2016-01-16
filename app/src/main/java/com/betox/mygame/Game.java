package com.betox.mygame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Activity {

    Bundle si;
    private Spinner spinner;
    List<String> list;
    List<String> listID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //only in landscape view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        list = new ArrayList<>();
        listID = new ArrayList<>();


        Intent i = getIntent();
        boolean znova = false;
        if (i != null) {
            znova = i.getBooleanExtra("znova", false);
        }

        if (znova) {
            setContentView(new GamePanel(Game.this));

        } else {
            //setContentView(new GamePanel(this));
            setContentView(R.layout.activity_game);
            // setContentView(new GamePanel(Game.this));


            Button bNewGame = (Button) findViewById(R.id.bStartGame);

            bNewGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Info.aJe) {
                        Info.spin = spinner.getSelectedItemId() + 1;
                        Info.aJe = true;
                    }
                    setContentView(new GamePanel(Game.this));
                }
            });

            napolniSpinner();

        }


    }

    public void napolniSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);

        AsyncGetQuizTask task = new AsyncGetQuizTask("quizzes/all");
        task.execute((Void) null);


    }

    public class AsyncGetQuizTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog pdLoading = new ProgressDialog(Game.this);

        String result = "";
        String loginURL = "";

        AsyncGetQuizTask(String URL) {
            loginURL = URL;
        }

        List<String> list1 = new ArrayList<String>();
        List<String> listID1 = new ArrayList<String>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tPrenašam...");
            pdLoading.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            // String loginURL = "Messages";

            try {
                result = Connection.getConnection(loginURL);
                //response = new JSONArray(result);
                //JSONObject jo = response.getJSONObject(0);
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("questions");


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jso = arr.getJSONObject(i);
                    String name = jso.getString("name");
                    String id = jso.getString("id");
                    list1.add(name);
                    listID1.add(id);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            list.addAll(list1);
            listID.addAll(listID1);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Game.this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);


            pdLoading.dismiss();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
