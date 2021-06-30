package com.example.cengonline.course;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cengonline.AssignmentsActivity;
import com.example.cengonline.R;
import com.example.cengonline.model.Course;
import com.example.cengonline.model.StudentListOfTheCourses;
import com.example.cengonline.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CreateAssignmentDialog extends AppCompatDialogFragment {

    private EditText uploadEditText;
    private FirebaseUser currentUser;
    DatabaseReference assignmentRef;
    private String courseCallId;
    private String courseCallName;
    private String dateTime;
    public CreateAssignmentDialog(String courseCallId, String courseCallName) {
        this.courseCallId = courseCallId;
        this.courseCallName = courseCallName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_assignment_dialog, null);
        builder.setView(view)
                .setTitle("Create Assignment")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createAssignment(courseCallId, courseCallName);
                        //Important open user page
                        /*Intent profileIntent = new Intent(getActivity(), AssignmentsActivity.class);
                        profileIntent.putExtra("courseId",courseCallId);
                        startActivity(profileIntent);*/
                    }
                });

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        uploadEditText = view.findViewById(R.id.assignment_dialog_edit_uploadText);

        return builder.create();

    }

    private void createAssignment(final String courseId, final String courseName)
    {
        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("Posts");
        assignmentRef = FirebaseDatabase.getInstance().getReference("Assignments");
        final String assignmentId = assignmentRef.push().getKey();
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("StudentListOfTheCourses")
                .child(courseId);
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    StudentListOfTheCourses st = snapshot.getValue(StudentListOfTheCourses.class);
                    assert st != null;
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+03"));
                    dateTime = dateFormat.format(c.getTime());


                    Map<String, Object> hashMap = new HashMap<>();

                    hashMap.put("courseId", courseId);
                    hashMap.put("courseName", courseName);
                    hashMap.put("assignmentId", assignmentId);
                    hashMap.put("assignmentDate", "");
                    hashMap.put("studentId", st.getStudentId());
                    hashMap.put("studentName", st.getStudentName());
                    hashMap.put("assignmentInfoText", uploadEditText.getText().toString());

                    String postId = postRef.getKey();
                    Map<String, Object> hashMapPost = new HashMap<>();
                    hashMapPost.put("courseId", courseId);
                    hashMapPost.put("courseName", courseName);
                    hashMapPost.put("postId", assignmentId);
                    hashMapPost.put("assignmentUploadDate", "");
                    hashMapPost.put("studentId", st.getStudentId());
                    hashMapPost.put("studentName", st.getStudentName());
                    hashMapPost.put("postText", uploadEditText.getText().toString());
                    hashMapPost.put("dateTime", dateTime);
                    hashMapPost.put("postType", "assignment");
                    assert assignmentId != null;
                    assignmentRef.child(st.getStudentId()).child(assignmentId).setValue(hashMap);
                    postRef.child(courseId).setValue(hashMapPost);
                    addAnnouncement(assignmentId);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


                //    startActivity(new Intent(getActivity(), ProfileActivity.class));
            }



    private void addAnnouncement(final String assignmentId){ // Assignment oluşturulduğunda çağıracağız bu methodu.
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("StudentListOfTheCourses")
                .child(courseCallId);

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    StudentListOfTheCourses st = snapshot.getValue(StudentListOfTheCourses.class);
                    assert st != null;
                    DatabaseReference referenceAnnouncement = FirebaseDatabase.getInstance().getReference("Announcements")
                            .child(st.getStudentId());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("assignmentId", assignmentId);
                    hashMap.put("announceStableText", "An assignment was shared in the");
                    hashMap.put("courseId", courseCallId);
                    hashMap.put("courseName", courseCallName);
                    referenceAnnouncement.child(assignmentId).setValue(hashMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}
