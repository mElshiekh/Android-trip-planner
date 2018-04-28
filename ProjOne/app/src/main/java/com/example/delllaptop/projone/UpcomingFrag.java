package com.example.delllaptop.projone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.delllaptop.projone.DTO.Trip;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rv;
    CardViewUpcoming cvu;
    ArrayList<Trip> trips;
    ArrayList<Trip> tripsupcoming;
    Button add_but;
    SQLAdapter adapter;
    SharedPreferences saving;
    public static final String saveData="NewData";
    public static final String newLogin="NewLogin";
    String user;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UpcomingFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingFrag newInstance(String param1, String param2) {
        UpcomingFrag fragment = new UpcomingFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        //
        adapter = new SQLAdapter(view.getContext());
        //
        saving=getActivity().getSharedPreferences(saveData,0);
        user = saving.getString("user", "null");
        Log.i("user",user);
        trips=adapter.retrieveTrips(user);
        Log.i("user",trips.size()+"");
        tripsupcoming=new ArrayList<Trip>();
        ///set eltrips
//        ArrayList<String> notes = new ArrayList<>();
//        Trip t1 = new Trip("name","spoint","slong","slat","epoint",
//                "elong","elat","upcoming","sdate","edate","stime","etime",7,notes);
//        t1.setId(1);
//        trips.add(t1);
//        Trip t2 = new Trip("name","spoint","slong","slat","epoint",
//                "elong","elat","upcoming","sdate","edate","stime","etime",7,notes);
//        t2.setId(2);
//        trips.add(t2);
//
//        Trip t3 = new Trip("name","spoint","slong","slat","epoint",
//                "elong","elat","upcoming","sdate","edate","stime","etime",7,notes);
//        t3.setId(3);
//        trips.add(t3);
//
//        Trip t4 = new Trip("name","spoint","slong","slat","epoint",
//                "elong","elat","upcoming","sdate","edate","stime","etime",7,notes);
//        t4.setId(4);
//        trips.add(t4);


        for(Trip temp:trips){
            Trip t;
            if (temp.getStatus().toString().equals("upcoming")){
                t = temp;
                tripsupcoming.add(t);
            }
        }
        rv = (RecyclerView) view.findViewById(R.id.recyclerupcoming);
        cvu = new CardViewUpcoming(getActivity(),tripsupcoming);
        rv.setAdapter(cvu);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        add_but = view.findViewById(R.id.but_add);
        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(),AddTripActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent1);
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
