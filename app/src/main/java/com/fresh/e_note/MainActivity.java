package com.fresh.e_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final int SCHEDULE_FRAGMENT_IND = 0;
    private final int TASKS_FRAGMENT_IND = 1;
    private final int NOTES_FRAGMENT_IND = 2;

    private AppBarConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        configuration = new AppBarConfiguration.Builder(
                R.id.nav_schedule, R.id.nav_tasks, R.id.nav_notes)
                .setOpenableLayout(drawer)
                .build();

        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment_container_main);
        NavigationUI.setupActionBarWithNavController(this, controller, configuration);
        NavigationUI.setupWithNavController(navigationView, controller);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container_main);
        return NavigationUI.navigateUp(navController, configuration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (getCurrentFragmentInd()) {
            case SCHEDULE_FRAGMENT_IND:
            case TASKS_FRAGMENT_IND:
                intent = new Intent(this, TaskEditActivity.class);
                break;
            case NOTES_FRAGMENT_IND:
                intent = new Intent(this, NoteEditActivity.class);
                break;
        }
        startActivity(intent);
    }

    private int getCurrentFragmentInd() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isChecked()) return i;
        }

        return -1;
    }
}