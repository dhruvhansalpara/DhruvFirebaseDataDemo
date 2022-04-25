package com.example.firebasedata_demo.database;



import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {ImageModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract Dao Dao();

    private Context context;


    public static AppDatabase getInstance(Context context){

        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Image-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    // below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate( SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new PopulateDbAsyncTask(appDatabase).execute();
        }
    };

    // we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(AppDatabase instance) {
            Dao dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    public static void destroyInstance() {
        appDatabase = null;
    }
}
