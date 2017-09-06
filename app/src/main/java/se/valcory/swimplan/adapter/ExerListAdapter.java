package se.valcory.swimplan.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import se.valcory.swimplan.R;
import se.valcory.swimplan.to.Exercise;

public class ExerListAdapter extends ArrayAdapter<Exercise>{

    private Context context;
    List<Exercise> exercises;

    public ExerListAdapter(Context context, List<Exercise> exercises) {
        super(context, R.layout.list_item, exercises);
        this.context = context;
        this.exercises = exercises;
    }

    private class ViewHolder {
        TextView empIdTxt;
        TextView empNameTxt;
        TextView exerDistanceTxt;
        TextView exerRepetitionTxt;
        TextView empDeptNameTxt;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Exercise getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.empIdTxt = (TextView) convertView
                    .findViewById(R.id.txt_exer_id);
            holder.empNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_exer_comment);
            holder.exerDistanceTxt = (TextView) convertView
                    .findViewById(R.id.txt_exer_distance);
            holder.exerRepetitionTxt = (TextView) convertView
                    .findViewById(R.id.txt_exer_repetition);
            holder.empDeptNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_exer_swst);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Exercise exercise = (Exercise) getItem(position);
        holder.empIdTxt.setText(exercise.getId() + "");
        holder.empNameTxt.setText(exercise.getName());
        holder.exerDistanceTxt.setText(exercise.getDistance() + "");
        holder.exerRepetitionTxt.setText(exercise.getRepetition() + "");
        holder.empDeptNameTxt.setText(exercise.getSwimmingStyle().getName());

        return convertView;
    }

    @Override
    public void add(Exercise exercise) {
        exercises.add(exercise);
        notifyDataSetChanged();
        super.add(exercise);
    }

    @Override
    public void remove(Exercise exercise) {
        exercises.remove(exercise);
        notifyDataSetChanged();
        super.remove(exercise);
    }
}
