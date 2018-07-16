package com.lostlight.william.ghost.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.lostlight.william.ghost.R;
import com.lostlight.william.ghost.models.Dreams;

import java.util.ArrayList;
import java.util.List;

public class DreamsAdapter extends ArrayAdapter<Dreams> {

    private Context context;
    private List<Dreams> active_object;
    CustomFilter filter;
    List<Dreams> filterList;

    public DreamsAdapter(Context context, List<Dreams> options_list) {
        super(context, 0, options_list);
        // TODO Auto-generated constructor stub
        this.context = context;
        active_object = options_list;
        filterList = options_list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // make sure we have a view to work with (may have been given null)
        View itemView = convertView;

        if(itemView == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            itemView = inflater.inflate(R.layout.list_dreams_view, parent, false);
        }

        //Find the list values to work with
        final Dreams current_list_item = active_object.get(position);

        //Title
        TextView date = itemView.findViewById(R.id.item_title);
        date.setText(current_list_item.getDate());

        //Description
        TextView description = itemView.findViewById(R.id.item_description);
        description.setText(current_list_item.getDetails());

        //Description
        TextView tags = itemView.findViewById(R.id.item_tags);
        tags.setText(current_list_item.getDreamTagsStr());

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

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    private class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTRAINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                List<Dreams> filters=new ArrayList<>();

                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getDreamTagsStr().toUpperCase().contains(constraint))
                    {
                        //Add dream object to filter list
                        filters.add(filterList.get(i));
                    }else{
                        //check if it is a date
                        if(filterList.get(i).getDate().contains(constraint)){
                            //Add dream object to filter list
                            filters.add(filterList.get(i));
                        }
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;

            }

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            active_object = (List<Dreams>) results.values;
            notifyDataSetChanged();
        }
    }
}
