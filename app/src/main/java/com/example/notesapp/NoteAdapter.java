package com.example.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {


    Note note ;
    Context context ;
    ArrayList<Note>notes;
    MyHelper db;
    int id ;


    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row,viewGroup,false);
        MyViewHolder holder =new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        note = notes.get(i);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        myViewHolder.textView1.setText(currentDate);
        myViewHolder.textView2.setText(note.getNote());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Note note=notes.get(i);
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                View view=LayoutInflater.from(v.getContext()).inflate(R.layout.custom_alertdialog2,null);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();
                Button button = view.findViewById(R.id.editid);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                        View view=LayoutInflater.from(v.getContext()).inflate(R.layout.edit_alertdialog,null);
                        builder.setView(view);
                        final AlertDialog dialog=builder.create();
                        dialog.show();
                        final EditText editText =view.findViewById(R.id.editnoteid);
                        editText.setText(note.getNote());

                        Button btn = view.findViewById(R.id.updateid);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "data updated", Toast.LENGTH_SHORT).show();
                                String x = editText.getText().toString();
                                Note note1 = new Note(x,note.getMid());
                                db = new MyHelper(v.getContext());
                                db.updateNote(note1);
                                dialog.dismiss();
                                notifyDataSetChanged();

                            }
                        });



                        Button button1 = view.findViewById(R.id.cancelid);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

               Button button1 = view.findViewById(R.id.deleteid);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        dialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                        builder1.setTitle("Confirmation")
                                .setMessage("Are you sure")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db = new MyHelper(v.getContext());
                                        db.deleteContact(note.getMid());
                                        Toast.makeText(v.getContext(), "data deleted", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog=builder1.create();
                        dialog.show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2 ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.dateid);
            textView2 = itemView.findViewById(R.id.noteid);
        }

    }

}
