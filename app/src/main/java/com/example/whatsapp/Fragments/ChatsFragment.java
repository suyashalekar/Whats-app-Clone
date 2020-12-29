package com.example.whatsapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp.Models.User;
import com.example.whatsapp.Models.UserAdaptor;
import com.example.whatsapp.R;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor
    }

ArrayList<User>list = new ArrayList<>();
    RecyclerView chatRecyclarView;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);

        chatRecyclarView = view.findViewById(R.id.chat_recycler_view);

        UserAdaptor userAdaptor = new UserAdaptor(list,getContext());
        chatRecyclarView.setAdapter(userAdaptor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatRecyclarView.setLayoutManager(linearLayoutManager);

        return view;
    }
}