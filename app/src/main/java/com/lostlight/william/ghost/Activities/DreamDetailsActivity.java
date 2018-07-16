package com.lostlight.william.ghost.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lostlight.william.ghost.R;
import com.lostlight.william.ghost.models.Dreams;

public class DreamDetailsActivity extends AppCompatActivity {

    public static Dreams SELECTED_DREAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_details);

        //Set dream date
        TextView date = (TextView)findViewById(R.id.txtDate);
        date.setText(SELECTED_DREAM.getDate());

        //set dream details
        TextView details = (TextView)findViewById(R.id.txtDreamDetails);
        details.setText(SELECTED_DREAM.getDetails());

        TextView tags = (TextView)findViewById(R.id.txtDreamTags);
        tags.setText(SELECTED_DREAM.getDreamTagsStr());
    }
}
