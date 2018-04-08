package com.buddy.campus.campusbuddy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<infoItem> minfoItems=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

    public UserInfo() {
        // Required empty public constructor
    }



    public static UserInfo newInstance(String param1, String param2) {
        UserInfo fragment = new UserInfo();
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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        final JSONObject[] js = {null};
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                minfoItems.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   for(DataSnapshot snap2 : postSnapshot.getChildren()){
                    final infoItem si = new infoItem();
                    String h = snap2.getValue().toString();
                    Matcher m = Pattern.compile("(\\{blog=)(.*)(\\})").matcher(h);
                    if (m.matches()) {
                        String json = m.group(2).toString();
                        try {
                            js[0] = new JSONObject(json);
                            si.setName(js[0].getString("name"));
                            si.setTitle(js[0].getString("title"));
                            si.setTxt(js[0].getString("text"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                    minfoItems.add(si);
                    }
                }

                //listener.onDataLoaded();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_user_info, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.info_recycler_view);
        return view;
    }

}
