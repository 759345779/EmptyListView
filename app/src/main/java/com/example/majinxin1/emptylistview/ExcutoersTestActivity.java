package com.example.majinxin1.emptylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExcutoersTestActivity extends AppCompatActivity {
    ExecutorService excutor = null;
    Runnable runnable = null;
    Future<Boolean> future = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excutoers_test);
        excutor = Executors.newFixedThreadPool(1);
        excutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });


        findViewById(R.id.bt_get_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatus();
            }
        });

        findViewById(R.id.bt_start_tread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThreadPool();
            }
        });
    }

    private void startThreadPool() {

       /* excutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("thread_status", "thread run" );
                for(int i=0;i<100000;i++) {
                    for(int y=0;y<10000;y++);
                }
                Log.i("thread_status", "thread finish" );
            }
        });*/
        future =excutor.submit(new TaskWithResult());
        if (future == null) {
            Log.i("thread_status", "future is =" + null);

        }

    }

    private void getStatus() {
        if (future == null) {
            Log.i("thread_status", "future is =" + null);

        }else {
            boolean isShutdown= false;
            try {
                isShutdown = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                isShutdown = true;
            }
            Log.i("thread_status", "tread future" +
                    " state=" + isShutdown);
        }

    }

    class TaskWithResult implements Callable<Boolean>{


        @Override
        public Boolean call() throws Exception {
            Log.i("thread_status", "thread run" );
            for(int i=0;i<100000;i++) {
                for(int y=0;y<10000;y++);
            }
            Log.i("thread_status", "thread finish" );

            return true;
        }
    }

}
