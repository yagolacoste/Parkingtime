package yagu.finaldeobjetos;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

import static yagu.finaldeobjetos.DatosEstacionamiento.idCuadra;
import static yagu.finaldeobjetos.MapsActivity.area;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class GeofenceRegistrationService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String TAG  = "GeofenceIntentService";
    NotificationCompat.Builder notificacion;
    private static final int idUnica = 51623;
    private MapsActivity mp;
    private DatosEstacionamiento dt;
    private MapsActivityEstacionar mae;

    public GeofenceRegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()){
            Log.d(TAG,"GeofencingEvent error" + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            if(transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(mp.GEOFERENCE_ID)){
                Log.d(TAG,"Estas dentro de la zona monitoreo");
            } else {
                LatLng latLng = area.get(mp.GEOFERENCE_ID);
                String consulta = "http://192.168.0.14/finalobjetos/RestaurarDatos.php?latitud="+latLng.latitude+"&longitud="+latLng.longitude+"&idCuadra="+idCuadra;
                RestaurarDatos(consulta);
                Log.d(TAG,"Estas saliendo con el auto");
                notificacion = new NotificationCompat.Builder(this);
                notificacion.setAutoCancel(true);
                notificacion.setSmallIcon(R.mipmap.ic_launcher);
                notificacion.setTicker("Nueva notificacion");
                notificacion.setPriority(Notification.PRIORITY_HIGH);
                notificacion.setWhen(System.currentTimeMillis());
                notificacion.setContentTitle("ATENCION");
                notificacion.setContentText("Acordate de pasar tu tarjeta SUMO");
                PendingIntent pendingIntent = PendingIntent.getActivity(GeofenceRegistrationService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notificacion.setContentIntent(pendingIntent);
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(idUnica,notificacion.build());


            }
        }
    }

    public void RestaurarDatos(String URL){

        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }
}
