package com.example.jacob.myfarm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Tab2 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private DatabaseHelper mDataBaseHelper ;

    private OnFragmentInteractionListener mListener;

    TextView name ;
    EditText plant , temp , hum , sal , light ;
    Button res ;
    TextView restxt ;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        mDataBaseHelper = new DatabaseHelper(getActivity());
        temp = view.findViewById(R.id.tempedit);
        plant = view.findViewById(R.id.plantedit);
        hum = view.findViewById(R.id.humedit);
        sal = view.findViewById(R.id.saledit);
        light = view.findViewById(R.id.lightedit);
        res = view.findViewById(R.id.resbtn);
        restxt = view.findViewById(R.id.resulttxt);
        name = view.findViewById(R.id.nametxt);

        Intent intent = getActivity().getIntent();
        final String user = intent.getStringExtra("user");
        name.setText(user.toUpperCase());

        final String[][] result = new String[1][1];
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result[0] = new String[]{""};
                String tempstr = temp.getText().toString();
                String humstr = temp.getText().toString();
                String salstr = temp.getText().toString();
                String lightstr = temp.getText().toString();
                if(tempstr.isEmpty() || humstr.isEmpty() || salstr.isEmpty() || lightstr.isEmpty())
                {
                    Toast.makeText(getActivity(), "Please fill all the fields !!", Toast.LENGTH_SHORT).show();
                }
                else
                {restxt.setText("");
                    if(plant.getText().toString().toLowerCase().equals("tomato"))
                    {
                        if(Integer.parseInt(tempstr)<20 )
                            result[0][0] = result[0][0] + "LOW TEMPERTURE you need to turn on the heating system , " ;
                        else if(Integer.parseInt(tempstr)>35)
                            result[0][0] = result[0][0] + "HIGH TEMPERTURE You have to ventilate the greenhouse , ";
                        else
                            result[0][0] = result[0][0] + "GOOD TEMPERTURE !! , ";
                        if(Integer.parseInt(humstr)<60)
                            result[0][0] = result[0][0] + "LOW HUMIDITY you need to water the trees , ";
                        else
                            result[0][0] = result[0][0] + "GOOD HUMIDITY , ";
                        if (Integer.parseInt(salstr)<3)
                            result[0][0] = result[0][0] + ", you need to add more mineral salts , ";
                        else
                            result[0][0] = result[0][0] + "NICE SALINITY , ";
                        if (Integer.parseInt(lightstr)<6000)
                            result[0][0] = result[0][0] + "there is lack of lighting !! , ";
                        else
                            result[0][0] = result[0][0] + "lighting is perfect !! , ";
                        restxt.setText(result[0][0]);
                    }
                    else if(plant.getText().toString().toLowerCase().equals("strawberry"))
                    {
                        if(Integer.parseInt(tempstr)<20 )
                            result[0][0] = result[0][0] + "  LOW TEMPERTURE you need to turn on the heating system ," ;
                        else if(Integer.parseInt(tempstr)>24)
                            result[0][0] = result[0][0] + "  HIGH TEMPERTURE You have to ventilate the greenhouse ,";
                        else
                            result[0][0] = result[0][0] + "  GOOD TEMPERTURE !! ,";
                        if(Integer.parseInt(humstr)<55)
                            result[0][0] = result[0][0] + "  LOW HUMIDITY you need to water the trees ,";
                        else
                            result[0][0] = result[0][0] + "  GOOD HUMIDITY ,";
                        if (Integer.parseInt(salstr)<2)
                            result[0][0] = result[0][0] + "  you need to add more mineral salts ,";
                        else
                            result[0][0] = result[0][0] + "  NICE SALINITY ,";
                        if (Integer.parseInt(lightstr)<6000)
                            result[0][0] = result[0][0] + "  there is lack of lighting !! ,";
                        else
                            result[0][0] = result[0][0] + "  lighting is perfect !! ,";
                        restxt.setText(result[0][0]);
                    }
                    else
                        restxt.setText("The situation is perfect !!");
                }
            }
        });


        return view ;
    }

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Tab2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
