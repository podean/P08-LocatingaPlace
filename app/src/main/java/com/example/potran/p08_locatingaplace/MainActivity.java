package com.example.potran.p08_locatingaplace;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED){
                    map.setMyLocationEnabled(true);
                } else{
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                    Log.e("GMAP - Permission", "GPS access has not been granted");
                }

                final LatLng singapore = new LatLng(1.3521,103.8198);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,11));

                final LatLng poi_East = new LatLng(1.367149, 103.928021);

                final LatLng poi_North = new LatLng(1.433240, 103.781218);

                final LatLng poi_Central = new LatLng(1.297802, 103.847441);

                btn1 = (Button)findViewById(R.id.buttonNorth);
                btn2 = (Button)findViewById(R.id.buttonCentral);
                btn3 = (Button)findViewById(R.id.buttonEast);
                btn4 = (Button)findViewById(R.id.buttonSingapore);


                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null){
//                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,12));
                        }
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null){
//                            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,12));
                        }
                    }
                });

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null){
//                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,12));
                        }
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null){
//                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,11));
                        }
                    }
                });



                UiSettings compass = map.getUiSettings();
                compass.setCompassEnabled(true);

                UiSettings zoom = map.getUiSettings();
                zoom.setZoomControlsEnabled(true);

                Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(poi_North)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 \n Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on)));



                Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Central)
                        .title("Central Branch")
                        .snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Marker east = map.addMarker(new
                        MarkerOptions()
                        .position(poi_East)
                        .title("East Branch")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {

                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.getTitle().equals("East Branch")) // if marker source is clicked
                            Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                        else if (marker.getTitle().equals("Central Branch"))
                            Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        else
                        Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }

                });


            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the read SMS
                    //  as if the btnRetrieve is clicked
//                    btnRetrieve.performClick();

                } else {
                    // permission denied... notify user
                    Toast.makeText(MainActivity.this, "Permission not granted",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}