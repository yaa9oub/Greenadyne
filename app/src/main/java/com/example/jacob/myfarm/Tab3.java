package com.example.jacob.myfarm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1 , plantTxt , plantnbrTxt;
    private String mParam2;
    private ImageView setting ;
    private OnFragmentInteractionListener mListener;
    TextView name ;


    EditText space , type ;
    RadioGroup radgrp ;
    Button savefarmer ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {   View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        initialize(view);
        name = view.findViewById(R.id.nametxt);
        space = view.findViewById(R.id.spaceedt);
        type = view.findViewById(R.id.earthtypeedt);
        radgrp = view.findViewById(R.id.grp);
        savefarmer = view.findViewById(R.id.farmbtn);
        setting = view.findViewById(R.id.settingsbtn);

        Intent intent = getActivity().getIntent();
        final String user = intent.getStringExtra("user");
        name.setText(user.toUpperCase());

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),settings.class);
                intent.putExtra("user",user);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
            }
        });

        savefarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spacestr = space.getText().toString();
                String typestr = type.getText().toString();
                int radioId = radgrp.getCheckedRadioButtonId();

                if(spacestr.isEmpty() || typestr.isEmpty() || radioId==-1 || plantnbrTxt.equals("PLANTS NUMBER") || plantTxt.equals("PLANTS"))
                {
                    Toast.makeText(getActivity(), "Check your fields !!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
            }
        });

        return view ;}


    public Tab3() {
        // Required empty public constructor
    }

    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
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

        private void initialize(View view)
        {
            String[] plants = {"PLANTS","TOMATO", "STRAWBERRY"};
            Spinner plant = (Spinner) view.findViewById(R.id.plantsspin);
            ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, plants);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            plant.setAdapter(aa);
            plant.setOnItemSelectedListener(new PlantClass());

            String[] nbrs= {"PLANTS NUMBER","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35"};
            Spinner plantnbr = (Spinner) view.findViewById(R.id.plantsnbr);
            ArrayAdapter<String> bb = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, nbrs);
            bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            plantnbr.setAdapter(bb);
            plantnbr.setOnItemSelectedListener(new PlantNbrClass());
        }

    class PlantClass implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            plantTxt = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(getActivity(), "You need to select plants !! ", Toast.LENGTH_SHORT).show();
        }
    }

    class PlantNbrClass implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            plantnbrTxt = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(getActivity(), "You need to select plants !! ", Toast.LENGTH_SHORT).show();
        }
    }

}
