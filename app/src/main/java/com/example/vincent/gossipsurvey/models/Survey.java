package com.example.vincent.gossipsurvey.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2/26/2017.
 */

public class Survey implements Parcelable{

    private String name;
    private List<String> answers;


    public Survey(){
        name = "";

    }
    public Survey(String name){
        this.name = name;
        answers = new ArrayList<>();
    }

    protected Survey(Parcel in) {
        name = in.readString();
        answers = in.createStringArrayList();
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public void setAnswers(List<String> answers){
        this.answers = answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeStringList(answers);
    }

    public void readFromParcel(Parcel in){
        int count = in.readInt();
        for(int i = 0 ; i < count ; i++){
            if (i == 0)
                name = in.readString();
            else
                answers = in.createStringArrayList();
        }
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public List<String> getAnswers(){
        return answers;
    }

}
