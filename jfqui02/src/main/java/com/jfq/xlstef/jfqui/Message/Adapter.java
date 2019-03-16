package com.jfq.xlstef.jfqui.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.Tools.DataBaseUtil.ListInfo;

import java.util.List;

/**
 *
 */
public class Adapter extends BaseAdapter{
    private List<ListInfo> list;
    private Context context;

    public Adapter(List<ListInfo> list, Context context){
        this.list=list;
        this.context=context;
    }

    public void onDateChange(List<ListInfo> list) {
        this.list = list;
        //当有数据变化，会自动刷新界面
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(holder==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            holder=new ViewHolder();

            holder.iv_icon01= (ImageView) convertView.findViewById(R.id.iv_icon01);
           // holder.iv_icon02= (ImageView) convertView.findViewById(R.id.iv_icon02);
            holder.tv_timer= (TextView) convertView.findViewById(R.id.tv_timer);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }


       String Name="jfqpic";

        //getIdentifier(资源名称,获取资源位置,资源的包名)
        int imgId01=context.getResources().getIdentifier(Name,"drawable",context.getPackageName());
       // int imageId02=context.getResources().getIdentifier(Name,"drawable",context.getPackageName());

        holder.iv_icon01.setImageResource(imgId01);
      // holder.iv_icon02.setImageResource(imageId02);
        holder.tv_timer.setText(list.get(position).getTimer());
        holder.tv_content.setText(list.get(position).getContent());
        return convertView;
    }


    static class ViewHolder{
        ImageView iv_icon01,iv_icon02;//左侧--图片
        TextView tv_timer,tv_content;//右侧--时间内容
    }

    /**
     * 添加列表项
     * @param listInfo
     */
    public void addItem(ListInfo listInfo) {
        list.add(listInfo);
    }


}
