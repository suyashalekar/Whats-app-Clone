package com.example.whatsapp.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.viewHolder>{

    ArrayList<User> list ;
    Context context;

    public UserAdaptor(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,
                parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        User user = list.get(position);
        Picasso.get().load(user.getProfilepic()).
                placeholder(R.drawable.man).into(holder.chatProfileImage);
        holder.userName.setText(user.getUserName());
 }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  class  viewHolder extends RecyclerView.ViewHolder {
        ImageView chatProfileImage;
        TextView userName,lastMessage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            chatProfileImage = itemView.findViewById(R.id.chat_user_name);
            userName = itemView.findViewById(R.id.chat_user_name);
            lastMessage= itemView.findViewById(R.id.chat_last_message);
        }
    }


}
