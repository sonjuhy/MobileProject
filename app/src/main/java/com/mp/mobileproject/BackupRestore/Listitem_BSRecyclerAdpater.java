package com.mp.mobileproject.BackupRestore;

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

public class Listitem_BSRecyclerAdpater extends RecyclerView.Adapter<Listitem_BSRecyclerAdpater.ItemViewHolder> {

    private ArrayList<bs_listitem> listData = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public Listitem_BSRecyclerAdpater.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_listitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Listitem_BSRecyclerAdpater.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public Object getItem(int position){
        return listData.get(position);
    }
    public void addItem(bs_listitem listItem){
        listData.add(listItem);
    }
    public void setContext(Context context){
        this.context = context;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_name;
        private TextView textView_size;
        private ImageView imageView;
        private bs_listitem listItem;

        private int position;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.bs_listitem_name);
            textView_size = itemView.findViewById(R.id.bs_listitem_size);
            imageView = itemView.findViewById(R.id.imageView);
        }
        public void onBind(bs_listitem listItem, int position){
            this.listItem = listItem;
            this.position = position;

            textView_size.setText(listItem.getSize());
            textView_name.setText(listItem.getName());

            if(listItem.getType()){//folder

            }
            else{//file
                imageView.setImageResource(R.drawable.file240);
            }
        }
    }
}
