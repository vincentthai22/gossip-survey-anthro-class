package com.example.vincent.gossipsurvey;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vincent on 2/26/2017.
 */

public class NewSurveyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);
        initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);

        return true;
    }

    public void initialize(){

        ListView surveyList = (ListView)  findViewById(R.id.newSurveyListView);
        List< List<String> > questionsAndOptionsList = new ArrayList<>();
        buildList(questionsAndOptionsList);
        LinearLayout buttonBar = (LinearLayout) findViewById(R.id.buttonBarLayout);
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        surveyList.setAdapter(new GossipArrayAdapter<List<String>>(this.getApplicationContext(), R.layout.list_survey_item, questionsAndOptionsList));

    }

    public void buildList(List< List<String> > questionsAndOptionList){
        int index;
        AssetManager assetManager = getAssets();
        try{
            InputStream questionsAndOptionListStream = assetManager.open("surveyquestions.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(questionsAndOptionListStream));
            String line, entireText ="";
            while((line = in.readLine()) != null){
               entireText +=line;
            }
            Log.d("entiretext", entireText);

            for(String questionsUnsplit : entireText.split("\\*") ) {
                List temp = new ArrayList();
                int counter = 0;

                for(String questions : questionsUnsplit.split("=") ) {
                    if(counter==0) {
                        Log.d("questions", questions);
                        temp.add(questions.trim());
                    }
                    else {
                        for (String options : questions.split("/") ) {
                            Log.d("options", options);
                            temp.add(options.trim());

                        }
                    }
                    counter++;
                }
                questionsAndOptionList.add(temp);
            }

            for(List l : questionsAndOptionList)
                for(Object s : l)
                    Log.d("after all parsing", (String)s);

        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }



}
