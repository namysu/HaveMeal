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

public class MainFoodAdapter extends BaseAdapter {
    private static final String TAG = "MainFoodAdapter";

    private Context mContext;

    private ArrayList<HashMap<String, String>> foodDataArrayList;

    public MainFoodAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.mContext = context;
        this.foodDataArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return foodDataArrayList.size();
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
            view = inflater.inflate(R.layout.item_food_list, null);

            FoodViewHolder viewHolder = new FoodViewHolder();
            viewHolder.date = (TextView) view.findViewById(R.id.food_date);
            viewHolder.detail = (TextView) view.findViewById(R.id.food_detail);
            viewHolder.kcal = (TextView) view.findViewById(R.id.food_kcal);

            view.setTag(viewHolder);
        }

        FoodViewHolder viewHolder = (FoodViewHolder) view.getTag();

        viewHolder.date.setText(foodDataArrayList.get(position).get("date"));
        viewHolder.detail.setText(foodDataArrayList.get(position).get("detail"));
        viewHolder.kcal.setText(foodDataArrayList.get(position).get("kcal"));

        return view;
    }

    private static class FoodViewHolder {
        TextView date;
        TextView detail;
        TextView kcal;
    }
}
