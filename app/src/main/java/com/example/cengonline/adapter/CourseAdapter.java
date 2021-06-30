package com.example.cengonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cengonline.PostActivity;
import com.example.cengonline.R;
import com.example.cengonline.course.CreateAssignmentDialog;
import com.example.cengonline.model.Course;
import com.example.cengonline.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context mContext;
    private List<Course> courseList;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public CourseAdapter(Context mContext, List<Course> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.course_content,parent,false);

        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        final Course course = courseList.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.coursePeriod.setText(course.getCoursePeriod());
        holder.courseTeacher.setText(course.getCourseTeacherName());
        holder.courseIdInvisible.setText(course.getCourseId());
        holder.createAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAssignmentDialog createCourseDialog = new CreateAssignmentDialog(holder.courseIdInvisible.getText().toString(),
                        holder.courseName.getText().toString());
                createCourseDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(),"Create Assignment");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostActivity.class);
                intent.putExtra("courseId",course.getCourseId());
                intent.putExtra("courseName",course.getCourseName());
                intent.putExtra("coursePeriod",course.getCoursePeriod());
                intent.putExtra("courseTeacherName",course.getCourseTeacherName());
                mContext.startActivity(intent);
            }
        });

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if(user.getType().equals("Teacher")){
                    holder.createAssignment.setVisibility(View.VISIBLE);
                    holder.editCourse.setVisibility(View.VISIBLE);
                }
                else{
                    holder.createAssignment.setVisibility(View.GONE);
                    holder.editCourse.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView courseName, coursePeriod, courseTeacher, editCourse, createAssignment, courseIdInvisible ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.content_courseName);
            coursePeriod = itemView.findViewById(R.id.content_coursePeriod);
            courseTeacher = itemView.findViewById(R.id.content_courseTeacher);
            editCourse = itemView.findViewById(R.id.content_editCourse);
            createAssignment = itemView.findViewById(R.id.content_createAssignment);
            courseIdInvisible = itemView.findViewById(R.id.content_courseId);

        }
    }
}
