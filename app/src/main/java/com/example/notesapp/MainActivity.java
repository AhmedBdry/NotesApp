package com.example.notesapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.DataFormatException;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note>notes ;
    RecyclerView recyclerView ;
    NoteAdapter noteAdapter ;
    ImageButton button ;
    MyHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new MyHelper(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerid);

        button  = findViewById(R.id.plusid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View view=getLayoutInflater().inflate(R.layout.custom_alertdialog,null);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();
                Button save = view.findViewById(R.id.saveid);
                Button cancel = view.findViewById(R.id.cancelid);
                final EditText editText = view.findViewById(R.id.enternoteid);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nute=editText.getText().toString();
                        Note note=new Note(nute);
                        db.addNotes(note);
                        Toast.makeText(MainActivity.this,"Data added",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        notes =db.getAllNotes();
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                        NoteAdapter noteAdapter =new NoteAdapter(MainActivity.this,notes);
                        recyclerView.setAdapter(noteAdapter);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        notes=db.getAllNotes();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        NoteAdapter noteAdapter =new NoteAdapter(MainActivity.this,notes);
        recyclerView.setAdapter(noteAdapter);

    }

}
