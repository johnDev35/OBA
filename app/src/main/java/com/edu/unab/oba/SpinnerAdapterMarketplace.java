package com.edu.unab.oba;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;
import java.util.ArrayList;

import model.Category;

public class SpinnerAdapterMarketplace extends ArrayAdapter<Category> {

       public SpinnerAdapterMarketplace(@NonNull Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return  initView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);

    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_spinner_marketplace,
                    parent,
                    false
            );
        }
        ImageView imgCategory = convertView.findViewById(R.id.imgCategory);
        TextView txtCategory = convertView.findViewById(R.id.txtCategory);
        Category currentCategory = getItem(position);

        if(currentCategory!= null) {
            txtCategory.setText(currentCategory.getCategory());
            Picasso.get().load(currentCategory.getImgCategory())
                    .placeholder(R.drawable.category_candy)
                    .into(imgCategory);
        }
        return convertView;
    }
}
