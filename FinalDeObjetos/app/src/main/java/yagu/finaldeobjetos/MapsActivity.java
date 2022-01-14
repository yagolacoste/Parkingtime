package yagu.finaldeobjetos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;
import java.util.Vector;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap googleMap;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private MapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private Marker currentLocationMarker;
    private MarkerOptions markerOptions;
    private PendingIntent pendingIntent;
    private GeofencingRequest geofencingRequest;
    private LatLng ubicacion;
    public static final String GEOFERENCE_ID = "REFERENCIA";
    public static final float GEOFENCE_RADIO_EN_METROS = 100;
    public static final int time_interval = 2000;
    public static final int fastest_interval = 1000;
    public static final HashMap<String,LatLng> area = new HashMap<String,LatLng>();
    private String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SharedPreferences sharpref = getSharedPreferences("ArchivoSP",MODE_PRIVATE);
        valor=sharpref.getString("COORDENADAS","No hay valor");
        Toast.makeText(getApplicationContext(),"Dato guardado:"+valor,Toast.LENGTH_LONG).show();
        if (!valor.equals("No hay valor")) {
            String lat = (String) valor.subSequence(10, 20);
            String logi = (String) valor.subSequence(21, 31);
            ubicacion = new LatLng(Double.parseDouble(lat), Double.parseDouble(logi));
            area.put(GEOFERENCE_ID,ubicacion);
        }
        createGoogleApi();
        boolean condicion = false;
        int condicionNumerica = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (condicionNumerica != 0) condicion = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && condicion) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

    }


    // Create GoogleApiClient instance
    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // Call GoogleApiClient connection when starting the Activity
        //googleApiClient.connect();
    }


    private void startLocationMonitor() {
        Log.d(TAG, "start location monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(time_interval)
                .setFastestInterval(fastest_interval)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {
            System.out.println("LLegue aca");
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    System.out.println("Cambia de posicion");
                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(location.getLatitude(),location.getLongitude()));
                    markerOptions.title("Ubicacion actual");
                    currentLocationMarker = googleMap.addMarker(markerOptions);
                    System.out.println("ubicacion: " + location.toString());
                    Log.d(TAG,"Cambio de localizacion Lat Lng "+ location.getLatitude() + " " + location.getLongitude());
                }
            });
        } catch (SecurityException e){
            Log.d(TAG,e.getMessage());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (response != ConnectionResult.SUCCESS) {
            Log.d(TAG,"Google play service no permitido");
            GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this,response,1).show();
        } else {
            Log.d(TAG, "Google play servicios permitidos");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Call GoogleApiClient connection when starting the Activity
        googleApiClient.reconnect();
    }


    private void startGeofencing() {
        Log.d(TAG, "Empieza el llamado de monitoreo");
        pendingIntent = getGeofencePendingIntent();
        geofencingRequest = new GeofencingRequest.Builder()
                .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
                .addGeofence(getGeofence())
                .build();
        if (!googleApiClient.isConnected())
            Log.d(TAG, "Google API no conectada");
        else {
            try {
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofencingRequest, pendingIntent).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.d(TAG, "Geofencinf conectada exitosamente");
                        } else
                            Log.d(TAG, "Fallo al agregar geofencing" + status.getStatus());
                    }
                });
            } catch (SecurityException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    @NonNull
    private Geofence getGeofence(){
        LatLng latLng = area.get(GEOFERENCE_ID);
        return  new  Geofence.Builder()
                .setRequestId(GEOFERENCE_ID)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(latLng.latitude,latLng.longitude,GEOFENCE_RADIO_EN_METROS)
                .setNotificationResponsiveness(1000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private PendingIntent getGeofencePendingIntent(){
        if(pendingIntent!=null){
            return  pendingIntent;
        }
        Intent intent = new Intent(this,GeofenceRegistrationService.class);

        return PendingIntent.getService(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.googleMap = googleMap;

        if (!valor.equals("No hay valor")){
            LatLng latLng = area.get(GEOFERENCE_ID);
            //System.out.println("Ubicacion : " + latLng);
            // Add a marker in Sydney and move the camera
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Punto de Geofencing"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f));
            googleMap.setMyLocationEnabled(true);

            Circle circle =  googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(latLng.latitude,latLng.longitude))
                    .radius(GEOFENCE_RADIO_EN_METROS)
                    .strokeColor(Color.RED)
                    .strokeWidth(4f));
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Dato no definido",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick("+latLng +")");

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClickListener: " + marker.getPosition() );
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        Log.i(TAG, "onConnected()");
        if (!valor.equals("No hay valor")) {
            startGeofencing();
            startLocationMonitor();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()" + connectionResult.getErrorMessage());
    }
}
