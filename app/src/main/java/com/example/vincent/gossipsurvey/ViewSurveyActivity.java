package com.example.vincent.gossipsurvey;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.vincent.gossipsurvey.models.Survey;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2/26/2017.
 */

public class ViewSurveyActivity extends AppCompatActivity {

    Survey survey;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);
        initialize();
    }

    public void initialize(){
        final ListView surveyData = (ListView) findViewById(R.id.newSurveyListView);
        List<List<String>> questionAndAnswerList = new ArrayList<>();
        survey = getIntent().getParcelableExtra(NewSurveyActivity.SURVEY_DATA_STRING);
        buildList(questionAndAnswerList);
        surveyData.setAdapter(new GossipArrayAdapter<List<String>>(this.getApplicationContext(), R.layout.list_survey_entry_item, questionAndAnswerList));
    }

    public void buildList(List<List<String>> questionAndAnswerList){
        AssetManager assetManager = getAssets();

        try{
            InputStream questionsAndOptionListStream = assetManager.open("surveyquestions.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(questionsAndOptionListStream));
            String line, entireText ="";
            while((line = in.readLine()) != null){
                entireText +=line;
            }
            int surveyCounter = 0;
            for(String questionsUnsplit : entireText.split("\\*") ) {
                List temp = new ArrayList();
                int counter = 0;

                for(String questions : questionsUnsplit.split("=") ) {
                    if(counter==0) {
                        Log.d("questions", questions);
                        temp.add(questions.trim());
                        temp.add(survey.getAnswers().get(surveyCounter));
                    }
                    counter++;
                }
                questionAndAnswerList.add(temp);
                surveyCounter++;
            }
        } catch (IOException e){

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
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
        item = menu.findItem(R.id.action_save);
        item.setVisible(false);

        return true;
    }
}
