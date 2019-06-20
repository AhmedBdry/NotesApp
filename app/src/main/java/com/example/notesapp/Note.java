package com.example.notesapp;

public class Note {

    private String date ;
    private String note;
    private int mid ;

    public Note(String note) {
        this.note = note;
    }
    public Note(int mid){
        this.mid=mid;
    }

    public Note(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public Note( String note, int mid) {

        this.note = note;
        this.mid = mid;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public int getMid() {
        return mid;
    }
}
