package com.lostlight.william.ghost.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.lostlight.william.ghost.Adapters.DreamsAdapter;
import com.lostlight.william.ghost.Database.App;
import com.lostlight.william.ghost.R;
import com.lostlight.william.ghost.models.Dreams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DreamsActivity extends AppCompatActivity implements View.OnClickListener{

    public static List<Dreams> DREAMS_LIST = new ArrayList<Dreams>();
    private ListView dreamListView;
    ImageButton imgBtnCalendar;
    EditText inputSearch;

    private int mYear, mMonth, mDay;

    //instance of this activity
    public static Activity activity;

    //Dreams adapter
    static DreamsAdapter LIST_ADAPTER;

    //Reference to the action bar
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreams);

        activity = this;

        //action bar features
        actionBar = getSupportActionBar();

        //initialize views
        initViews();
        //populate the list with the dreams
        populateList();
        //register click call back for viewing more dream details
        registerClickCallback();
    }

    void initViews(){
        dreamListView = (ListView) findViewById(R.id._dream_list);

        //Edit textview
        inputSearch = findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LIST_ADAPTER.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Buttons
        imgBtnCalendar = findViewById(R.id.btnCalendar);
        imgBtnCalendar.setOnClickListener(this);
    }

    private void populateList(){
        //Get the list of dreams in a new thread
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                DREAMS_LIST = App.get().getDB(activity).dreamsDao().getAll();

                //Order dream list
                Collections.sort(DREAMS_LIST);

                //Set tags for each dream
                for (Dreams dream: DREAMS_LIST){
                    dream.setDreamTagsStr(activity, dream.getId());
                }

                //initialize the adapter
                LIST_ADAPTER = new DreamsAdapter(activity, DREAMS_LIST);

                if(DREAMS_LIST.isEmpty())
                    Toast.makeText(activity,"No dreams logged yet",Toast.LENGTH_LONG).show();
                dreamListView.setAdapter(LIST_ADAPTER);
            }
        }).start();

    }

    private void registerClickCallback(){
        ListView list = findViewById(R.id._dream_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position,
                                    long id) {

                DreamDetailsActivity.SELECTED_DREAM = DREAMS_LIST.get(position);
                startActivity(new Intent(activity, DreamDetailsActivity.class));
            }

        });
    }

    @Override
    public void onClick(View v) {
        // Process to get Current Time
        if (v == imgBtnCalendar ) {

            final EditText selectedView = inputSearch;
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(activity,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            selectedView.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }

                    }, mYear, mMonth, mDay);
            dpd.show();
        }
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
            case R.id.add_dream_entry:
                startActivityForResult(new Intent(getApplicationContext(), AddDreamActivity.class),200);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (200) : {
                if (resultCode == Activity.RESULT_OK) {
                    boolean updateList = data.getBooleanExtra("Update List",false);
                    // TODO Update your TextView.
                    if(updateList){
                        Toast.makeText(activity,"Updating list",Toast.LENGTH_LONG).show();
                        LIST_ADAPTER.notifyDataSetChanged();
                    }else{
                        Toast.makeText(activity,"Not updating list",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        }
    }
}
