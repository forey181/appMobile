package com.example.jamesforey.mobileappscw2;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


public class GridListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Attendees> arrayList;
    private LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;

    public GridListAdapter(Context context, ArrayList<Attendees> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_check_box, viewGroup, false);
            viewHolder.checkBox = view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));
        viewHolder.checkBox.setText(arrayList.get(i).getAttendeeName());
        //when the check box is clicked, update mSelectedItemIds
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
            }
        });

        return view;
    }

    //a class to save all the component in the list_view_check_box.xml
    private class ViewHolder {
        private CheckBox checkBox;
    }

    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}

