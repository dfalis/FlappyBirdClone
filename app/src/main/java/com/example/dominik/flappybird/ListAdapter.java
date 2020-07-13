package com.example.dominik.flappybird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<DBScore> {
    private Context context;
    private ArrayList<DBScore> scores;
    private int layoutResourceId;

    public ListAdapter(Context context, int layoutResourceId, ArrayList<DBScore> scores) {
        super(context, layoutResourceId, scores);

        this.context = context;
        this.scores = scores;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(this.layoutResourceId, parent, false);

        TextView number = rowView.findViewById(R.id.number);
        number.setText((position+1)+".");

        TextView textView = rowView.findViewById(R.id.score);
        textView.setText(scores.get(position).score+"");

        return rowView;
    }
}