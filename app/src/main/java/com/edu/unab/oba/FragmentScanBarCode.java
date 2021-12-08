package com.edu.unab.oba;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.ArrayList;

import model.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentScanBarCode# newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentScanBarCode extends Fragment {

    private CodeScanner mCodeScanner;
    int REQUEST_CODE = 101;
    Button btnNewScan, btnStopScan;
    EditText edTxtBarCode;
    RecyclerView rvScanProduct;


    public static FragmentScanBarCode newInstance(ArrayList<Category> categories){
        FragmentScanBarCode fragment = new FragmentScanBarCode();

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(
                R.layout.fragment_scan_bar_code,
                container,
                false);

        btnNewScan = root.findViewById(R.id.btnNewScan);
        btnNewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
                mCodeScanner.setAutoFocusEnabled(true);
            }
        });

        btnStopScan = root.findViewById(R.id.btnStopScan);
        btnStopScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.stopPreview();
            }
        });

        edTxtBarCode = root.findViewById(R.id.edTxtBarCode);
        rvScanProduct = root.findViewById(R.id.rvScanProduct);

        if (!checkPermissions()) {
            Toast.makeText(activity, "Permiso no concedido para acceder a la cámara", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }
        CodeScannerView scannerView = root.findViewById(R.id.codeScannerProduct);
        mCodeScanner = new CodeScanner(activity, scannerView);

        mCodeScanner.stopPreview();
        mCodeScanner.setAutoFocusEnabled(false);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edTxtBarCode.setText(result.getText());
                    }
                });
            }
        });
        return root;
    }

    private boolean checkPermissions() {
        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}