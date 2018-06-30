package com.lostlight.william.ghost.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lostlight.william.ghost.Adapters.DreamsAdapter;
import com.lostlight.william.ghost.Database.App;
import com.lostlight.william.ghost.R;
import com.lostlight.william.ghost.models.Dreams;

import java.util.ArrayList;
import java.util.List;

public class DreamsActivity extends AppCompatActivity {

    //Instantiate list option arrays
    public static String[] list_options = null;
    public static String[] list_option_desc = null;
    public static String[] list_option_tags = null;

    public static List<Dreams> DREAMS_LIST = new ArrayList<Dreams>();

    //instance of this activity
    public static Activity activity;

    //Dreams adapter
    static DreamsAdapter list_adapter;

    //Reference to the action bar
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreams);

        activity = this;

        //action bar features
        actionBar = getSupportActionBar();

        //populate the list with the dreams
        populateList();
        //register click call back for viewing more dream details
        registerClickCallback();
    }

    private void populateList(){
        //Get the list of dreams in a new thread
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                DREAMS_LIST = App.get().getDB(activity).dreamsDao().getAll();
            }
        }).start();

        if(DREAMS_LIST.isEmpty()){
            Toast.makeText(activity,"No dreams logged yet",Toast.LENGTH_LONG).show();
        }else{
            list_adapter = new DreamsAdapter(activity, DREAMS_LIST);
            ListView list = (ListView) findViewById(R.id._dream_list);
            list.setAdapter(list_adapter);
        }

    }

    private void registerClickCallback(){
        ListView list = (ListView) findViewById(R.id._dream_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position,
                                    long id) {
                //View My profile

                DreamDetailsActivity.SELECTED_DREAM = DREAMS_LIST.get(position);
                startActivity(new Intent(activity, DreamDetailsActivity.class));
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dreams, menu);

        return true;
    }

    //Capture first menu button click
    PopupMenu.OnMenuItemClickListener AddMenuItem = new PopupMenu.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(activity, "Settings menu item clicked",Toast.LENGTH_SHORT)
                    .show();

            return false;
        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.search_dreams:
                Toast.makeText(activity,"Search option goes here",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_dream_entry:
                startActivity(new Intent(getApplicationContext(), AddDreamActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        populateList();
    }
}
