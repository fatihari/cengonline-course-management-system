package com.example.cengonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cengonline.adapter.AssignmentAdapter;
import com.example.cengonline.adapter.CourseAdapter;
import com.example.cengonline.course.CreateAssignmentDialog;
import com.example.cengonline.model.Announcement;
import com.example.cengonline.model.Assignment;
import com.example.cengonline.model.Course;
import com.example.cengonline.model.StudentListOfTheCourses;
import com.example.cengonline.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView createAssignment;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> assignmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_assignments);

        //Important open user page
       /* Bundle b = getIntent().getExtras();
        assert b != null;
        courseId = b.getString("courseId");*/


        ////Important open user page

        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        //Course Activity page select
        bottomNavigationView.setSelectedItemId(R.id.nav_assignments);
        //Switch to other pages
        switchPage();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        createAssignment = findViewById(R.id.txt_createAssignment);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            if(user.getType().equals("Student"))
            {
                createAssignment.setVisibility(View.GONE);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        createAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignmentDialog();
            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_assignment);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.vertical_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        assert verticalDivider != null;
        dividerItemDecoration.setDrawable(verticalDivider);
        recyclerView.addItemDecoration(dividerItemDecoration);

        assignmentList = new ArrayList<>();
        assignmentAdapter = new AssignmentAdapter(this,assignmentList);
        recyclerView.setAdapter(assignmentAdapter);
        assignmentAdapter.notifyDataSetChanged();
        readAssignments();

    }

    //for Students
    private void readAssignments()
    {
        DatabaseReference refAssignment = FirebaseDatabase.getInstance()
                .getReference("Assignments").child(currentUser.getUid());
        refAssignment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assignmentList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Assignment assignment = snapshot.getValue(Assignment.class);
                    assignmentList.add(assignment);
                }
                assignmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void openAssignmentDialog()
    {
        /*CreateAssignmentDialog createCourseDialog = new CreateAssignmentDialog();
        createCourseDialog.show(getSupportFragmentManager(),"Create Assignment");*/
    }

    private void switchPage(){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_assignments:
                        return true;


                    case R.id.nav_courses:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_messages:
                        Intent intentMessage = new Intent(getApplicationContext(),MessagesActivity.class);
                        startActivity(intentMessage);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_announcements:
                        Intent intentAnnounce = new Intent(getApplicationContext(),AnnouncementsActivity.class);
                        startActivity(intentAnnounce);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_profile:
                        Intent intentProfile = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intentProfile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
