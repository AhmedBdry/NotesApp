package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyDataBase";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="notes";
    private static final String KEY_ID="id";
    private static final String KEY_DATE="date";
    private static final String KEY_NOTE="note";

    public MyHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+KEY_ID+" integer primary key ,"+KEY_DATE+" varchar(30),"+KEY_NOTE+" integer);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DELETE_TABLE="DROP TABLE "+ TABLE_NAME+"IF EXISTS";
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    public void addNotes (Note note){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(KEY_DATE,note.getDate());
        values.put(KEY_NOTE,note.getNote());
        db.insert(TABLE_NAME,null,values);
    }

    public ArrayList<Note> getAllNotes (){
        ArrayList<Note>notes = new ArrayList<>();
        String select_query="SELECT * FROM "+TABLE_NAME+"";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =db.rawQuery(select_query,null);
        if (cursor.moveToFirst()){
            do {
                String date=cursor.getString(cursor.getColumnIndex(KEY_DATE));
                String notee=cursor.getString(cursor.getColumnIndex(KEY_NOTE));
                int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
                Note note=new Note(notee,id);
                notes.add(note);
            }while (cursor.moveToNext());
        }
            return notes;
    }
    public Note getNoteId(int id){
        String select_query="SELECT * FROM "+TABLE_NAME+" WHERE id="+id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(select_query,null);
        Note note=null;
        if(cursor.moveToFirst()) {
            int  note_id =cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String noti=cursor.getString(cursor.getColumnIndex(KEY_NOTE));
            note=new Note(noti,note_id);
        }
        return note;
    }
    public void updateNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NOTE,note.getNote());
        db.update(TABLE_NAME,values,"id=?",new String[]{String.valueOf(note.getMid())});
    }
    public void deleteContact(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
    }
}
