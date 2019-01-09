package com.example.marcela.reso.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marcela.reso.R;
import com.example.marcela.reso.models.CommentGetModel;

import java.util.List;

public class IssuesArrayAdapter extends ArrayAdapter<CommentGetModel> {
    private final Context context;
    private final List<CommentGetModel> values;

    public IssuesArrayAdapter(Context context,List<CommentGetModel> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_issue, parent, false);

        TextView creatorTextView = (TextView) rowView.findViewById(R.id.tv_row_issue_creator);
        TextView contentTextView = (TextView) rowView.findViewById(R.id.tv_row_issue_content);

        if(values == null || values.size() == 0)
        {
            return rowView;
        }

        creatorTextView.setText(values.get(position).Creator);
        contentTextView.setText(values.get(position).Content);
        return rowView;
    }
}
