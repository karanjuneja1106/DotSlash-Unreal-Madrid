package com.buddy.campus.campusbuddy;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class mapActivity extends AppCompatActivity {
    MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string
        //inflate and create the map
        setContentView(R.layout.activity_map);
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        GeoPoint startPoint = new GeoPoint(21.1652833, 72.7864436);
        mapController.setCenter(startPoint);
        ArrayList<OverlayItem> items = new ArrayList<>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("/Files");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("TAG", ""+ds);
                }
//                ArrayAdapter<String> adapter = new ArrayAdapter(OtherUsersActivity.this, android.R.layout.simple_list_item_1, array);
//                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        usersdRef.addValueEventListener(eventListener);
        items.add(new OverlayItem("title","description",new GeoPoint(21.1652833, 72.7864436)));
        items.add(new OverlayItem("DESC","Title",new GeoPoint(21.1702, 72.8511)));
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this,items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
    }
    @Override
    public void onResume(){
        super.onResume();
        map.onResume();
    }
    @Override
    public  void onPause(){
        super.onPause();
        map.onPause();
    }
}