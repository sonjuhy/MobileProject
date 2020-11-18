package com.mp.mobileproject.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp.mobileproject.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListItem_RecyclerAdapter extends RecyclerView.Adapter<ListItem_RecyclerAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Listitem_calendar> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_calendar, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        System.out.println("Setting Adapter onBind viewer : "+listData.get(0).GetName());
        holder.onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    public Object getItem(int posision){
        return listData.get(posision);
    }
    public void addItem(Listitem_calendar listItem){
        listData.add(listItem);
    }
    public void setContext(Context context){
        this.context = context;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_name;
        private  ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.ListItem_name);
            imageView = itemView.findViewById(R.id.ListItem_image);
        }
        public void onBind(Listitem_calendar listItem_input, int position_input){
            textView_name.setText(listItem_input.GetName());
            if(listItem_input.GetType() == 1){
                imageView.setImageResource(R.drawable.calendar_public);
            }
            System.out.println("setting recycler view item name : "+listItem_input.GetName());
        }
    }
}
