package com.edu.unab.oba;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBudget#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBudget extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBudget() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBudget.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBudget newInstance(String param1, String param2) {
        FragmentBudget fragment = new FragmentBudget();
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

    Button btnOk, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        btnOk = view.findViewById(R.id.btnOkEditBudget);
        btnCancel = view.findViewById(R.id.btnCancelEditBudget);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOkEditBudget:
                Toast.makeText(getContext(), "Ok Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancelEditBudget:
                Toast.makeText(getContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        getParentFragmentManager().popBackStack();

    }
}