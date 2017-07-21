package com.example.wheeler.jamessummer2017finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.wheeler.jamessummer2017finalproject.adapters.TaskListAdapter;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TaskList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskList extends Fragment {

    private final int NEWTASK = 123;
    private final int EDITTASK = 321;

    private ListView listView;
    private Button addTask;
    private int selected;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public TaskList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskList.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskList newInstance(String param1, String param2) {
        TaskList fragment = new TaskList();
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
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);
        listView = (ListView) v.findViewById(R.id.tasklv);
        MainActivity fun =(MainActivity) getContext();
        listView.setAdapter(new TaskListAdapter(getContext(),fun.tasks));
        ButterKnife.bind(getActivity());
        selected = 0;
        addTask = (Button) v.findViewById(R.id.new_long);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddTask.class);
                startActivityForResult(intent, NEWTASK);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity mainActivity = (MainActivity) getContext();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Task", mainActivity.tasks.get(position));
                Intent intent = new Intent(getActivity(), ActivityEditTask.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDITTASK);
                selected = position;
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        Task task = (Task) bundle.getParcelable("Task");
        MainActivity mainActivity = (MainActivity) getContext();

        switch (requestCode) {
            case NEWTASK:

                mainActivity.tasks.add(task);
                break;
            case EDITTASK:
                Task editingTask = mainActivity.tasks.get(selected);
                editingTask.setName(task.getName());
                editingTask.setInfo(task.getInfo());
                break;
            default:
        }
        listView.invalidateViews();
    }


}
