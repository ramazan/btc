package ramazandemircom.appex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {
    String url = "https://www.paribu.com/ticker";
    String url2 = "https://www.bitstamp.net/api/ticker";
    TextView t1,t2,t3,t4,t5;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (TextView) findViewById(R.id.textView2);
        t4 = (TextView) findViewById(R.id.textView5);
        t3 = (TextView) findViewById(R.id.textView3);
        t5 = (TextView) findViewById(R.id.textView6);
        t2 = (TextView) findViewById(R.id.textView4);
        btn = (Button) findViewById(R.id.button);


        personData();
        parseBitstmp();

        Toast.makeText(MainActivity.this,"Veriler Yüklendi....",Toast.LENGTH_SHORT).show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                personData();
                parseBitstmp();

                Toast.makeText(MainActivity.this,"Yenilendi",Toast.LENGTH_SHORT).show();

            }
        });
    }

    void personData(){


        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                JSONObject object = null;
                try {
                    object = new JSONObject(string);
                    JSONObject sys = object.getJSONObject("BTC_TL");
                    String lastPrice = sys.getString("last");
                    String lowPrice24hr = sys.getString("low24hr");
                    String highPrice24hr = sys.getString("high24hr");
                    String volume = sys.getString("volume");
                    t1.setText("Sitedeki Son Fiyat : "+lastPrice+" TL");
                    t2.setText("24 Saat En Düşük : "+lowPrice24hr+" TL");
                    t3.setText("24 Saat En Yüksek : "+highPrice24hr+" TL");
                    t4.setText("Hacim : "+volume);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);

    }


    void parseBitstmp(){


        StringRequest request = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                JSONObject object = null;
                try {
                    object = new JSONObject(string);
                    String lastPrice = object.getString("last");
                   // String lowPrice24hr = object.getString("low24hr");
                    //String highPrice24hr = object.getString("high24hr");
                    t5.setText("Amerika Son Fiyat : "+lastPrice+" $");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);

    }

}