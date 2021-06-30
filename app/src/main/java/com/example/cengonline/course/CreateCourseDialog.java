package com.example.cengonline.course;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cengonline.MainActivity;
import com.example.cengonline.ProfileActivity;
import com.example.cengonline.R;
import com.example.cengonline.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateCourseDialog extends AppCompatDialogFragment {

    private EditText courseName, coursePeriod, courseCode;
    private FirebaseUser currentUser;
    DatabaseReference courseReference;
    public String teacherName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_dialog, null);
        builder.setView(view)
                .setTitle("Create Course")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    createCourse();
                    }
                });

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        courseName = view.findViewById(R.id.dialog_edit_courseName);
        coursePeriod = view.findViewById(R.id.dialog_edit_coursePeriod);
        courseCode = view.findViewById(R.id.dialog_edit_courseCode);

        return builder.create();

    }

    private void createCourse()
    {
        final String courseId = courseReference.push().getKey();


        //final String[] tempTeacherName = new String[1];
        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                assert user != null;
                teacherName = user.getNameSurname();
                Map<String, Object> hashMap = new HashMap<>();

                hashMap.put("courseId", courseId);
                hashMap.put("courseName", courseName.getText().toString());
                hashMap.put("coursePeriod", coursePeriod.getText().toString());
                hashMap.put("courseCode", courseCode.getText().toString());
                hashMap.put("courseTeacherId", currentUser.getUid());
                hashMap.put("courseTeacherName", teacherName);
                hashMap.put("courseStudentsId", "");
                assert courseId != null;
                courseReference.child(courseId).setValue(hashMap);


          //    startActivity(new Intent(getActivity(), ProfileActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //if usertype = teacher ismini çek.
        //hashMap.put("courseTeacherName", diseasesButton.getText());



    }

}
