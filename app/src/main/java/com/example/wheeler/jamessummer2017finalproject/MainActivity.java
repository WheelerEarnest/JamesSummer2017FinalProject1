package com.example.wheeler.jamessummer2017finalproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wheeler.jamessummer2017finalproject.adapters.ListNormalAdapter;
import com.example.wheeler.jamessummer2017finalproject.service.TestService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private ArrayList<String> drawerList;
    private DrawerLayout drawerLayout;
    private ListView listView;

    public ArrayList<Task> tasks;
    private TestReceiver testReceiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar tb = (Toolbar) findViewById(R.id.main_action_bar);
        setSupportActionBar(tb);
        tasks = new ArrayList<>();
        Task defaultTask = new Task("Default", "just a random default");
        defaultTask.setDue(new Date (System.currentTimeMillis()));
        tasks.add(defaultTask);


        startService();



        drawerList = new ArrayList<>();
        drawerList.add("Current Task");
        //drawerList.add("New Task");
        drawerList.add("List of Tasks");
        //drawerList.add("Calendar");
        drawerList.add("Settings");

        setUpFrags();

    }

    private void startService(){
        Intent intent = new Intent(this, TestService.class);
        intent.putExtra("Service", "Start");
        int closest = nextTask();
        Task task = tasks.get(closest);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Task",task);
        intent.putExtras(bundle);
        startService(intent);
        registerBroadcast();
    }


    private void registerBroadcast(){
        testReceiver = new TestReceiver();
        IntentFilter filer = new IntentFilter();
        filer.addAction(TestService.ACTION);
        registerReceiver(testReceiver, filer);
    }


    private void setUpFrags(){
        final LongTerm longTerm = new LongTerm();
        final CurrentTask currentTask = new CurrentTask();
        final Settings settings = new Settings();
        final TaskList taskList = new TaskList();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CurrentTask task = new CurrentTask();
        fragmentTransaction.add(R.id.main_fragment, task);
        fragmentTransaction.commit();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.drawer_left);
        listView.setAdapter(new ListNormalAdapter(this, drawerList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        drawerLayout.closeDrawers();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment, currentTask).commit();
                        break;
                    case 1:
                        drawerLayout.closeDrawers();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment, taskList).commit();
                        break;

                    case 2:
                        drawerLayout.closeDrawers();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment, settings).commit();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.menu_expansion){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class TestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals(TestService.ACTION)){
                int update = intent.getIntExtra(TestService.UPDATE, 0);

            }
        }
    }
    private NotificationManager manager;
    private int notifyId = 100;
    private PendingIntent getDefaultIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }
    public void smallNotification(View v){
        Toast.makeText(this, "small", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("An item is due")
                .setContentText("Text")
                .setContentIntent(getDefaultIntent(Notification.FLAG_AUTO_CANCEL))
                .setNumber(10)
                .setTicker("Ticker")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(notifyId, mBuilder.build());
    }
    public int nextTask(){
        int nextTask = -1;
        Date earliest = null;
        int i = 0;
        if(!tasks.isEmpty()) {
            for (Task task : tasks) {
                Date tmp = task.getDue();
                if (earliest == null || tmp.compareTo(earliest) < 0) {
                    earliest = tmp;
                    nextTask = i;
                }
                i++;
            }
        }
        return nextTask;
    }
}
