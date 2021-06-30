package com.example.cengonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cengonline.R;
import com.example.cengonline.model.Comment;
import com.example.cengonline.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> commentList;
    private FirebaseUser currentUser;

    public CommentAdapter(Context mContext, List<Comment> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_content,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final Comment comment = commentList.get(position);
        holder.commentComItem.setText(comment.getCommentText());
        getUserInfos(holder.imageProfileComItem,holder.usernameComItem,comment.getUserId());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageProfileComItem;
        public TextView usernameComItem, commentComItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfileComItem = itemView.findViewById(R.id.image_profile_com_content);
            usernameComItem = itemView.findViewById(R.id.username_com_content);
            commentComItem = itemView.findViewById(R.id.comment_com_content);

        }
    }

    private void getUserInfos(final ImageView profileImg, final TextView nameSurname, String userId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                Glide.with(mContext).load(user.getPictureUrl()).into(profileImg);
                nameSurname.setText(user.getNameSurname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
