package com.example.delllaptop.projone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.delllaptop.projone.DTO.DownloadImage;
import com.example.delllaptop.projone.DTO.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Dell Laptop on 3/13/2018.
 */

public class SyncDownload extends AsyncTask<ArrayList<Trip>,Void,Integer> {


    ArrayList<Trip> trips;
    ArrayList<Trip> tripsnew;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private FirebaseUser fuser;
    SQLAdapter adapter;
    Context context;

    public SyncDownload(Context context, FirebaseAuth mAuth, DatabaseReference db, FirebaseUser fuser) {
        this.mAuth = mAuth;
        this.db = db;
        this.fuser = fuser;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(ArrayList<Trip>[] arrayLists) {
        Log.i("elidfire",fuser.getEmail());
        adapter = new SQLAdapter(context);
        tripsnew = new ArrayList<Trip>();
        trips=adapter.retrieveTrips(fuser.getEmail());
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("firebase",dataSnapshot.getChildren().toString());
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dss : templateSnapshot.getChildren()) {
                        Trip trip = dss.getValue(Trip.class);
                        tripsnew.add(trip);
                    }
                }
                for(Trip trip: tripsnew){
                    int flag = 0;
                    for(Trip temp: trips){
                        if(trip.getName().equals(temp.getName()) && trip.getSd().equals(temp.getSd())){
                            Log.i("checking","7asal");
                            flag=1;
                        }
                    }
                    if(flag==0){
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        Calendar c = Calendar.getInstance();
                        Calendar c2 = Calendar.getInstance();
                        try {
                            c2.setTime(format.parse(trip.getSd()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.i("thedate",c+" "+c2);

                        if(c.after(c2) && trip.getStatus().contains("upcoming")) {
                            adapter.insertTrip(trip.getName(), trip.getSp(), trip.getSlong(), trip.getSlat(), trip.getEp(), trip.getElong(), trip.getElat(), "cancelled", trip.getSd(), trip.getSd(), trip.getSt(), trip.getSt(), trip.getRep(), trip.getUser(), "new");
                        }else{

                            adapter.insertTrip(trip.getName(),trip.getSp(),trip.getSlong(),trip.getSlat(),trip.getEp(),trip.getElong(),trip.getElat(), trip.getStatus(),trip.getSd(),trip.getSd(),trip.getSt(),trip.getSt(),trip.getRep(),trip.getUser(),"new");
                        }
                    }
                }
                trips = adapter.retrieveTrips(fuser.getEmail());
                Log.i("thedate",trips.size()+"");
                for(Trip trip: trips){
                    Log.i("thedate", trip.getUrl()+"");
                    if(trip.getUrl()!=null) {
                        Log.i("thedate", "new");
                        if (!trip.getStatus().contains("upcoming")) {
                            Log.i("thedate", "da5al hna");
                            String webLink = "https://maps.googleapis.com/maps/api/directions/json?origin=" + trip.getSlat() +
                                    "," + trip.getSlong() + "&destination=" + trip.getElat()
                                    + "," + trip.getElong();
                            DownloadImage dImg = new DownloadImage(webLink, context, trip);
                            dImg.execute();
//                            long i = adapter.updateTrip(trip.getId(), trip.getName(), trip.getSp(), trip.getSlong(), trip.getSlat(), trip.getEp(), trip.getElong(), trip.getElat(), trip.getStatus(), trip.getSd(), trip.getSd(), trip.getSt(), trip.getSt(), trip.getRep(), trip.getUser(), null);
                        }
                        else{
                            //hna el alarm set
                            String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            DateFormat formate2 = new SimpleDateFormat("hh:mm");
                            Calendar myCal = Calendar.getInstance();
                            try {
                                Date myTime = formate2.parse(trip.getSt());
                                Date date2=format.parse(trip.getSd());
                                Log.i("aya", String.valueOf(myCal.getTime()));
                                myCal.setTime(date2);
                                myCal.set(Calendar.HOUR_OF_DAY,myTime.getHours());
                                myCal.set(Calendar.MINUTE,myTime.getMinutes());
                                HashMap<Integer,Date> myData = new HashMap<Integer, Date>();
                                AlarmManager myAlarm =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                                Intent i = new Intent(context, AlertReciever.class);
                                i.putExtra("trip",trip);
                                PendingIntent pi = PendingIntent.getBroadcast(context, trip.getId(), i, 0);
                                myAlarm.set(AlarmManager.RTC_WAKEUP,myCal.getTimeInMillis(),pi);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

//                            a5er el alarm
                        }
                        for (Trip temp : tripsnew) {
                            Log.i("notessync", "ahoh ahoh ahoh");
                            if (trip.getName().equals(temp.getName()) && trip.getSd().equals(temp.getSd())) {
                                Log.i("notessync","ahoh ahoh ahoh ya rab");
                                if(temp.getNotes()!=null)
                                for (String note : temp.getNotes()) {
                                   long i= adapter.insertNote(note, trip.getId());
                                    Log.i("notessync","ahoh ahoh ahoh" + i);
                                }
                            }
                        }
                        trip.setUrl(null);
                        long i = adapter.updateTrip(trip.getId(), trip.getName(), trip.getSp(), trip.getSlong(), trip.getSlat(), trip.getEp(), trip.getElong(), trip.getElat(), trip.getStatus(), trip.getSd(), trip.getSd(), trip.getSt(), trip.getSt(), trip.getRep(), trip.getUser(), null);
                        Log.i("elxxxxi",i+"");
                    }
                    db.child(fuser.getUid()).child(trip.getId()+"").setValue(trip);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return null;
    }
}
