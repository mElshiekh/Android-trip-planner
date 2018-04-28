package com.example.delllaptop.projone;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.delllaptop.projone.DTO.Trip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateNoteActivity extends AppCompatActivity {
    LinearLayout Layout1,Layout2,Layout3,Layout4,Layout5,Layout6,Layout7,Layout8,Layout9,Layout10,myLinear;
    Button addNote;
    Button add1,add2,add3,add4,add5,add6,add7,add8,add9,add10;
    Button cancel1,cancel2,cancel3,cancel4,cancel5,cancel6,cancel7,cancel8,cancel9,cancel10;
    EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10,myEdit;
    int i;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        Layout1=findViewById(R.id.ItemUpd1);
        Layout2=findViewById(R.id.ItemUpd2);
        Layout3=findViewById(R.id.ItemUpd3);
        Layout4=findViewById(R.id.ItemUpd4);
        Layout5=findViewById(R.id.ItemUpd5);
        Layout6=findViewById(R.id.ItemUpd6);
        Layout7=findViewById(R.id.ItemUpd7);
        Layout8=findViewById(R.id.ItemUpd8);
        Layout9=findViewById(R.id.ItemUpd9);
        Layout10=findViewById(R.id.ItemUpd10);
        addNote=findViewById(R.id.UpdNote);
        add1=findViewById(R.id.ItemAddBtnUpd1);
        add2=findViewById(R.id.ItemAddBtnUpd2);
        add3=findViewById(R.id.ItemAddBtnUpd3);
        add4=findViewById(R.id.ItemAddBtnUpd4);
        add5=findViewById(R.id.ItemAddBtnUpd5);
        add6=findViewById(R.id.ItemAddBtnUpd6);
        add7=findViewById(R.id.ItemAddBtnUpd7);
        add8=findViewById(R.id.ItemAddBtnUpd8);
        add9=findViewById(R.id.ItemAddBtnUpd9);
        add10=findViewById(R.id.ItemAddBtnUpd10);
        cancel1=findViewById(R.id.ItemDelBtnUpd1);
        cancel2=findViewById(R.id.ItemDelBtnUpd2);
        cancel3=findViewById(R.id.ItemDelBtnUpd3);
        cancel4=findViewById(R.id.ItemDelBtnUpd4);
        cancel5=findViewById(R.id.ItemDelBtnUpd5);
        cancel6=findViewById(R.id.ItemDelBtnUpd6);
        cancel7=findViewById(R.id.ItemDelBtnUpd7);
        cancel8=findViewById(R.id.ItemDelBtnUpd8);
        cancel9=findViewById(R.id.ItemDelBtnUpd9);
        cancel10=findViewById(R.id.ItemDelBtnUpd10);
        edit1=findViewById(R.id.ItemEditTextUpd1);
        edit2=findViewById(R.id.ItemEditTextUpd2);
        edit3=findViewById(R.id.ItemEditTextUpd3);
        edit4=findViewById(R.id.ItemEditTextUpd4);
        edit5=findViewById(R.id.ItemEditTextUpd5);
        edit6=findViewById(R.id.ItemEditTextUpd6);
        edit7=findViewById(R.id.ItemEditTextUpd7);
        edit8=findViewById(R.id.ItemEditTextUpd8);
        edit9=findViewById(R.id.ItemEditTextUpd9);
        edit10=findViewById(R.id.ItemEditTextUpd10);
        Intent intent  = getIntent();
        final Trip tripy = (Trip) intent.getSerializableExtra("trip");
        final ArrayList<String> myNotes=new ArrayList();
        final ArrayList<String> myNotesOld=tripy.getNotes();
        Log.i("yarabna",myNotesOld+"");

//        for(i=0;i<myNotesOld.size();i++)
//        {
//            "Layout"+i+1=findViewById(R.id."ItemUpd"+i+1).setVisibility(View.VISIBLE);
//     }
        for (int i=0; i<myNotesOld.size(); i++) {
            int j = i+1;
            Log.i("yarabna",myNotesOld.get(i)+"");
            String LayoutID="ItemUpd"+j;
            int layoutId = getResources().getIdentifier(LayoutID,"id",getPackageName());
            myLinear=findViewById(layoutId);
            Log.i("yarabna",myLinear+"");
            myLinear.setVisibility(View.VISIBLE);
            String TextID = "ItemEditTextUpd"+ j;
            int resID = getResources().getIdentifier(TextID, "id", getPackageName());
            myEdit = findViewById(resID);
            Log.i("yarabna",myEdit+"");
            myEdit.setText(myNotesOld.get(i));

        }


        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit1.setText("empty");
            }
        });

        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit2.setText("empty");
            }
        });

        cancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit3.setText("empty");
            }
        });

        cancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit4.setText("empty");
            }
        });
        cancel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit5.setText("empty");
            }
        });
        cancel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit6.setText("empty");
            }
        });
        cancel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit7.setText("empty");
            }
        });
        cancel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit8.setText("empty");
            }
        });
        cancel9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit9.setText("empty");
            }
        });
        cancel10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit10.setText("empty");
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout2.setVisibility(View.VISIBLE);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout3.setVisibility(View.VISIBLE);
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout4.setVisibility(View.VISIBLE);

            }
        });

        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout5.setVisibility(View.VISIBLE);
            }
        });

        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout6.setVisibility(View.VISIBLE);
            }
        });

        add6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout7.setVisibility(View.VISIBLE);
            }
        });

        add7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout8.setVisibility(View.VISIBLE);
            }
        });

        add8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout9.setVisibility(View.VISIBLE);
            }
        });

        add9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout10.setVisibility(View.VISIBLE);
            }
        });

        add10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myNotes.clear();
                if(!edit1.getText().toString().equals("empty")&&!edit1.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit1.getText().toString()))
                    {
                        myNotes.add(edit1.getText().toString());
                    }

                }
                if(!edit2.getText().toString().equals("empty")&&!edit2.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit2.getText().toString()))
                    {
                        myNotes.add(edit2.getText().toString());
                    }
                }
                if(!edit3.getText().toString().equals("empty")&&!edit3.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit3.getText().toString()))
                    {
                        myNotes.add(edit3.getText().toString());
                    }
                }
                if(!edit4.getText().toString().equals("empty")&&!edit4.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit4.getText().toString()))
                    {
                        myNotes.add(edit4.getText().toString());
                    }
                }
                if(!edit5.getText().toString().equals("empty")&&!edit5.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit5.getText().toString()))
                    {
                        myNotes.add(edit5.getText().toString());
                    }
                }
                if(!edit6.getText().toString().equals("empty")&&!edit6.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit6.getText().toString()))
                    {
                        myNotes.add(edit6.getText().toString());
                    }
                }
                if(!edit7.getText().toString().equals("empty")&&!edit7.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit7.getText().toString()))
                    {
                        myNotes.add(edit7.getText().toString());
                    }
                }
                if(!edit8.getText().toString().equals("empty")&&!edit8.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit8.getText().toString()))
                    {
                        myNotes.add(edit8.getText().toString());
                    }

                }
                if(!edit9.getText().toString().equals("empty")&&!edit9.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit9.getText().toString()))
                    {
                        myNotes.add(edit9.getText().toString());
                    }
                }
                if(!edit10.getText().toString().equals("empty")&&!edit10.getText().toString().isEmpty())
                {
                    if(!myNotes.contains(edit10.getText().toString()))
                    {
                        myNotes.add(edit10.getText().toString());
                    }
                }

                for(String k:myNotes)
                {
                    Log.i("res",k);
                }
                int min=myNotesOld.size();
                int flag = 0;
                if(myNotes.size()<myNotesOld.size()){
                    min = myNotes.size();
                    flag=1;
                }

                SQLAdapter adaptor = new SQLAdapter(view.getContext());
                if(!myNotes.isEmpty()) {
                    for (int j = 0; j < min; j++) {
                        adaptor.updateNote(myNotes.get(j),myNotesOld.get(j),tripy.getId());
                        Log.i("check",myNotesOld.get(j));
                    }
                }
                if(flag==0){
                    for(int i=min;i<myNotes.size();i++){
                        adaptor.insertNote(myNotes.get(i), tripy.getId());
                        Log.i("check",myNotes.get(i));

                    }
                }
                else{
                    for(int i=min;i<myNotesOld.size();i++) {
                        adaptor.deleteNote(myNotesOld.get(i), tripy.getId());
                      //  Log.i("check",myNotes.get(i)+" "+myNotesOld.get(i));

                    }
                }
                ArrayList<Trip> trips = adaptor.retrieveTrips(tripy.getUser());
                for (Trip trip : trips) {
                    if(trip.getId()==tripy.getId()){
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        DateFormat formate2 = new SimpleDateFormat("hh:mm");
                        Calendar myCal = Calendar.getInstance();

                        Date date2= null;
                        try {
                            Date myTime = formate2.parse(trip.getSt());
                            date2 = format.parse(trip.getSd());
                            Log.i("aya", String.valueOf(myCal.getTime()));
                            myCal.setTime(date2);
                            myCal.set(Calendar.HOUR_OF_DAY,myTime.getHours());
                            myCal.set(Calendar.MINUTE,myTime.getMinutes());
                            AlarmManager myAlarm =(AlarmManager) UpdateNoteActivity.this.getSystemService(Context.ALARM_SERVICE);
                            Intent i = new Intent(UpdateNoteActivity.this, AlertReciever.class);
                            Bundle bundle = new Bundle();
                            Log.i("myTripnotes",trip.getNotes()+"");
                            bundle.putSerializable("trip",trip);
                            i.putExtra("bundle",bundle);
                            PendingIntent pi = PendingIntent.getBroadcast(UpdateNoteActivity.this, trip.getId(), i, PendingIntent.FLAG_UPDATE_CURRENT);
                            myAlarm.set(AlarmManager.RTC_WAKEUP,myCal.getTimeInMillis(),pi);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
                finish();
            }
        });
    }
}


