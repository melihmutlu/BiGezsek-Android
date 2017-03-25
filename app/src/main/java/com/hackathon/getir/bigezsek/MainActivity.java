package com.hackathon.getir.bigezsek;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private android.widget.Toolbar toolbar;
    private GoogleMap map;
    private FloatingSearchView searchView;
    private Context context;
    private LatLng loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // Set toolbar
        toolbar = (android.widget.Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                Intent i = new Intent(context, GooglePlacesActivity.class);
                startActivityForResult(i,1);
            }

            @Override
            public void onFocusCleared() {

            }
        });

        // Get Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                // Get results
                String placeID =data.getStringExtra("placeID");
                String placeName = data.getStringExtra("placeName");
                LatLng placeLatLng = (LatLng) data.getExtras().get("placeLatLng");

                Bundle bundle = new Bundle();
                bundle.putString("placeID", placeID);
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.setArguments(bundle);
                dFragment.show(getFragmentManager(), "Date Picker");

                searchView.setSearchText(placeName);

            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Ask for permission
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        map.setMyLocationEnabled(true);

        // Get and display current location
        GoogleMap.OnMyLocationChangeListener locationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange (Location location) {
                loc = new LatLng (location.getLatitude(), location.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        };

        map.setOnMyLocationChangeListener(locationChangeListener);


    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        static String placeID;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            placeID = getArguments().getString("placeID");
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            Intent getCards = new Intent(getActivity(),CardUIActivity.class);
            getCards.putExtra("location",placeID);
            getCards.putExtra("date",""+day+"."+(month+1)+"."+year);
            startActivity(getCards);
        }
    }


}
