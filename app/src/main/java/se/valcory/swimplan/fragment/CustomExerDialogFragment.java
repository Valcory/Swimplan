package se.valcory.swimplan.fragment;

        import java.util.List;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.NumberPicker;
        import android.widget.Spinner;
        import android.widget.Toast;

        import se.valcory.swimplan.MainActivity;
        import se.valcory.swimplan.R;
        import se.valcory.swimplan.db.SwimmingStyleDAO;
        import se.valcory.swimplan.db.ExerciseDAO;
        import se.valcory.swimplan.to.SwimmingStyle;
        import se.valcory.swimplan.to.Exercise;

public class CustomExerDialogFragment extends DialogFragment {

    // UI references
    private EditText exerNameEtxt;
    private NumberPicker exerRepetitionNp;
    private NumberPicker exerDistanceNp;
    private Spinner swstSpinner;
    private LinearLayout submitLayout;
    private Exercise exercise;

    ExerciseDAO exerciseDAO;
    ArrayAdapter<SwimmingStyle> adapter;

    public static final String ARG_ITEM_ID = "exer_dialog_fragment";

    public interface CustomExerDialogFragmentListener {
        void onFinishDialog();
    }

    public CustomExerDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        exerciseDAO = new ExerciseDAO(getActivity());
        Bundle bundle = this.getArguments();
        exercise = bundle.getParcelable("selectedExercise");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_exer,
                null);
        builder.setView(customDialogView);

        exerNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_comment);
        exerRepetitionNp = (NumberPicker) customDialogView
                .findViewById(R.id.np_repetition);
        exerDistanceNp = (NumberPicker) customDialogView
                .findViewById(R.id.np_distance);
        swstSpinner = (Spinner) customDialogView
                .findViewById(R.id.spinner_swst);
        submitLayout = (LinearLayout) customDialogView
                .findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);

        exerRepetitionNp.setMinValue(1);
        exerRepetitionNp.setMaxValue(20);
        exerRepetitionNp.setWrapSelectorWheel(false);
        final String[] distances= {"25", "50", "75", "100", "125", "150", "175", "200", "225",
                "250", "275", "300", "325", "350", "375", "400"};
        exerDistanceNp.setMinValue(0);
        exerDistanceNp.setMaxValue(distances.length-1);
        exerDistanceNp.setDisplayedValues(distances);
        exerDistanceNp.setWrapSelectorWheel(false);

        setValue();

        builder.setTitle(R.string.update_exer);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        exercise.setName(exerNameEtxt.getText().toString());
                        exercise.setRepetition(exerRepetitionNp.getValue());
                        exercise.setDistance((exerDistanceNp.getValue()+1)*25);
                        SwimmingStyle swst = (SwimmingStyle) adapter
                                .getItem(swstSpinner.getSelectedItemPosition());
                        exercise.setSwimmingStyle(swst);
                        long result = exerciseDAO.update(exercise);
                        if (result > 0) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update exercise",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        SwimmingStyleDAO swimmingStyleDAO = new SwimmingStyleDAO(getActivity());
        List<SwimmingStyle> swimmingStyles = swimmingStyleDAO.getSwimmingStyles();
        adapter = new ArrayAdapter<SwimmingStyle>(getActivity(),
                android.R.layout.simple_list_item_1, swimmingStyles);
        swstSpinner.setAdapter(adapter);
        int pos = adapter.getPosition(exercise.getSwimmingStyle());

        if (exercise != null) {
            exerNameEtxt.setText(exercise.getName());
            exerRepetitionNp.setValue(exercise.getRepetition());
            exerDistanceNp.setValue((exercise.getDistance()-1)/25);
            swstSpinner.setSelection(pos);
        }
    }
}