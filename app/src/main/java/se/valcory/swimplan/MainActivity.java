package se.valcory.swimplan;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import se.valcory.swimplan.db.SwimmingStyleDAO;
import se.valcory.swimplan.fragment.CustomExerDialogFragment.CustomExerDialogFragmentListener;
import se.valcory.swimplan.fragment.ExerciseAddFragment;
import se.valcory.swimplan.fragment.ExerciseListFragment;


public class MainActivity extends AppCompatActivity implements CustomExerDialogFragmentListener {

    private Fragment contentFragment;
    private ExerciseListFragment exerciseListFragment;
    private ExerciseAddFragment exerciseAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        SwimmingStyleDAO swstDAO = new SwimmingStyleDAO(this);


        if(swstDAO.getSwimmingStyles().size() <= 0)
            swstDAO.loadSwimmingStyles();


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(ExerciseAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(ExerciseAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_exer);
                        contentFragment = fragmentManager
                                .findFragmentByTag(ExerciseAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(ExerciseListFragment.ARG_ITEM_ID) != null) {
                exerciseListFragment = (ExerciseListFragment) fragmentManager
                        .findFragmentByTag(ExerciseListFragment.ARG_ITEM_ID);
                contentFragment = exerciseListFragment;
            }
        } else {
            exerciseListFragment = new ExerciseListFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(exerciseListFragment, ExerciseListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle(R.string.add_exer);
                exerciseAddFragment = new ExerciseAddFragment();
                switchContent(exerciseAddFragment, ExerciseAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof ExerciseAddFragment) {
            outState.putString("content", ExerciseAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", ExerciseListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            if (!(fragment instanceof ExerciseListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getSupportActionBar().setTitle(resourseId);

    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof ExerciseListFragment
                || fm.getBackStackEntryCount() == 0) {
            //finish();
            //Shows an alert dialog on quit
            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage("Do You Want To Quit?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }


    @Override
    public void onFinishDialog() {
        if (exerciseListFragment != null) {
            exerciseListFragment.updateView();
        }
    }
}
