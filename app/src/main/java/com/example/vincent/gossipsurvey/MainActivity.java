package com.example.vincent.gossipsurvey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vincent.gossipsurvey.models.Survey;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String SAVED_INSTANCE_SURVEY_LIST = "savedinsntancesurvey";

    private List<Survey> surveyList = new ArrayList<>();
    private GossipArrayAdapter<Survey> adapter;
    private MainActivity selfRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selfRef = this;
        try {
            surveyList = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_SURVEY_LIST);
        } catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addButtonHandler();
            }
        });

        initialize();

    }

    public void initialize(){
        try {
            FileInputStream fis = openFileInput("surveys");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String temp;
            while((temp = br.readLine()) != null){
                sb.append(temp);
                Log.d("input reader", temp);
            }
        } catch (FileNotFoundException e) {
            Log.d("filenotfound", "not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("filenotfound2", "not found");
            e.printStackTrace();
        }


        final ListView surveyListView= (ListView) findViewById(R.id.surveyListView);
        adapter = new GossipArrayAdapter<Survey>(this, R.layout.list_item, surveyList);
        surveyListView.setAdapter(adapter);
        surveyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("onItemClcik", "youve clicked position: "+position );
                Intent intent = new Intent(selfRef, ViewSurveyActivity.class);
                intent.putExtra( NewSurveyActivity.SURVEY_DATA_STRING , surveyList.get(position));
                startActivity(intent);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Survey s = data.getParcelableExtra(NewSurveyActivity.SURVEY_DATA_STRING);
        if(!s.getName().equals("null")) {
            surveyList.add(s);
            adapter.notifyDataSetChanged();
        }

        try {
            FileOutputStream fos = openFileOutput("surveys.txt", Context.MODE_PRIVATE);
            for(int i = 0 ; i < surveyList.size(); i++){
                Survey survey = surveyList.get(i);
                List<String> answers = survey.getAnswers();

                for(int j = 0 ; j < answers.size(); j++){
                    fos.write( survey.getName().getBytes() );
                    fos.write(answers.get(i).getBytes());
                    fos.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        return true;
    }

    public void testMethod(){
        Log.d("testing","123123");
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

    public void onSaveInstanceState(Bundle savedInstanceState){
        try{
            if(savedInstanceState != null){
                savedInstanceState.putParcelableArrayList(SAVED_INSTANCE_SURVEY_LIST, (ArrayList)surveyList);
            } else {

            }
        } catch(NullPointerException e){

        }

       // super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        surveyList = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_SURVEY_LIST);
        adapter.notifyDataSetChanged();
    }

    public void addButtonHandler(){
        Log.d("addButon", "we made it into add button");
        Intent intent = new Intent(this, NewSurveyActivity.class);
        int requestCode = 2;
        startActivityForResult(intent, requestCode);

    }

}
