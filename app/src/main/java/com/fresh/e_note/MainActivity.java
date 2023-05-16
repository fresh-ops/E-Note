package com.fresh.e_note;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.fresh.e_note.fragments.notes_fragment.NotesFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawer;
    private AppBarConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        configuration = new AppBarConfiguration.Builder(
                R.id.nav_schedule, R.id.nav_tasks, R.id.nav_notes)
                .setOpenableLayout(drawer)
                .build();

        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment_container_main);
        NavigationUI.setupActionBarWithNavController(this, controller, configuration);
        NavigationUI.setupWithNavController(navigationView, controller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container_main);
        return NavigationUI.navigateUp(navController, configuration)
                || super.onSupportNavigateUp();
    }
}