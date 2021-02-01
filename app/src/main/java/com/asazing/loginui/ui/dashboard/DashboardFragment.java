package com.asazing.loginui.ui.dashboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.asazing.loginui.R;


public class DashboardFragment extends Fragment implements ClipboardManager.OnPrimaryClipChangedListener {

    private DashboardViewModel dashboardViewModel;
    private static final String TAG = "ClipboardFragment";
    private Handler handler;
    private ClipboardManager clipboard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        // Retain so we can keep our handler
        setRetainInstance(true);

    }
        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        TextView textView = root.findViewById(R.id.textCopy);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               copyText(textView.getText().toString());


            }
        });
        return root;

    }
    @Override
    public void onPrimaryClipChanged() {
        Log.d(TAG, "Clipboard changed");

    }

    public void copyText(String text) { // Metodopara copiar texto
        try {
            ClipData clip = ClipData.newPlainText("text",  text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Texto copiado", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(getActivity(), "Error al copiar el texto : "+e, Toast.LENGTH_SHORT).show();
        }


    }
}