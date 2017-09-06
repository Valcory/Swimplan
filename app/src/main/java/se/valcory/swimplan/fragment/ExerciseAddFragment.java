package se.valcory.swimplan.fragment;

import java.lang.ref.WeakReference;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import se.valcory.swimplan.R;
import se.valcory.swimplan.db.SwimmingStyleDAO;
import se.valcory.swimplan.db.ExerciseDAO;
import se.valcory.swimplan.to.SwimmingStyle;
import se.valcory.swimplan.to.Exercise;


public class ExerciseAddFragment extends Fragment implements OnClickListener {

    // UI references
    private EditText empNameEtxt;
    private EditText exerDistanceEtxt;
    private EditText exerRepetitionEtxt;
    private Spinner swstSpinner;
    private Button addButton;
    private Button resetButton;






    Exercise exercise = null;
    private ExerciseDAO exerciseDAO;
    private SwimmingStyleDAO swimmingStyleDAO;
    private GetSwstTask task;
    private AddExerTask addExerTask;

    public static final String ARG_ITEM_ID = "exer_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exerciseDAO = new ExerciseDAO(getActivity());
        swimmingStyleDAO = new SwimmingStyleDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_exer, container,
                false);

        findViewsById(rootView);

        setListeners();

        task = new GetSwstTask(getActivity());
        task.execute((Void) null);

        return rootView;
    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        empNameEtxt.setText("");
        exerDistanceEtxt.setText("");
        exerRepetitionEtxt.setText("");
        if (swstSpinner.getAdapter().getCount() > 0)
            swstSpinner.setSelection(0);
    }

    private void setExercise() {
        exercise = new Exercise();
        exercise.setName(empNameEtxt.getText().toString());
        exercise.setDistance(Double.parseDouble(exerDistanceEtxt.getText()
                .toString()));
        exercise.setRepetition(Integer.parseInt(exerRepetitionEtxt.getText()
                .toString()));
        SwimmingStyle selectedSwst = (SwimmingStyle) swstSpinner.getSelectedItem();
        exercise.setSwimmingStyle(selectedSwst);
    }

    @Override
    public void onResume() {
        //getActivity().setTitle(R.string.add_exer);
        //getActivity().getActionBar().setTitle(R.string.add_exer);
        super.onResume();
    }

    private void findViewsById(View rootView) {
        empNameEtxt = (EditText) rootView.findViewById(R.id.etxt_comment);
        exerDistanceEtxt = (EditText) rootView.findViewById(R.id.etxt_distance);
        exerRepetitionEtxt = (EditText) rootView.findViewById(R.id.etxt_repetition);
        swstSpinner = (Spinner) rootView.findViewById(R.id.spinner_swst);
        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
            setExercise();
            addExerTask = new AddExerTask(getActivity());
            addExerTask.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class GetSwstTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<Activity> activityWeakRef;
        private List<SwimmingStyle> swimmingStyles;

        public GetSwstTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            swimmingStyles = swimmingStyleDAO.getSwimmingStyles();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {

                ArrayAdapter<SwimmingStyle> adapter = new ArrayAdapter<SwimmingStyle>(
                        activityWeakRef.get(),
                        android.R.layout.simple_list_item_1, swimmingStyles);
                swstSpinner.setAdapter(adapter);

                addButton.setEnabled(true);
            }
        }
    }

    public class AddExerTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddExerTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = exerciseDAO.save(exercise);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Övning sparad",
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}
