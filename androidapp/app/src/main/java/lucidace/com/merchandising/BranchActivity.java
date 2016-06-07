package lucidace.com.merchandising;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lucidace.com.merchandising.R;
import lucidace.com.merchandising.adapters.branches.BrandsListAdapter;
import lucidace.com.merchandising.adapters.common.ListItemWithIconAdapter;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.branches.SuperMarket;
import lucidace.com.merchandising.network.sync.SyncAdapter;
import lucidace.com.merchandising.realm.BranchDataController;

public class BranchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mListView;
    BrandsListAdapter mAdapter;
    Toolbar toolbar;
    ActionBar ab;
    BranchDataController realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realm=BranchDataController.with(this);

        ab = getSupportActionBar();
        setActionBarText("My Branches");


        mListView = (ListView)findViewById(R.id.list);
        addSampleItems();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);



    }
    public void setActionBarText(String text){
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(text);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_home) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }
        else if(id==R.id.action_logout){
            MyApplication.getInstance().logout();
        }
        else if(id==R.id.action_refresh){
            SyncAdapter.syncImmediately(getApplicationContext());
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void addSampleItems() {

        mAdapter = new BrandsListAdapter(this,BranchDataController.with(this).getBranches());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, ProductCategoriesActivity.class);
        i.putExtra("branch", BranchDataController.with(this).getRealm().copyFromRealm(mAdapter.getItem(position)));
        startActivity(i);

    }
}
