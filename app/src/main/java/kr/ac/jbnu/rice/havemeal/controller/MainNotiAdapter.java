package kr.ac.jbnu.rice.havemeal.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.jbnu.rice.havemeal.R;

public class MainNotiAdapter extends BaseAdapter {
    private static final String TAG = "MainNotiAdapter";

    private Context mContext;

    private ArrayList<HashMap<String, String>> notiDataArrayList;

    public MainNotiAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.mContext = context;
        this.notiDataArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return notiDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_noti_list, null);

            NotiViewHolder viewHolder = new NotiViewHolder();
            viewHolder.desc = (TextView) view.findViewById(R.id.noti_desc);
            viewHolder.user = (TextView) view.findViewById(R.id.noti_user);

            view.setTag(viewHolder);
        }

        NotiViewHolder viewHolder = (NotiViewHolder) view.getTag();

        viewHolder.desc.setText(notiDataArrayList.get(position).get("desc"));
        viewHolder.user.setText(notiDataArrayList.get(position).get("user"));

        return view;
    }

    private static class NotiViewHolder {
        TextView desc;
        TextView user;
    }
}
