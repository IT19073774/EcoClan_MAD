package com.example.ecoclan_v2;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserMap extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    public static final String TAG = "TAG";


    private GoogleMap mMap;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    EditText resource_name, estimate_weight;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Double Lat, Lng;
    Button setlocationbtn, backbtn ;
    Spinner materialspinner;

    String UserID,UserEmail,material;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          material = materialspinner.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);
        setContentView(R.layout.activity_user_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        resource_name = findViewById(R.id.resource_name);
        estimate_weight = findViewById(R.id.weight);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        UserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();

        // Spinner element
        materialspinner = (Spinner) findViewById(R.id.materialspinner);
        materialspinner.setOnItemSelectedListener(this);
        List<String> meterials = new ArrayList<>();
        meterials.add("Paper");
        meterials.add("Glass");
        meterials.add("Plastic");
        meterials.add("Cloth");
        meterials.add("Metal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meterials);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialspinner.setAdapter(dataAdapter);





        setlocationbtn = (Button) findViewById(R.id.setbtn);

        //back button
        backbtn = (Button) findViewById(R.id.backbtn1);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserMap.this, userAccount.class);
                startActivity(i);
                finish();
            }
        });

        setlocationbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //final String material = material_name.getText().toString().trim();
                final String resource = resource_name.getText().toString().trim();
                final String weight = estimate_weight.getText().toString().trim();
                setlocationbtn.setVisibility(View.GONE);


                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Pick up here!"));


                DocumentReference docRef = fStore.collection("Counter").document("Count");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                final Integer Count =  Integer.parseInt(document.getData().get("Count").toString());
                                Lat = mLastLocation.getLatitude();
                                Lng = mLastLocation.getLongitude();

                                final Map<String, Object> requests = new HashMap<>();
                                requests.put("UserID", UserID.toString());
                                requests.put("resourceID", "RES0" + Count);
                                requests.put("Material", material);
                                requests.put("resource", resource);
                                requests.put("giverID", UserEmail.toString());
                                requests.put("Latitude", Lat);
                                requests.put("Longitude", Lng);
                                requests.put("EWeight", Double.parseDouble(weight));

                                final Map<String, Object> requests3 = new HashMap<>();
                                requests3.put("Count", Count+1);
                                requests3.put("giverID", "ADMIN");

                                fStore.collection("Resources").document("RES0" + Count).set(requests).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            fStore.collection("Counter").document("Count").set(requests3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Pickup requested", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                                backbtn.setVisibility(View.VISIBLE);

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

    }


    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

      //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    public void searchLocation(View view) {
//        EditText locationSearch = (EditText) findViewById(R.id.editText);
//        String location = locationSearch.getText().toString();
//        List<Address> addressList = null;
//
//        if (location != null || !location.equals("")) {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Address address = addressList.get(0);
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            Toast.makeText(getApplicationContext(),address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_LONG).show();
//        }
//    }

}