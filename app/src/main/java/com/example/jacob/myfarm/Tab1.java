package com.example.jacob.myfarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class Tab1 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private DatabaseHelper mDataBaseHelper ;
    private OnFragmentInteractionListener mListener;
    private RadioButton onVibration ;


    public Tab1() {
        // Required empty public constructor

    }

    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
    private CustomGauge gauge1;
    private CustomGauge gauge2;
    private CustomGauge gauge3;
    private TextView text1 , text2 , text3;
    int i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tab1, container, false);

        onVibration = view.findViewById(R.id.btn3);
        Button button = view.findViewById(R.id.button);
        mDataBaseHelper = new DatabaseHelper(getActivity());
        gauge1 = view.findViewById(R.id.gauge1);
        gauge2 = view.findViewById(R.id.gauge2);
        gauge3 = view.findViewById(R.id.gauge3);

        text1  = view.findViewById(R.id.textView1);
        text2  = view.findViewById(R.id.textView2);
        text3  = view.findViewById(R.id.textView3);

        Intent intent = getActivity().getIntent();
        final String user = intent.getStringExtra("user");


        String tempe="" , hume="" , sale="";
        Cursor data = mDataBaseHelper.getsensors(user);
        while(data.moveToNext()){
            tempe = data.getString(0);
            hume = data.getString(1);
            sale = data.getString(2);
        }

        gauge1.setValue(Integer.parseInt(sale));
        gauge2.setValue(Integer.parseInt(hume));
        gauge3.setValue(Integer.parseInt(tempe));

        text1.setText(Integer.toString(gauge1.getValue()));
        text2.setText(Integer.toString(gauge2.getValue()));
        text3.setText(Integer.toString(gauge3.getValue()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                final int x = rand.nextInt(30)+20, y=rand.nextInt(70)+30 ,z=rand.nextInt(5)+1 ;
                sensing(x,y,z);
                boolean sensor = mDataBaseHelper.updateSensor(user,x,y,z);
                if(sensor == true)
                    Toast.makeText(getActivity(), "REFRESHED", Toast.LENGTH_SHORT).show();
                new Thread() {
                    public void run() {
                        for (i=0;i<=100;i++) {
                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(i<=z){
                                            gauge1.setValue(i);
                                            text1.setText(Integer.toString(gauge1.getValue()));
                                        }
                                        if(i<=y){
                                            gauge2.setValue(i);
                                            text2.setText(Integer.toString(gauge2.getValue()));
                                        }
                                        if(i<=x){
                                            gauge3.setValue(i);
                                            text3.setText(Integer.toString(gauge3.getValue()));
                                        }
                                    }
                                });
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }

        });

        onVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
            }
        });

        return view;
    }


    private void sensing(int temp ,int hum ,int sel) {
        if(temp<20 || temp > 35 || hum < 60 || sel <3)
        {
            NotificationCompat.Builder b = new NotificationCompat.Builder(getActivity());
            Intent intent =new Intent(getActivity(),Tab2.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            b.setAutoCancel(true)
                    .setSmallIcon(R.drawable.icon)
                    .setContentText("Verify the conditions , plants are in danger !!")
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentTitle("Notification Alert, Click Me!")
                    .setAutoCancel(true)
                    .setVibrate(new long[] {0 , 100 , 200 , 100 , 200 , 100 , 200 , 100 })
                    .setLights(Color.RED ,  1000, 1000)
                    .setContentIntent(contentIntent);

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, b.build());
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
