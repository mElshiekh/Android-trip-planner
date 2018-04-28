package com.example.delllaptop.projone;

import android.content.Context;
import android.os.AsyncTask;

import com.example.delllaptop.projone.DTO.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Dell Laptop on 3/13/2018.
 */

public class SyncUpload extends AsyncTask<ArrayList<Trip>,Void,Integer> {


    ArrayList<Trip> trips;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private FirebaseUser fuser;
    SQLAdapter adapter;
    Context context;

    public SyncUpload(Context context, FirebaseAuth mAuth, DatabaseReference db, FirebaseUser fuser) {
        this.mAuth = mAuth;
        this.db = db;
        this.fuser = fuser;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(ArrayList<Trip>[] arrayLists) {
        adapter = new SQLAdapter(context);
        trips=adapter.retrieveTrips(fuser.getEmail());
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(Trip trip : trips){
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
