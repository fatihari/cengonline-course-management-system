package com.example.cengonline.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cengonline.AssignmentsActivity;
import com.example.cengonline.CommentActivity;
import com.example.cengonline.R;
import com.example.cengonline.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private Context mContext;
    private List<Post> postList;
    private FirebaseAuth auth;
    private FirebaseUser currentFirebaseUser;
    private String courseId, courseName, courseTeacherName, coursePeriod;


    public PostAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;

    }


    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_content,parent,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Intent intent = ((Activity) mContext).getIntent();
        courseId = intent.getStringExtra("courseId");
        courseName = intent.getStringExtra("courseName");
        coursePeriod = intent.getStringExtra("coursePeriod");
        courseTeacherName = intent.getStringExtra("courseTeacherName");

        auth = FirebaseAuth.getInstance();
        currentFirebaseUser = auth.getCurrentUser();
        final Post post = postList.get(position);
        holder.txtPost.setText(post.getPostText());
        holder.postType.setText(post.getPostType().toLowerCase());

        if(holder.postType.getText().toString().equals("assignment")){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF9E9"));

            holder.postType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AssignmentsActivity.class);
                    mContext.startActivity(intent);
                }
            });


        }
        if(holder.postType.getText().toString().equals("post")){
            holder.itemView.setBackgroundColor(Color.parseColor("#EEF3FD"));
        }
        holder.txtDate.setText(post.getDateTime());
        String url="https://firebasestorage.googleapis.com/v0/b/cengonline-e8060.appspot.com/o/placeholder.png?alt=media&token=b15a060b-b9ca-4f12-86c8-3b10cd8d330c";
        Glide.with(mContext).load(url).into(holder.profilePicture);
        holder.txtTeacherName.setText(courseTeacherName);



        holder.viewAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("postId", post.getPostId());
                intent.putExtra("userId", post.getUserId());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profilePicture;

        public TextView txtPost, txtTeacherName, viewAllComments,
                txtFixed, postType, dotFixed, txtDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture = itemView.findViewById(R.id.post_profile_picture_content);
            txtPost = itemView.findViewById(R.id.text_post_content);
            viewAllComments = itemView.findViewById(R.id.view_all_comments);
            txtTeacherName = itemView.findViewById(R.id.txt_teacherName_post_content);
            txtFixed = itemView.findViewById(R.id.fixed_text_content);
            postType = itemView.findViewById(R.id.post_type_content);
            dotFixed = itemView.findViewById(R.id.fixed_dot_content);
            txtDate = itemView.findViewById(R.id.post_date_content);

        }
    }



}


