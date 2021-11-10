package com.khacngoc.myproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
public class mActivityAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<mActivity> list;

    public mActivityAdapter(MainActivity context, int layout, List<mActivity> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView imageviewRemove, imageviewRepair;
        TextView tvTittle, tvText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);

            viewHolder.tvTittle = convertView.findViewById(R.id.tvTittle);
            viewHolder.tvText = convertView.findViewById(R.id.tvText);
            viewHolder.imageviewRemove = convertView.findViewById(R.id.imageviewRemove);
            viewHolder.imageviewRepair = convertView.findViewById(R.id.imageviewRepair);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mActivity mActivities = list.get(position);

        viewHolder.tvTittle.setText(mActivities.getTittle());
        viewHolder.tvText.setText(mActivities.getText());

        viewHolder.imageviewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.XoaCongViec(mActivities.getTittle(), mActivities.getId());
            }
        });

        return convertView;
    }
}
