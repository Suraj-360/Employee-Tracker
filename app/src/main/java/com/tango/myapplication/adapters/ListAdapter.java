package com.tango.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tango.myapplication.R;
import com.tango.myapplication.models.UserModel;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<UserModel>{

    private ArrayList<UserModel> userModels;
    Context mContext;

    // View lookup cache
    private static class ViewHolder
    {
        // Item assign
        TextView empID;
        TextView empName;
        TextView empEmail;
        TextView empPhone;
        TextView empLocation;
    }

    public ListAdapter(ArrayList<UserModel> data, Context context)
    {
        super(context, R.layout.litem_view, data);
        this.userModels = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserModel userModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.litem_view, parent, false);

            // Item assign
            viewHolder.empID = convertView.findViewById(R.id.itemEmpID);
            viewHolder.empName = convertView.findViewById(R.id.itemEmpName);
            viewHolder.empEmail = convertView.findViewById(R.id.itemEmpEmail);
            viewHolder.empPhone = convertView.findViewById(R.id.itemEmpPhone);
            viewHolder.empLocation = convertView.findViewById(R.id.itemEmpLocation);
            result=convertView;

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.empEmail.setText(String.format("Employee Email :- %s", userModel.getEmail()));
        viewHolder.empName.setText(String.format("Employee Name :- %s", userModel.getUsername()));
        viewHolder.empPhone.setText(String.format("Employee Phone :- %s", userModel.getPhone()));
        viewHolder.empID.setText(String.format("Employee ID :- %s", userModel.getEmpID()));
        viewHolder.empLocation.setText(String.format("Employee Location :- %s", userModel.getLocation()));

        // Return the completed view to render on screen
        return convertView;
    }
}
