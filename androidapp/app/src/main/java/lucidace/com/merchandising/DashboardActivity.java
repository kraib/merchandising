package lucidace.com.merchandising;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lucidace.com.merchandising.adapters.common.ListItemWithIconAdapter;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.common.BasicListItem;
import lucidace.com.merchandising.network.sync.SyncAdapter;

public class DashboardActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView mListView;
    ListItemWithIconAdapter mAdapter;
    ActionBar ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SyncAdapter.syncImmediately(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        ab.setTitle("");


        if (MyApplication.getInstance().getPrefManager().getUser() == null) {
            launchLoginActivity();
        }


        mListView = (ListView)findViewById(R.id.list);
        addDashboardItems();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    private void addDashboardItems(){
        List<BasicListItem> objects = new ArrayList<BasicListItem>();
        BasicListItem item = new BasicListItem();
        item.setTitle("Submit Daily Report");
        item.setDescription("Add current SOS and Prices");
        item.setImageResource(R.drawable.ic_assignment);
        objects.add(item);

        item =new BasicListItem();
        item.setTitle("My Performance");
        item.setDescription("My current Productivity");
        item.setImageResource(R.drawable.ic_trending_up);
        objects.add(item);

        item =new BasicListItem();
        item.setTitle("Check In");
        item.setDescription("Report Presence");
        item.setImageResource(R.drawable.ic_action_check_in);
        objects.add(item);

        item =new BasicListItem();
        item.setImageResource(R.drawable.ic_phone);
        item.setTitle("Call and Contacts");
        item.setDescription("People's contacts and Ip calls");
        objects.add(item);

        mAdapter = new ListItemWithIconAdapter(this,objects);
    }
    private void launchLoginActivity() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mAdapter.getItem(position).getTitle().equals("Submit Daily Report")){

            Intent i = new Intent(this, BranchActivity.class);
            startActivity(i);

        }
        else {
            Toast.makeText(getApplicationContext(), "Module Under Development", Toast.LENGTH_LONG).show();
        }
    }


}
