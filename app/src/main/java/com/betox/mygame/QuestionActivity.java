package com.betox.mygame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.datatype.Duration;

public class QuestionActivity extends AppCompatActivity {

    String praviO, a, b, c, d, hint;
    RadioButton rb1, rb2,rb3,rb4, selected;

    RadioGroup rg;


    int life = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        rg = (RadioGroup)findViewById(R.id.grupa);

        rb1 = (RadioButton)findViewById(R.id.rbOne);
        rb2 = (RadioButton)findViewById(R.id.rbTwo);
        rb3 = (RadioButton)findViewById(R.id.rbThree);
        rb4 = (RadioButton)findViewById(R.id.rbFour);

        rb1.setSelected(true);

        Button b  = (Button)findViewById(R.id.bRestart);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selID = rg.getCheckedRadioButtonId();
                if(selID != -1){
                    selected = (RadioButton) findViewById(selID);


                    if (!praviO.equals(selected.getText())) {
                        Info.life--;
                    }

                    Intent i;
                    if (Info.life > 0) {
                        i = new Intent(QuestionActivity.this, Game.class);
                        i.putExtra("znova", Info.life > 0);
                    } else {
                        i = new Intent(QuestionActivity.this, AfterActivity.class);

                    }


                    startActivity(i);
                }else{
                    Toast.makeText(QuestionActivity.this, "Označite odgovor", Toast.LENGTH_LONG).show();
                }


            }
        });

        AsyncGetQuestionTask task = new AsyncGetQuestionTask("question/random/"+Info.spin);
        task.execute((Void) null);

    }

    public void bpokaziHint_clicked(View view) {

        Toast.makeText(QuestionActivity.this, hint, Toast.LENGTH_LONG).show();
        Info.tocke -=15;


    }

    public class AsyncGetQuestionTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog pdLoading = new ProgressDialog(QuestionActivity.this);

        String result = "";
        JSONArray response;
        String loginURL = "";

        TextView tvQuestion;

        String text, v1,v2,v3,v4,pravi;


        AsyncGetQuestionTask(String URL) {
            loginURL = URL;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tPrenašam...");
            pdLoading.show();

            tvQuestion = (TextView)findViewById(R.id.tvVprasanje);

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
                JSONObject jo = arr.getJSONObject(0);


                text = jo.getString("question");
                v1 = jo.getString("a");
                v2 = jo.getString("b");
                v3 = jo.getString("c");
                v4 = jo.getString("d");
                pravi = jo.getString("answer");
                hint = jo.getString("hint");

                a = v1;
                b = v2;
                c = v3;
                d = v4;

                if(pravi.equals(a)){
                    praviO = a;
                }else if(pravi.equals(b)){
                    praviO = b;
                }else if(pravi.equals(c)){
                    praviO = c;
                }else{
                    praviO = d;
                }




            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();

            tvQuestion.setText(text);
            rb1.setText(v1);
            rb2.setText(v2);
            rb3.setText(v3);
            rb4.setText(v4);

        }
    }

}
