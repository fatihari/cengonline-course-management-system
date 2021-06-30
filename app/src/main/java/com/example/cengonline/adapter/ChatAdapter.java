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
import com.example.cengonline.model.Chat;
import com.example.cengonline.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context mContext;
    private List<Chat> chatList;
    private FirebaseUser currentUser;
    public static final int MESSAGE_LEFT = 0;
    public static final int MESSAGE_RIGHT = 1;

    public ChatAdapter(Context mContext, List<Chat> chatList) {
        this.mContext = mContext;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MESSAGE_LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_content_left, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_content_right, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        Chat chat = chatList.get(position);
        holder.textChatItem.setText(chat.getMessageText());
        //getUserInfo(holder.timeItem,chat.getReceiverUserId());
        //getUserInfo(holder.timeItem,chat.getSenderUserId());
        holder.timeItem.setText(chat.getCurrentTime());


     /*   if(position!=getItemCount()-1 && chatList.get(position).getCurrentDate().equals(chatList.get(position-1).getCurrentDate()))
        {
            holder.dateItem.setVisibility(View.GONE);
        }

        if(position!=0 && !chatList.get(position).getCurrentDate().equals(chatList.get(position-1).getCurrentDate()))
        {
            holder.dateItem.setVisibility(View.VISIBLE);
        holder.dateItem.setText(chat.getCurrentDate());
    }
        */
        if(position!=0)
        {

            if(chatList.get(position).getCurrentDate().equals(chatList.get(position-1).getCurrentDate()))
            {
                holder.dateItem.setVisibility(View.GONE);
            }
            else if(!chatList.get(position).getCurrentDate().equals(chatList.get(position-1).getCurrentDate()))
            {
                holder.dateItem.setVisibility(View.VISIBLE);
                holder.dateItem.setText(chat.getCurrentDate());
            }
        }
        if(position==0)
        {
            holder.dateItem.setText(chatList.get(position).getCurrentDate());
            holder.dateItem.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageProfileChatItem;
        public TextView timeItem, textChatItem, dateItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeItem = itemView.findViewById(R.id.time_chat_item);
            textChatItem = itemView.findViewById(R.id.text_chat_item);
            dateItem = itemView.findViewById(R.id.date_chat_item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSenderUserId().equals(currentUser.getUid()))
        {
            return MESSAGE_RIGHT;
        }

        else
            {
                return MESSAGE_LEFT;
            }
    }
}
