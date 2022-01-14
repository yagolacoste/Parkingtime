package yagu.finaldeobjetos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class DatosEstacionamiento extends AppCompatActivity {
    Button btnObtenerDatos;
    ListView listaResultado;
    EditText etDestinoPrincipal,etEntre1,etEntre2;
    static String idCuadra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_datos_estacionamiento);
        btnObtenerDatos=(Button) findViewById(R.id.IdButtonDatos);
        etDestinoPrincipal=(EditText) findViewById(R.id.etDestinoPrinciapl);
        etEntre1 = (EditText) findViewById(R.id.etEntre1);
        etEntre2 = (EditText) findViewById(R.id.etEntre2);
        listaResultado = (ListView)findViewById(R.id.lvResultado);

       listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String textItemList = (String) listaResultado.getItemAtPosition(position);
               String consulta = "http://192.168.0.14/finalobjetos/consultarLugar.php?idCuadra="+idCuadra;
               buscarCoordenadas(consulta);

           }
       });
       btnObtenerDatos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String destinoPrincipal = etDestinoPrincipal.getText().toString().replace(" ","%20");
               String entre1 = etEntre1.getText().toString().replace(" ","%20");
               String entre2 = etEntre2.getText().toString().replace(" "," %20");
               String consulta = "http://192.168.0.14/finalobjetos/consultarCuadra.php?calle1="+destinoPrincipal+"&calle2="+entre1+"&calle3="+entre2;
               consultarCuadra(consulta);
           }
       });
    }

    private void buscarCoordenadas(String URL){

        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        String infoEnviar = ja.getString(0 ) + " " + ja.getString(1);
                        Intent intent = new Intent(getApplicationContext(),MapsActivityEstacionar.class);
                        intent.putExtra("Info",infoEnviar);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    private void consultarCuadra(String URL){

        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    private void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=3){

            try {

                idCuadra = ja.getString(0);
                lista.add("Lugares libres: "+ja.getString(i+1)+" Direccion:"+ja.getString(i+2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listaResultado.setAdapter(adaptador);
    }

    public String getidCuadra(){
        return idCuadra;
    }
}
