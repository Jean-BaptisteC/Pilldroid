package net.foucry.pilldroid;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM;
import static net.foucry.pilldroid.utils.Constants.build;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import net.foucry.pilldroid.utils.Utils;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getName();

    private ActivityResultLauncher<String> requestNotificationPermission;
    private ViewPager2 viewPager;
    private LinearProgressIndicator progressIndicator;
    private int[] layouts;
    private MaterialButton btnSkip, btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        requestNotificationPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), this::storeNotificationPermissionResult);
        if (!prefManager.isUnderstood()) {
            firstStart();
        }
        if (!prefManager.isFirstTimeLaunch()) {
            finish();
        }
        prefManager.setFirstTimeLaunch(false);

        setContentView(R.layout.welcome_activity);

        setFullScreen();

        viewPager = findViewById(R.id.view_pager);
        progressIndicator = findViewById(R.id.progress_indicator);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome1,
                R.layout.welcome2,
                R.layout.welcome3,
                R.layout.welcome4,
                R.layout.welcome5,
                R.layout.welcome6,
                R.layout.welcome7,
                R.layout.welcome8,
                R.layout.welcome9,
                R.layout.welcome10,
                R.layout.welcome11};

        // making notification bar transparent
        changeStatusBarColor();
        Utils.changedNavigationBarColor(this);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == layouts.length - 1) {
                    btnNext.setText(getString(R.string.start));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }
                int progress = (int) (((position + 1f) / layouts.length) * 100);
                progressIndicator.setProgress(progress, true);
            }
        });
        btnSkip.setOnClickListener(v -> launchHomeScreen());

        btnNext.setOnClickListener(v -> {
            // checking for last page
            // if last page home screen will be launched
            int current = viewPager.getCurrentItem();
            if (current < layouts.length -1) {
                // move to next screen
                viewPager.setCurrentItem(current + 1, true);
            } else {
                launchHomeScreen();
            }
        });
    }

    private void firstStart() {
        if (build >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(POST_NOTIFICATIONS);
        }
        if (build >= Build.VERSION_CODES.S) {
            final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
            dialog.setMessage(R.string.schedule_option);
            dialog.setPositiveButton(R.string.yes, (v, id) -> startActivity(new Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
            dialog.setNegativeButton(R.string.no, (v, id)-> finish()
            );
            dialog.setCancelable(false);
            dialog.show();
        }
        askForComprehensive();
        prefManager.setUnderstood(true);
    }

    private void askForComprehensive() {
        final MaterialAlertDialogBuilder dlg = new MaterialAlertDialogBuilder(this);
        dlg.setMessage(getString(R.string.understood));
        dlg.setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
        dlg.setCancelable(false);
        dlg.show();
    }

    private int getItem() {
        return viewPager.getCurrentItem() + 1;
    }

    void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, DrugListActivity.class));
        finish();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void setFullScreen() {
        if (build >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends RecyclerView.Adapter<MyViewPagerAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layouts[viewType], parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Rien à binder car chaque page est un layout statique
        }

        @Override
        public int getItemCount() {
            return layouts.length;
        }

        @Override
        public int getItemViewType(int position) {
            return position; // Important pour utiliser layouts[position]
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    private void storeNotificationPermissionResult(boolean value) {
        if (value)
            Log.i(TAG, "Permission granted");
        else
            Log.w(TAG, "Permission refused");
    }
}
