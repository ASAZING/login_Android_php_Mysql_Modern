package com.asazing.loginui.ui.notifications;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.asazing.loginui.R;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;

    GridView gridView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        gridView = root.findViewById(R.id.simpleGridView);
        MyAdapter customAdapter = new MyAdapter(getActivity());
        gridView.setAdapter(customAdapter);
        return root;
    }

}