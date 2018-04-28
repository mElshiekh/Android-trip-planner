package com.example.delllaptop.projone;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.delllaptop.projone.DTO.NotesParce;
import com.example.delllaptop.projone.DTO.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UpdateTripActivity extends AppCompatActivity {
    private static final String TAG = "Hello !";
    EditText Name;
    Button btnTimePicker, btnDatePicker, Next;
    String name, spoint, sLat, sLong, epoint, eLat, eLong,sdate, totaltime;
    private int mYear, mMonth, mDay, mHour, mMin, rep;
    PlaceAutocompleteFragment frag1, frag2;
    double mysLat, mysLong, myeLat, myeLong;
    TextView myDate, myTime;
    Spinner mySpinner1;
    String compareValue;
    int position;
    Trip myTrip;
    Date date;
    Date myDateCheck;
    SharedPreferences saving;
    int hours,min;
    public static final String saveData="NewData";
    public static final String newLogin="NewLogin";
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(!(new CheckPermissions(this).checkInternet())){
//            finish();
//        }
        setContentView(R.layout.activity_update_trip);
        Intent i = getIntent();
        saving=getSharedPreferences(saveData,0);
        user = saving.getString("user", "null");
//        //////////////////////////////////////////////////////////////////////////////////////
//        saving = getApplicationContext().getSharedPreferences(saveData, 0);
//        SharedPreferences.Editor edit = saving.edit();
//        edit.putBoolean("refresh",true);
//        Log.i("user",user);
//        //////////////////////////////////////////////////////////////////////////////////////////////
        Log.i("user",user);
        myTrip = (Trip) i.getSerializableExtra("trip");
        Name = findViewById(R.id.Name_update);
        Name.setText(myTrip.getName(), TextView.BufferType.EDITABLE);
        frag1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_update);
        frag1.setText(myTrip.getSp());
        frag2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2_update);
        frag2.setText(myTrip.getEp());
        myDate = findViewById(R.id.in_date_update);
        myDate.setText(myTrip.getSd());
        myTime = findViewById(R.id.in_time_update);
        myTime.setText(myTrip.getSt());
        mySpinner1 = findViewById(R.id.spinner1_update);
        int value = myTrip.getRep();
        if (value == 0) {
            compareValue = "No Repeat";
        } else if (value == 1) {
            compareValue = "Repeat Daily";
        } else if (value == 7) {
            compareValue = "Repeat Weekly";
        } else if (value == 28) {
            compareValue = "Repeat Monthly";
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Trip_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            mySpinner1.setSelection(spinnerPosition);
        }


        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (frag1 != null)
            frag1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName());
                    spoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    mysLat = myLatLong.latitude;
                    mysLong = myLatLong.longitude;
                    sLat = mysLat + "";
                    sLong = mysLong + "";

                    //  Toast.makeText(MainActivity.this, "the place is "+ placeName ,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });


        if (frag2 != null)
            frag2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place./
                    Log.i(TAG, "Place: " + place.getName());
                    //String placeName = place.getName().toString();
                    //  Toast.makeText(MainActivity.this, "the place is "+ placeName ,Toast.LENGTH_SHORT).show();
                    epoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    myeLat = myLatLong.latitude;
                    myeLong = myLatLong.longitude;
                    eLat = myeLat + "";
                    eLong = myeLong + "";
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });

        btnDatePicker = findViewById(R.id.btn_date_update);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String myDate2 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    date = format.parse(timeStamp);
                                    myDateCheck=format.parse(myDate2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(myDateCheck.before(date))
                                {
                                    Toast.makeText(view.getContext(),"Enter Valid Date",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    myDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnTimePicker = findViewById(R.id.btn_time_update);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMin = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Calendar myCalInstance = Calendar.getInstance();
                                Calendar myRealCalender = Calendar.getInstance();
                                if(myDateCheck==null){
                                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                    try {
                                        myDateCheck = format.parse(myTrip.getSd());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                myRealCalender.setTime(myDateCheck);
                                myRealCalender.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                myRealCalender.set(Calendar.MINUTE,minute);
                                Log.i("time", String.valueOf(myCalInstance.getTime()));
                                Log.i("time", String.valueOf(myRealCalender.getTime()));
                                if((myRealCalender.getTime()).before(myCalInstance.getTime()))
                                {
                                    Toast.makeText(view.getContext(),"Enter Valid Time",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    hours = hourOfDay;
                                    min = minute;
                                    if(hourOfDay<10&&min>=10) {
                                        myTime.setText("0" + hourOfDay + ":" + minute);
                                    }
                                    if(hourOfDay<10&&min<10)
                                    {
                                        myTime.setText("0" + hourOfDay + ":"+"0"+ minute);
                                    }
                                    if(hourOfDay>=10&&min<10)
                                    {
                                        myTime.setText(hourOfDay + ":"+"0"+ minute);
                                    }
                                    if(hourOfDay>=10&&min>=10)
                                    {
                                        myTime.setText(hourOfDay + ":"+ minute);
                                    }
                                }
                            }
                        }, mHour, mMin, false);
                timePickerDialog.show();
            }
        });
        Next = findViewById(R.id.Next_update);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = Name.getText().toString();
                sdate = myDate.getText().toString();
                totaltime = myTime.getText().toString();
                if (position == 0)
                    rep = 0;
                if (position == 1)
                    rep = 1;
                if (position == 2)
                    rep = 7;
                if (position == 3)
                    rep = 30;

                SQLAdapter adaptor = new SQLAdapter(view.getContext());
                if(sLat==null || sLong == null){
                    sLat = myTrip.getSlat();
                    sLong = myTrip.getSlong();
                    spoint = myTrip.getSp();
                }
                if(eLat == null || eLong==null){
                    eLat = myTrip.getElat();
                    eLong = myTrip.getElong();
                    epoint = myTrip.getEp();
                }
                long i2 = adaptor.updateTrip(myTrip.getId(),name,spoint,sLong,sLat,epoint,eLong,eLat, "upcoming",sdate,null,totaltime,totaltime,rep,user,null);
                //hna el alarm set
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                DateFormat formate2 = new SimpleDateFormat("hh:mm");
                Calendar myCal = Calendar.getInstance();
                try {
                    Date myTime = formate2.parse(totaltime);
                    Date date2=format.parse(sdate);
                    Log.i("aya", String.valueOf(myCal.getTime()));
                    myCal.setTime(date2);
                    myCal.set(Calendar.HOUR_OF_DAY,myTime.getHours());
                    myCal.set(Calendar.MINUTE,myTime.getMinutes());
                    AlarmManager myAlarm =(AlarmManager) UpdateTripActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(UpdateTripActivity.this, AlertReciever.class);
                    Bundle bundle = new Bundle();
                    Log.i("myTripnotes",myTrip.getNotes()+"");
                    bundle.putSerializable("trip",myTrip);
                    i.putExtra("bundle",bundle);
                    PendingIntent pi = PendingIntent.getBroadcast(UpdateTripActivity.this, myTrip.getId(), i, PendingIntent.FLAG_UPDATE_CURRENT);
                    myAlarm.set(AlarmManager.RTC_WAKEUP,myCal.getTimeInMillis(),pi);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                            a5er el alarm
                if(myTrip.getNotes().isEmpty())
                {

                    Intent AddNoteIntent = new Intent(view.getContext(), AddNoteActivity.class);
                    AddNoteIntent.putExtra("trip_id", myTrip.getId());
                    startActivity(AddNoteIntent);
                }
                else {
                    Intent updateNoteIntent = new Intent(view.getContext(), UpdateNoteActivity.class);
                    updateNoteIntent.putExtra("trip", myTrip);
                    startActivity(updateNoteIntent);
                }
                finish();
            }
        });



    }
}

