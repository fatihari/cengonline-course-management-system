package com.example.cengonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cengonline.R;
import com.example.cengonline.model.Announcement;
import com.example.cengonline.model.Assignment;
import com.example.cengonline.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    private Context mContext;
    private List<Announcement> announcementList;

    public AnnouncementAdapter(Context mContext, List<Announcement> announcementList) {
        this.mContext = mContext;
        this.announcementList = announcementList;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.announcement_content, parent,false);

        return new AnnouncementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {
        final Announcement announcement = announcementList.get(position);
        holder.announceStableText.setText(announcement.getAnnounceStableText());
        holder.courseName.setText(announcement.getCourseName());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView  announceStableText, courseName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.an_courseName);
            announceStableText = itemView.findViewById(R.id.announceStableText);
        }
    }
    private void getUserInfo(final ImageView profilePic, final TextView userName, String userId){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                Glide.with(mContext).load(user.getPictureUrl()).into(profilePic);
                userName.setText(user.getNameSurname());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getAssignmentTxt(final TextView assignmentText, String courseId){
        DatabaseReference referencePost = FirebaseDatabase.getInstance().getReference("Assignments").child(courseId);
        referencePost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Assignment assignment = dataSnapshot.getValue(Assignment.class);
                assert assignment != null;
                assignmentText.setText(assignment.getAssignmentInfoText());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
