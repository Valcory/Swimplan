package se.valcory.swimplan.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import se.valcory.swimplan.R;
import se.valcory.swimplan.adapter.ExerListAdapter;
import se.valcory.swimplan.db.ExerciseDAO;
import se.valcory.swimplan.to.Exercise;

public class ExerciseListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "exercise_list";

    Activity activity;
    ListView exerciseListView;
    ArrayList<Exercise> exercises;

    ExerListAdapter exerciseListAdapter;
    ExerciseDAO exerciseDAO;

    private GetExerTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        exerciseDAO = new ExerciseDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exer_list, container,
                false);
        findViewsById(view);

        task = new GetExerTask(activity);
        task.execute((Void) null);

        exerciseListView.setOnItemClickListener(this);
        exerciseListView.setOnItemLongClickListener(this);
        return view;
    }

    private void findViewsById(View view) {
        exerciseListView = (ListView) view.findViewById(R.id.list_exer);
    }

    @Override
    public void onItemClick(AdapterView<?> list, View view, int position,
                            long id) {
        Exercise exercise = (Exercise) list.getItemAtPosition(position);

        if (exercise != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedExercise", exercise);
            CustomExerDialogFragment customExerDialogFragment = new CustomExerDialogFragment();
            customExerDialogFragment.setArguments(arguments);
            customExerDialogFragment.show(getFragmentManager(),
                    CustomExerDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        Exercise exercise = (Exercise) parent.getItemAtPosition(position);
        // Use AsyncTask to delete from database
        exerciseDAO.deleteExercise(exercise);
        exerciseListAdapter.remove(exercise);

        return true;
    }

    public class GetExerTask extends AsyncTask<Void, Void, ArrayList<Exercise>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetExerTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Exercise> doInBackground(Void... arg0) {
            ArrayList<Exercise> exerciseList = exerciseDAO.getExercises();
            return exerciseList;
        }

        @Override
        protected void onPostExecute(ArrayList<Exercise> exerList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                exercises = exerList;
                if (exerList != null) {
                    if (exerList.size() != 0) {
                        exerciseListAdapter = new ExerListAdapter(activity,
                                exerList);
                        exerciseListView.setAdapter(exerciseListAdapter);
                    } else {
                        alertView("Du kan lägga till övningar via menyn uppe till höger.");
                    }
                }
            }
        }
    }


    public void updateView() {
        task = new GetExerTask(activity);
        task.execute((Void) null);
    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle( "Välkommen till Swimplan!" )
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();

    }
    @Override
    public void onResume() {
        //Fixar set title efter onResume. De under ger null pointer exception.
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        //getActivity().setTitle(R.string.app_name);
        //getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }
}