package com.example.tundex_droid;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.view.View.OnClickListener;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PointF;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity
        implements UserLocationObjectListener,OnClickListener , NavigationView.OnNavigationItemSelectedListener{

    String[] droid;
    private final String MAPKIT_API_KEY = "f75de670-3871-4602-b8ee-8447b27fe253";
    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    TextView selection;

    Connection con;
    String un, pass, db, ip;

    @SuppressLint("NewApi")
    public Connection connectoinclass(String user, String password, String database, String server)
    {
        int sdk_int = Build.VERSION.SDK_INT;
        if (sdk_int > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection connection = null;
        String connectionURL = null;
        try{
            Class.forName("net.soursforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver//" + server + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error 2: ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error 3: ", e.getMessage());
        }
        return connection;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, 1);
            }
        }
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
        findViewById(R.id.nav_bar_open).setOnClickListener(this);
        findViewById(R.id.btn_call_droid).setOnClickListener(this);


        ip ="localhost";
        db = "robotsandusers";
        un = "mysql";
        pass = "mysql";

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMap().setRotateGesturesEnabled(false);
        mapView.getMap().move(new CameraPosition(new Point(0, 0), 14, 0, 0));

        userLocationLayer = mapView.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);

        class CheckLogin extends AsyncTask<String,String,String>
        {
            String z = "";
            Boolean IsSuccsess = false, isFree;

            double LAT, ION;
            @Override
            protected String doInBackground(String... params)
            {
                TextView typeR = (TextView) findViewById(R.id.selection);
                String type =  typeR.getText().toString();
                if (type.trim().equals(""))
                {
                    z = "fail";
                }
                else
                {
                    try {
                        con = connectoinclass(un,pass,db,ip);
                        if (con == null)
                        {
                            z = "no Internet";
                        }
                        else
                        {
                            String query = "select * from robots where type=" + type.toString();
                            Statement atmt = con.createStatement();
                            ResultSet rs = atmt.executeQuery(query);
                            if (rs.next())
                            {
                                z = "Yes!";
                                IsSuccsess = true;
                                con.close();
                            }
                            else {
                                z = "nope";
                                IsSuccsess = false;
                            }
                        }
                    }
                    catch (Exception ex){
                        IsSuccsess = false;
                        z = ex.getMessage();
                    }

                }
                return z;
            }
        }



    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        droid = getResources().getStringArray(R.array.droid);
    
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//Spinner
        selection = (TextView) findViewById(R.id.selection);

        Spinner spinner = (Spinner) findViewById(R.id.spinner); //1!!!!!!!!!!!!
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, droid);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                selection.setText(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_adress)
        {
            Intent intent = new Intent(this, CallHistory.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_payment)
        {
            Intent intent = new Intent(this,PaymentActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_contact_us) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            startActivity(emailIntent);

        }
        else if (id == R.id.nav_sing_in) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_bar_open:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    break;
            case R.id.btn_call_droid:
                Intent intent = new Intent(this, CallDroidActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));

        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

      /*  pinIcon.setIcon(
                "icon",
                ImageProvider.fromResource(this, R.drawable.icon),
                new IconStyle().setAnchor(new PointF(0f, 0f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(0f)
                        .setScale(1f)
        );*/

        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.search_result),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(1f)
                        .setScale(0.5f)
        );

        //  userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);
    }

    @Override
    public void onObjectRemoved(UserLocationView view) {
    }

    @Override
    public void onObjectUpdated(UserLocationView view, ObjectEvent event) {
    }

}




