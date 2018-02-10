package com.shrikanthravi.slidinguppanel;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Shrikanth Ravi on 30-07-2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Musync1.0";
    public static final String TABLE_NAME = "FAVOURITES";
    public static final String TABLE_NAME1 = "PLAYLISTS";
    public static final String TABLE_NAME2 = "PLAYS";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "SONG_ID";
    public static final String COL_2 = "PLAYSC";
    String f;
    String[] columns ={COL_0,COL_1};
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT,SONG_ID TEXT)");
        db.execSQL("create table " + TABLE_NAME1 + " (PID INTEGER  PRIMARY KEY ,PNAME TEXT,PTRACKS TEXT,PDUR TEXT)");
        db.execSQL("create table " + TABLE_NAME2 + "(PID INTEGER PRIMARY KEY AUTOINCREMENT,SONG_ID TEXT,PLAYSC INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);


    }

    public void createPlaylist(String playlistname){

        SQLiteDatabase dbinstance = this.getWritableDatabase();
        dbinstance.execSQL("create table " + playlistname + " (ID INTEGER  PRIMARY KEY AUTOINCREMENT,SONG_ID TEXT)");
        ContentValues contentValues = new ContentValues();
        contentValues.put("PNAME", playlistname);
        dbinstance.insert(TABLE_NAME1, null, contentValues);
    }

    public void updatePlaylist(String playlistname,String song_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, song_id);
        db.insert(playlistname, null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(getPlaylistSongs(playlistname));
        contentValues1.put("PTRACKS", temp.size());
        contentValues1.put("PDUR", "");

        db.update(TABLE_NAME1, contentValues1, "PNAME = ? ", new String[]{playlistname});

    }

    public ArrayList<Playlist> getPlayList(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME1, null);

        res.moveToFirst();
        while (res.isAfterLast() == false) {

            playlists.add(new Playlist(res.getString(res.getColumnIndex("PNAME")),res.getString(res.getColumnIndex("PTRACKS")),res.getString(res.getColumnIndex("PDUR"))));
            res.moveToNext();

        }
        return playlists;
    }

    public ArrayList<String> getPlaylistSongs(String tablename){
        ArrayList<String> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + tablename, null);

        res.moveToFirst();
        while (res.isAfterLast() == false) {

            System.out.println(res.getString(res.getColumnIndex(COL_1)));
            songs.add(res.getString(res.getColumnIndex(COL_1)));
            res.moveToNext();

        }
        return songs;
    }

    public void insert_play(String song_id){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Plays> arrayList = new ArrayList();
        arrayList.addAll(getPlay());
        int count=-1;
        if(arrayList.size()!=0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getSongId().equals(song_id)) {
                    count = arrayList.get(i).getPlaycount();
                    break;
                }
            }
        }
        if(count<0){

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,song_id);
            contentValues.put("PLAYSC",1);
            db.insert(TABLE_NAME2,null,contentValues);
            System.out.println("plays inserted");

        }
        else{

            ContentValues contentValues = new ContentValues();
            contentValues.put("PLAYSC",count+1);
            db.update(TABLE_NAME2, contentValues, "SONG_ID = ? ", new String[]{song_id});
            System.out.println("plays updated");

        }


    }

    public List<Plays> getPlay(){

        ArrayList<Plays> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME2, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            array_list.add(new Plays(res.getString(res.getColumnIndex(COL_1)),res.getInt(res.getColumnIndex(COL_2))));
            res.moveToNext();
        }


        return array_list;
    }
/*
    public String getType(int id) {
        String h = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Vehicle_Info where ID=" + id + "", null);
        res.moveToFirst();
        h = res.getString(res.getColumnIndex(COL_2));

        return h;
    }
*/



    public boolean insert_all(String song_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, song_id);
        db.insert(TABLE_NAME, null, contentValues);
        System.out.println("inserted");
        return true;
    }


    public ArrayList<String> getFavList() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        res.moveToFirst();
        while (res.isAfterLast() == false) {

            System.out.println(res.getString(res.getColumnIndex(COL_1)));
            array_list.add(res.getString(res.getColumnIndex(COL_1)));
            res.moveToNext();

        }
        return array_list;
    }

    /*

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }


    public boolean update(int veh_id, String fuel, String reg, String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_6, fuel);
        contentValues.put(COL_7, reg);
        contentValues.put(COL_9, year);

        db.update(TABLE_NAME, contentValues, "VEHICLE_ID = ? ", new String[]{Integer.toString(veh_id)});
        return true;
    }*/

    public Integer delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "SONG_ID = ? ",
                new String[]{String.valueOf(id)});
    }
/*

    public ArrayList<GarageGetterSetter> getAllVehicles() {
        ArrayList<GarageGetterSetter> arr_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            arr_list.add(new GarageGetterSetter(res.getInt(res.getColumnIndex(COL_0)),res.getInt(res.getColumnIndex(COL_1)),res.getInt(res.getColumnIndex(COL_2)),res.getString(res.getColumnIndex(COL_3)),res.getString(res.getColumnIndex(COL_4)),res.getString(res.getColumnIndex(COL_5)),res.getString(res.getColumnIndex(COL_6)),res.getString(res.getColumnIndex(COL_7)),res.getString(res.getColumnIndex(COL_8)),res.getString(res.getColumnIndex(COL_9)),res.getString(res.getColumnIndex(COL_10))));
            res.moveToNext();


        }
        return arr_list;
    }

    public ArrayList<GarageGetterSetter> getAllByType(String typ) {
        ArrayList<GarageGetterSetter> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME, columns, "type=?", new String[] { typ }, null, null, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            array_list.add(new GarageGetterSetter(res.getInt(res.getColumnIndex(COL_0)),res.getInt(res.getColumnIndex(COL_1)),res.getInt(res.getColumnIndex(COL_2)),res.getString(res.getColumnIndex(COL_3)),res.getString(res.getColumnIndex(COL_4)),res.getString(res.getColumnIndex(COL_5)),res.getString(res.getColumnIndex(COL_6)),res.getString(res.getColumnIndex(COL_7)),res.getString(res.getColumnIndex(COL_8)),res.getString(res.getColumnIndex(COL_9)),res.getString(res.getColumnIndex(COL_10))));
            res.moveToNext();

        }
        return array_list;
    }
*/


}

