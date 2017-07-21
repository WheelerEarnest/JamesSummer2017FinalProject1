package com.example.wheeler.jamessummer2017finalproject;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CurrentTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentTask extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public CurrentTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentTask.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentTask newInstance(String param1, String param2) {
        CurrentTask fragment = new CurrentTask();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public int nextTask(){
        int nextTask = 0;
        Date earliest = null;
        MainActivity mainActivity = (MainActivity) getContext();
        int i = 0;
        for(Task task : mainActivity.tasks){
            Date tmp = task.getDue();
            if(earliest == null || tmp.compareTo(earliest) < 0){
                earliest = tmp;
                nextTask = i;
            }
            i++;
        }
        return nextTask;
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
        View view = inflater.inflate(R.layout.fragment_current_task, container, false);
        MainActivity mainActivity = (MainActivity) getContext();
        TextView name =  (TextView) view.findViewById(R.id.current_task_title);
        TextView description = (TextView) view.findViewById(R.id.current_task_desc);
        TextView due = (TextView) view.findViewById(R.id.current_task_due);
        if(mainActivity.tasks.isEmpty()){
            name.setText("No tasks");
        }else {
            int nextTask = nextTask();
            Task task = mainActivity.tasks.get(nextTask);
            name.setText(task.getName());
            description.setText(task.getInfo());
            due.setText(task.getDue().toString());

        }
        startThread();
        return view;
    }
    private final int TIMER = 123;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case TIMER:
                    Bundle bundle = msg.getData();
//                    String s = bundle.getString("msg");
//                    shortToast("Message: " + s);
                    MainActivity mainActivity = (MainActivity) getContext();
                    ImageView imgv = (ImageView) mainActivity.findViewById(R.id.current_task_image);

                    ObjectAnimator animator =
                            ObjectAnimator.ofFloat(imgv, "rotationY", 0,360,0);
                    animator.setDuration(1000);
                    animator.setInterpolator(new AccelerateInterpolator());
                    //animator.getRepeatMode(ValueAnimator.REVERSE);
                    animator.start();

                    break;
                default:
            }
            super.handleMessage(msg);
        }
    };
    private Thread thread;
    private void startThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "TimerMessage");
                    msg.setData(bundle);
                    msg.what = TIMER;
                    handler.sendMessage(msg);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
