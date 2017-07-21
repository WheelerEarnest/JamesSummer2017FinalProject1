package com.example.wheeler.jamessummer2017finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.wheeler.jamessummer2017finalproject.adapters.BaseViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//CREATE A NEW TASK

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link LongTerm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LongTerm extends Fragment {
    private  ViewPager viewPager;
    ArrayList<Fragment> timePager = new ArrayList<>();


    @BindView(R.id.new_task_name)
    EditText name;
    @BindView(R.id.new_task_description)
    EditText description;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @OnClick(R.id.new_task_accept)
    public void accept(View v){

        MainActivity main = (MainActivity) getContext();
        //main.tasks.add(new)
    }


    public LongTerm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment dusing the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LongTerm.
     */
    // TODO: Rename and change types and number of parameters
    public static LongTerm newInstance(String param1, String param2) {
        LongTerm fragment = new LongTerm();
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
        ButterKnife.bind(getActivity());
        View view = inflater.inflate(R.layout.fragment_long_term, container, false);
        timePager.add(new DateFrag());
        timePager.add(new TimeFrag());
        viewPager = (ViewPager) view.findViewById(R.id.new_task_time_pager);
        viewPager.setAdapter(new BaseViewPagerAdapter(getFragmentManager(), timePager));
        viewPager.setCurrentItem(0);



        return view;
    }

}