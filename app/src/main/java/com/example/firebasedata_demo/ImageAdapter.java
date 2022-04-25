package com.example.firebasedata_demo;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private ArrayList<String> mList;
    private Context context;
    ProgressDialog progressDialog ;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;

    public ImageAdapter(Context context, ArrayList<String> mList) {

        this.context = context;
        this.mList = mList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(mList.get(position)).into(holder.imageView);




        holder.ivDownlaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadsImage(mList.get(position)).execute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Downlaod Data...");
                progressDialog.show();
                Log.e("dhruvtest","--"+mList.get(position).toString());


            }
        });



    }


    class DownloadsImage extends AsyncTask<String, Void,Void>{

        String urls;
        DownloadsImage(String url){
            this.urls=url;
        }

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(urls);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(context,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        // Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(context,"Image is Downlaod",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView ,ivDownlaod;

        public MyViewHolder( View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivURLImage);
            ivDownlaod = itemView.findViewById(R.id.ivDownlaod);

        }
    }

}
