package com.example.ahmed.backendexamples;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahmed on 4/8/2018.
 */

public class UserRecyclerAdapter extends  RecyclerView.Adapter<UserRecyclerAdapter.MyViewHolder> {
    ArrayList<User> list ;

    // Constructor with the DataSource ArrayList as a parameter
    UserRecyclerAdapter(ArrayList<User> list){

        this.list = list;
    }


    @Override
    public UserRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflating the Layout you designed for the single cell in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recycler_view_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserRecyclerAdapter.MyViewHolder holder, int position) {
        // getting the corresponding object in the arrayList to the position of each cell in the recyclerView
        // and passing it to the bindData() method
        User data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView textView ;
        //in the constructor we initiate the designed cell view elements using the passed View object parameter
        public MyViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView2) ;
        }
        //binding data of each object to the cell's view elements
        public void bindData( User obj){
            textView.setText(obj.getId()+" "+obj.getFirstName()+" "+obj.getLastName());
        }
    }

}
