package org.esiea.wychovaniak_arrighi.myapplication;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BeerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        GetBiersServices.startActionBiers(this);
        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);


        RecyclerView rv = ((RecyclerView) findViewById(R.id.rv_Biers));
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(new BiersAdapter());
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }


    public static final String BIERS_UPDATE = "org.esiea.bemat_gull.app_1.action.BIERS_UPDATE";
    public class BierUpdate extends BroadcastReceiver {
        public static final String TAG = "BierUpdate";

        @Override
        public void onReceive(Context context, Intent intent) {
            //  Log.d(TAG,getIntent().getAction());
            notificationTest();
        }


    }
    public void notificationTest() {
        NotificationCompat.Builder wat =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("DL")
                        .setContentText("bieres.json");
        NotificationManager manager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        manager.notify(1,wat.build());
    }



    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {
        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class BierHolder extends RecyclerView.ViewHolder {
            JSONArray biers;


            public  BierHolder(View v,JSONArray biers) {
                super(v);
                this.biers = biers;
            }
        }
    }
}