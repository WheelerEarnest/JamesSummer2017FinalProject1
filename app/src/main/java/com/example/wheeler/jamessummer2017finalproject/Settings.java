package com.example.wheeler.jamessummer2017finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wheeler.jamessummer2017finalproject.dialog.EraseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Settings extends Fragment {

    private Button erase;
    private GestureDetector gestureDetector;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Settings() {
        // Required empty public constructor
    }

//    @OnClick(R.id.settings_erase)
//    public void erase(View v){
//        EraseDialog eraseDialog = new EraseDialog(getContext(), new EraseDialog.ICustomDialogListener() {
//            @Override
//            public void onOkClicked(String msg) {
//                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//        eraseDialog.setCanceledOnTouchOutside(true);
//        eraseDialog.show();
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        erase = (Button) view.findViewById(R.id.settings_erase);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseDialog eraseDialog = new EraseDialog(getContext(), new EraseDialog.ICustomDialogListener() {
                    @Override
                    public void onOkClicked(String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
                eraseDialog.setCanceledOnTouchOutside(true);
                eraseDialog.show();
            }
        });
        gestureDetector = new GestureDetector(getContext(), new simpleGestureListener());
        //view.setOnTouchListener(getContext());
        ButterKnife.bind(view);
        return view;
    }

    public boolean onTouch(View v, MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    private class simpleGestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            MainActivity mainActivity = (MainActivity) getContext();
            mainActivity.onBackPressed();
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }



}
