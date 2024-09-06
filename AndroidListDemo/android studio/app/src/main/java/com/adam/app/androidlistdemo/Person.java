package com.adam.app.androidlistdemo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Person implements Parcelable {

    private static final String TAG = "Parcelable";
    public static final Creator<Person> CREATOR = new Creator<Person>() {

        @Override
        public Person createFromParcel(Parcel in) {
            // TODO Auto-generated method stub
            Log.i(TAG, "createFromParcel is called");
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            // TODO Auto-generated method stub
            Log.i(TAG, "newArray is called");
            return new Person[size];
        }

    };
    public String name = null;
    public String age = null;


    public Person() {
        Log.i(TAG, "Person constructor is called");
    }


    //	public Person(String name, String age) {
//		super();
//		this.name = name;
//		this.age = age;
//	}
//
    Person(Parcel in) {
        Log.i(TAG, "Person(Parcel in) constructor is called");
        this.name = in.readString();
        this.age = in.readString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub
        Log.i(TAG, "writeToParcel is called");
        arg0.writeString(this.name);
        arg0.writeString(this.age);

    }


}
