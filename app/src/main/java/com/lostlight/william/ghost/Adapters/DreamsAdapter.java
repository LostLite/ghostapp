package com.lostlight.william.ghost.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostlight.william.ghost.R;
import com.lostlight.william.ghost.models.Dreams;

import java.util.List;

public class DreamsAdapter extends ArrayAdapter<Dreams> {

    private Context context;
    private List<Dreams> active_object;

    public DreamsAdapter(Context context, List<Dreams> options_list) {
        super(context, 0, options_list);
        // TODO Auto-generated constructor stub
        this.context = context;
        active_object = options_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // make sure we have a view to work with (may have been given null)
        View itemView = convertView;

        if(itemView == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            itemView = inflater.inflate(R.layout.list_dreams_view, parent, false);
        }

        //Find the list values to work with
        Dreams current_list_item = active_object.get(position);

        //Title
        TextView date = (TextView) itemView.findViewById(R.id.item_title);
        date.setText(current_list_item.getDate());

        //Description
        TextView description = (TextView) itemView.findViewById(R.id.item_description);
        description.setText(current_list_item.getDetails());

        //Description
        TextView tags = (TextView) itemView.findViewById(R.id.item_tags);
        //tags.setText(current_list_item.getDetails());

        return itemView;
    }

    @Override
    public int getCount() {
        return active_object.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }
}
