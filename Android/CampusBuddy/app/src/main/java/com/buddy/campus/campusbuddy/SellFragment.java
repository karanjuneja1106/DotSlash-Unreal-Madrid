package com.buddy.campus.campusbuddy;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Use the {@link SellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //private ThumbnailDownloader<Holder> mThumbnailDownloader;

    // TODO: Rename and change types of parameters



    int a,b;
    private String mParam1;
    private String mParam2;
    List<SellItem> mSellItems=new ArrayList<>();
    RecyclerView recyclerView;

    public interface doneObjectListener {
        public void onDataLoaded();

    }
    private doneObjectListener listener;

    public SellFragment() {
        // Required empty public constructor
        this.listener=null;
    }
    public void setCustomObjectListener(doneObjectListener listener) {
        this.listener = listener;
    }

    // TODO: Rename and change types and number of parameters
    public static SellFragment newInstance(String param1, String param2) {
        SellFragment fragment = new SellFragment();
        Bundle args = new Bundle();
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
        this.setCustomObjectListener(new doneObjectListener() {
            @Override
            public void onDataLoaded() {
                recyclerView.setAdapter(new picAdapter(mSellItems));
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Advertisements");
        final JSONObject[] js = {null};
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final SellItem si = new SellItem();
                    String h = postSnapshot.getValue().toString();
                    Matcher m = Pattern.compile("(\\{ad=)(.*)(\\})").matcher(h);
                    if (m.matches()) {
                        String json = m.group(2).toString();
                        try {
                            js[0] = new JSONObject(json);
                            si.setmCaption(js[0].getString("TITLE"));
                            si.setmOwner(js[0].getString("USER"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                    mSellItems.add(si);
                }
                listener.onDataLoaded();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        Log.d("frag","frag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sell, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.sell_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return view;
    }



    public class Holder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        //ImageView imageView;
        TextView caption,owner;
        //private SellItem mSellItem;
        public Holder(View itemView) {
            super(itemView);
            //imageView=(ImageView) itemView.findViewById(R.id.no_image);
            caption=(TextView) itemView.findViewById(R.id.item_caption);
            owner=(TextView) itemView.findViewById(R.id.item_owner);
            itemView.setOnClickListener(this);

        }

        public void bind(Drawable drawable) {
            //imageView.setImageDrawable(drawable);
        }

        public void bindText(String own,String cap){
            caption.setText(cap);
            owner.setText(own);
        }
        private void bindGalleryItem(SellItem item){
            //mSellItem=item;
        }
        @Override
        public void onClick(View v) {

        }
    }
    private class picAdapter extends RecyclerView.Adapter<Holder>{
        List<SellItem> sellItems;

        public picAdapter(List<SellItem> msellItems) {
            this.sellItems = msellItems;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view= layoutInflater.inflate(R.layout.sellview,parent,false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            SellItem item=sellItems.get(position);
            holder.bindGalleryItem(item);
//            Drawable placeholder=getResources().getDrawable(R.drawable.placeholder);
//            mThumbnailDownloader.queueThumbnail(holder,item.getmUrl(),item.getmId());
//            holder.bind(placeholder);
            holder.bindText(item.getmOwner(),item.getmCaption());
        }

        @Override
        public int getItemCount() {
            return sellItems.size();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("a",a);
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<SellItem>> {


        //private ProgressDialog mProgress;
        public FetchItemsTask()
        {

        }

        @Override
        protected void onPreExecute() {
//            if (query==null){
//                if (a!=1){
//                    mProgress=new ProgressDialog(getActivity());
//                    mProgress.setIndeterminate(true);
//                    mProgress.setCancelable(false);
//                    mProgress.setMessage("Loading..........Page "+a);
//                    mProgress.show();}
//                else
//                {
//                    mProgress=new ProgressDialog(getActivity());
//                    mProgress.setIndeterminate(true);
//                    mProgress.setCancelable(false);
//                    mProgress.setMessage("Loading....");
//                    mProgress.show();}}
//            else{
//                if (a!=1){
//                    mProgress=new ProgressDialog(getActivity());
//                    mProgress.setIndeterminate(true);
//                    mProgress.setCancelable(false);
//                    mProgress.setMessage("Loading Search results....Page "+a);
//                    mProgress.show();}
//                else
//                {
//                    mProgress=new ProgressDialog(getActivity());
//                    mProgress.setIndeterminate(true);
//                    mProgress.setCancelable(false);
//                    mProgress.setMessage("Loading Search Results....");
//                    mProgress.show();}
//
//            }

            super.onPreExecute();
        }

        @Override
        protected List<SellItem> doInBackground(Void... params) {
            itemFetchr itemFetchr=new itemFetchr();
            return  itemFetchr.fetchItem();
        }

        @Override
        protected void onPostExecute(List<SellItem> list) {
            //mProgress.dismiss();
            mSellItems=list;
            setupAdapter();
        }
        public void setupAdapter(){
            if (isAdded())
            {

            }
        }
    }

}
