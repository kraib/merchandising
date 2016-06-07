package lucidace.com.merchandising.app;


import android.app.Application;
import android.content.Context;
import android.content.Intent;


import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lucidace.com.merchandising.LoginActivity;
import lucidace.com.merchandising.helpers.SessionManager;
import lucidace.com.merchandising.realm.BranchDataController;
import lucidace.com.merchandising.realm.ProductsDataController;




public class MyApplication extends Application {
    SessionManager pref;

    public static final String TAG = MyApplication.class
            .getSimpleName();


    @Override
    public void attachBaseContext(Context base){
        super.attachBaseContext(base);


    }

    private static MyApplication mInstance;



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }



    public SessionManager getPrefManager() {
        if (pref == null) {
            pref = new SessionManager(this);
        }

        return pref;
    }

    public String getToken(){
        if (pref == null) {
            pref = new SessionManager(this);
        }

        return pref.getUser().getAccess_token();
    }

    public void logout() {
        pref.logoutUser();
        BranchDataController.with(this).clearAll();
        ProductsDataController.with(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
