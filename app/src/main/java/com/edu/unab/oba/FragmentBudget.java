package com.edu.unab.oba;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentBudget extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "totalSpend";
    private static final String ARG_PARAM2 = "budget";
    private int totalSpend, budget, tempBudget=0;

    Button btnOk, btnCancel;
    EditText edTxtBudget, edTxtSpend, edTxtBalance;

    public FragmentBudget() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentBudget.
     */
    public static FragmentBudget newInstance(int totalSpend, int budget) {
        FragmentBudget fragment = new FragmentBudget();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, totalSpend);
        args.putInt(ARG_PARAM2, budget);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            totalSpend = getArguments().getInt(ARG_PARAM1);
            budget = getArguments().getInt(ARG_PARAM2);;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        btnOk = view.findViewById(R.id.btnOkEditBudget);
        btnCancel = view.findViewById(R.id.btnCancelEditBudget);
        edTxtBudget = view.findViewById(R.id.edTxtBudget);
        edTxtSpend = view.findViewById(R.id.edTxtSpend);
        edTxtBalance = view.findViewById(R.id.edTxtBalance);

        // Set values
        edTxtBudget.setText("" + budget);
        edTxtSpend.setText("" + totalSpend);
        int balance = budget - totalSpend;
        edTxtBalance.setText("" + balance);

        if(totalSpend >  budget){
            edTxtBudget.setTextColor(Color.parseColor("#ff0000"));
            edTxtBudget.setTextColor(Color.parseColor("#ff0000"));
        } else{
            edTxtBudget.setTextColor(Color.parseColor("#000000"));
            edTxtBudget.setTextColor(Color.parseColor("#000000"));
        }

        edTxtBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                   tempBudget = Integer.parseInt(s.toString());
                   if(tempBudget < totalSpend) {
                       edTxtBudget.setTextColor(Color.parseColor("#ff0000"));
                       edTxtBudget.setTextColor(Color.parseColor("#ff0000"));
                   }
                   else {
                       edTxtBudget.setTextColor(Color.parseColor("#000000"));
                       edTxtBudget.setTextColor(Color.parseColor("#000000"));
                   }
                    int balance = tempBudget - totalSpend;
                    edTxtBalance.setText("" +  balance);

                } catch (Exception e){
                    Toast.makeText(getContext(), "Los valores indicados no son válidos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnOkEditBudget:
                int tmpBudget = Integer.parseInt(edTxtBudget.getText().toString());
                if(tmpBudget<totalSpend){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirmación cambio de presupuesto")
                            .setMessage("¿Está seguro de mantener el valor de presupuesto por debajo del gasto total?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    budget = tmpBudget;
                                    getParentFragmentManager().popBackStack();
                                    ((MarketplaceActivity) getContext()).updateBudget(budget);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                    .show();
                }
                else{
                    budget = tmpBudget;
                    ((MarketplaceActivity) getContext()).updateBudget(budget);
                    getParentFragmentManager().popBackStack();
                }
                break;
            case R.id.btnCancelEditBudget:
                getParentFragmentManager().popBackStack();
                break;
        }
    }
}