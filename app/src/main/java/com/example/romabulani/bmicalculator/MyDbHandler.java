package com.example.romabulani.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHandler extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;

    MyDbHandler(Context context)
    {
        super(context,"bmidb",null,1);
        this.context=context;
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table bmitable(date text,"+" bmi text,"+"weight float)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public  void addBmi(String date, String bmi,float weight){
        ContentValues cv=new ContentValues();
        date.toString();

        cv.put("date",date);
        cv.put("bmi",bmi);
        cv.put("weight",weight);

        db.insert("bmitable",null,cv);
    }

    public  String viewBmi(){
        Cursor cursor=db.query("bmitable",null,null,null,null,null,null );
        StringBuffer sb=new StringBuffer();
        cursor.moveToFirst();
        if(cursor.getCount()>0)
            do
            {
                sb.append(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getFloat(2)+
                        "\n--------------------------------------------------------------\n");

            }while (cursor.moveToNext());
        return sb.toString();

    }


}
