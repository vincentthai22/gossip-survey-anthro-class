package com.example.vincent.gossipsurvey;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vincent.gossipsurvey.models.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2/26/2017.
 */

public class GossipArrayAdapter<T> extends ArrayAdapter {

    private List<T> list;
    private int resource, headerResource;
    private List<String> answers;

    public GossipArrayAdapter(Context context, int resource, int headerResource, List<String> list){
        super(context,resource, list);
        this.list = (ArrayList) list;
        this.resource = resource;
        this.headerResource = headerResource;
        answers = new ArrayList<>();
    }
    public GossipArrayAdapter(Context context, int resource,  List<T> list){
        super(context,resource, list);
        this.list = (ArrayList) list;
        this.resource = resource;
        answers = new ArrayList<>();
        Log.d("size",answers.size()+"");
        //answers.add("Survey");
        for(int i = 0 ; i < 18; i++){
            answers.add("blank");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, resource);
    }

    @Override
    public T getItem(int position){
        return list.get(position);
    }

    public View createViewFromResource(final int position, View convertView, ViewGroup parent, int resource){
        View view;
        LayoutInflater mInflater = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView text, rightText;
        ImageView img;
        if(convertView == null)
            view = mInflater.inflate(resource,parent,false);
        else
            view = convertView;
        switch(resource) {

            case R.layout.list_item:
                text = (TextView) view.findViewById(R.id.nameLabel);
                Log.d("setText", "" + getItem(position));
                text.setText( ((Survey) getItem(position)).getName());
                return view;
            case R.layout.list_survey_item:

                List<String> questionAndOptions = (ArrayList) getItem(position); //index 0 = question, > 0 = options
                TextView questionTextView = (TextView) view.findViewById(R.id.questionTextView);
                LinearLayout buttonBar = (LinearLayout) view.findViewById(R.id.buttonBarLayout);
                buttonBar.setDividerPadding(10);
                int width = 0;
                buttonBar.removeAllViews();
                int i = 0;
                if(questionAndOptions.size()>1)
                    for (Object questionOrOption : questionAndOptions) {
                        if (i == 0)
                            questionTextView.setText((String) questionOrOption);
                        else {
                            final Button newOptionButton = new Button(this.getContext());
                            newOptionButton.setId(position);
                            newOptionButton.setBackgroundColor(Color.GRAY);
                            newOptionButton.setTextColor(Color.BLACK);
                            newOptionButton.setText((String) questionOrOption);
                            newOptionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if( ((ColorDrawable) newOptionButton.getBackground()).getColor() == Color.GRAY) {
                                        newOptionButton.setBackgroundColor(Color.GREEN);
                                        if (position < answers.size()) {
                                            answers.remove(position);
                                            answers.add(position, (String) newOptionButton.getText());
                                        }
                                    } else {

                                        newOptionButton.setBackgroundColor(Color.GRAY);
                                        if (position < answers.size())
                                        answers.remove(position);
                                    }
                                Log.d("buttonId", newOptionButton.getId()+"");
                                }

                            });
                            buttonBar.addView(newOptionButton);


                        }
                        i++;
                    }
                else
                    questionTextView.setText("Survey");
                return view;

            case R.layout.list_survey_entry_item:
                List<String> questionAndAnswer = (ArrayList<String>) getItem(position);
                questionTextView = (TextView) view.findViewById(R.id.questionTextView);
                TextView answerTextView = (TextView) view.findViewById(R.id.answerTextView);
                questionTextView.setText(questionAndAnswer.get(0));
                answerTextView.setText(questionAndAnswer.get(1));



        }
        return view;
    }

    public List<String> getAnswers(){
        return answers;
    }

}
