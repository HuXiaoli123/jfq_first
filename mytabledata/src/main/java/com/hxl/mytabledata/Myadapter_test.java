package com.hxl.mytabledata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public  class Myadapter_test extends RecyclerView.Adapter<Myadapter_test.MyViewHolder> {

        Context context;
        List<String> list;

    Myadapter_test(Context context,List<String> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public Myadapter_test.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
            return new Myadapter_test.MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onBindViewHolder(Myadapter_test.MyViewHolder holder, int position) {
            holder.tv.setText(list.get(position));

        }

        public void addData(int position) {
            list.add(position, "Insert One");
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                /*tv = (TextView) view.findViewById(R.id.load_tv);*/
            }
        }
    }
