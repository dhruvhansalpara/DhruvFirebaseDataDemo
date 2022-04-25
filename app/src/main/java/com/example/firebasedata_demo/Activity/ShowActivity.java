package com.example.firebasedata_demo.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedata_demo.ImageAdapter;
import com.example.firebasedata_demo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowActivity  extends AppCompatActivity {


    private RecyclerView RVImageList;
    private ArrayList<String> list;

    private ImageAdapter adapter;
    ProgressDialog progressDialog ;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Images");


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showactivity);

        RVImageList = findViewById(R.id.RVImageList);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data...");
        progressDialog.show();

        RVImageList.setHasFixedSize(true);
        RVImageList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new ImageAdapter(this , list);
        RVImageList.setAdapter(adapter);


        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("images/");
        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference file:listResult.getItems()){
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            list.add(uri.toString());
                            Log.e("Itemvalue",uri.toString());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            RVImageList.setAdapter(adapter);

                            progressDialog.dismiss();

                        }
                    });
                }
            }
        });


//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Model model = dataSnapshot.getValue(Model.class);
//                    list.add(model);
//
//                }
//                adapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });

    }
}
