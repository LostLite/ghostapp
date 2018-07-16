package com.lostlight.william.ghost.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lostlight.william.ghost.Database.App;
import com.lostlight.william.ghost.R;
import com.lostlight.william.ghost.models.DreamTags;
import com.lostlight.william.ghost.models.Dreams;
import com.lostlight.william.ghost.models.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AddDreamActivity extends AppCompatActivity implements View.OnClickListener{

    EditText dateEditText, detailsEditText, tagsEditText;
    ImageButton imgBtnCalendar;
    Button submitBtn;

    //instance of this activity
    public static Activity activity;

    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dream);

        activity = this;
        initViews();
    }

    void initViews(){
        //set tomorrow default date
        dateEditText = (EditText)findViewById(R.id.txtDreamDate);
        dateEditText.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-"
                        + (Calendar.getInstance().get(Calendar.MONTH) + 1)
                        + "-" + Calendar.getInstance().get(Calendar.YEAR));
        detailsEditText = (EditText)findViewById(R.id.txtDetails);
        tagsEditText = (EditText)findViewById(R.id.txtTags);
        //Buttons
        imgBtnCalendar = (ImageButton)findViewById(R.id.btnCalendar);
        imgBtnCalendar.setOnClickListener(this);

        submitBtn = (Button)findViewById(R.id.btn_submit);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Process to get Current Time
        if (v == imgBtnCalendar ) {

            final EditText selectedView = dateEditText;
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

        if(v == submitBtn){

            //submit form for processing
            processData();
        }
    }

    void processData(){
        if(dateEditText.getText().toString().trim().equals("")||
                detailsEditText.getText().toString().trim().equals("") ||
                tagsEditText.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),
                    "Provide all required details.",
                    Toast.LENGTH_LONG).show();
        }else{

            // run the sentence in a new thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Save dreams
                    Dreams newDream = new Dreams();
                    newDream.setDate(dateEditText.getText().toString().trim());
                    newDream.setDetails(detailsEditText.getText().toString().trim());

                    long dream_id = App.get().getDB(activity).dreamsDao().insertDream(newDream);

                    //update dream list in underlying activity
                    newDream.setId((int) dream_id);

                    //Save tags
                    List<String> tagsList = Arrays.asList(tagsEditText.getText().toString().trim().split(","));
                    for (String tag: tagsList) {
                        //check if tag exists
                        Tags thisTag = App.get().getDB(activity).tagsDao().findByName(tag);
                        long tag_id = 0;
                        if(thisTag == null){

                            Tags newTag = new Tags();
                            newTag.setTitle(tag);
                            //register a new tag and return the id
                            tag_id =  App.get().getDB(activity).tagsDao().insertTag(newTag);
                        }else{

                            //get the tag id
                            tag_id = thisTag.getId();
                        }
                        //Save dream tags
                        DreamTags newDreamTag = new DreamTags();
                        newDreamTag.setDreamId((int) dream_id);
                        newDreamTag.setTagId((int) tag_id);
                        App.get().getDB(activity).dreamTagsDao().insertDramTag(newDreamTag);
                    }

                    //Get the tags for new dream
                    newDream.setDreamTagsStr(activity, newDream.getId());

                    //Order dream list
                    DreamsActivity.DREAMS_LIST.add(newDream);
                    Collections.sort(DreamsActivity.DREAMS_LIST);


                }
            }).start();

            //Terminate activity and send an update alert to the calling activity
            Intent responseIntent = new Intent();
            responseIntent.putExtra("Update List",true);
            setResult(Activity.RESULT_OK,responseIntent);
            finish();

        }
    }
}
