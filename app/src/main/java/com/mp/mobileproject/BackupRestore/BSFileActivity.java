package com.mp.mobileproject.BackupRestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mp.mobileproject.R;

import java.io.File;

public class BSFileActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;
    private Listitem_BSRecyclerAdpater recyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_s_file);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_BS);
        recyclerView.setLayoutManager(gridLayoutManager);


    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private BSFileActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final BSFileActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.OnLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if(child != null && clickListener != null && gestureDetector.onTouchEvent(motionEvent)){
                clickListener.OnClick(child, recyclerView.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }
    private void Search_File(String path){
        File root = new File(path);
        File[] files = root.listFiles();

        recyclerAdapter = new Listitem_BSRecyclerAdpater();
        recyclerAdapter.setContext(this);

        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory() == true){
                recyclerAdapter.addItem(new bs_listitem(files[i].getName(),"Folder", path,true));
            }
            else{
                recyclerAdapter.addItem(new bs_listitem(files[i].getName(),"File", path,false));
            }
        }
        recyclerView.setAdapter(recyclerAdapter);
    }

    public interface ClickListener{
        void OnClick(View view, int position);
        void OnLongClick(View view, int position);
    }
}