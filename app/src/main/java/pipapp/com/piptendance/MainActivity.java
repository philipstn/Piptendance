package pipapp.com.piptendance;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractCollection;

import pipapp.com.piptendance.Fragment.AbsenceFragment;
import pipapp.com.piptendance.Fragment.ReviewFragment;
import pipapp.com.piptendance.Model.User;

public class MainActivity extends AppCompatActivity {
    DrawerLayout dl;
    NavigationView nv;
    android.support.v7.widget.Toolbar toolbar;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    TextView Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = findViewById(R.id.dl);
        nv = findViewById(R.id.nv);
        View view = nv.getHeaderView(0);
        Username = view.findViewById(R.id.tvUsername);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String uid = mAuth.getUid();

        mDatabase.child("User").child(uid).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nama = String.valueOf(dataSnapshot.getValue());
                Username.setText(nama);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // set item as selected to persist highlight
                item.setChecked(true);
                // close drawer when item is tapped
                dl.closeDrawers();
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnLogout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void btnAbsence(MenuItem item) {
        setTitle("Absence");
        AbsenceFragment absenceFragment = new AbsenceFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, absenceFragment).commit();
    }

    public void btnReview(MenuItem item) {
        setTitle("Review");
        ReviewFragment reviewFragment = new ReviewFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, reviewFragment).commit();
    }
}
