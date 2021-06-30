package com.example.cengonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cengonline.ChatActivity;
import com.example.cengonline.R;
import com.example.cengonline.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> userList;
    private FirebaseUser currentUser;
    private String item;
    public UserAdapter(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //user_content layoutu view tuttuk.
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_content,parent,false);
        item="";
        //ViewHolder ile bu görsel layout'u (viewi) return ediyoruz.
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
    //veriler databaseden alınır bu methodda ve ekrana yazdırılır.

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = userList.get(position);
        holder.txtUserName.setText(user.getNameSurname());
        Glide.with(mContext).load(user.getPictureUrl()).into(holder.imgProfilePic); //veritabanından profil resmini çağırıp, sayfaya(layout içeren view'a ekledik)
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userId",user.getId());
                intent.putExtra("userName",user.getNameSurname());
                intent.putExtra("userPictureUrl",user.getPictureUrl());
                mContext.startActivity(intent);
            }
        });*/
        holder.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userId",user.getId());
                intent.putExtra("userName",user.getNameSurname());
                intent.putExtra("userPictureUrl",user.getPictureUrl());
                mContext.startActivity(intent);
            }
        });
        //Kişi kendisini listede gördüğünde kendisine mesaj atmasını engellemek için.
        if(user.getId().equals(currentUser.getUid()))
        {
            holder.btnMessage.setVisibility(View.INVISIBLE);
        }




    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtUserName;
        CircleImageView imgProfilePic;
        Button btnMessage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.user_content_username);
            imgProfilePic = itemView.findViewById(R.id.user_content_profilePicture);
            btnMessage = itemView.findViewById(R.id.user_content_messageButton);

        }
    }
}
