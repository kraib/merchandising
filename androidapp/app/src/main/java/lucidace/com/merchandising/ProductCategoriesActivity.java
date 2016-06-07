package lucidace.com.merchandising;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import lucidace.com.merchandising.adapters.products.ProductCategoryListAdapter;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.helpers.DummyData;
import lucidace.com.merchandising.models.branches.Branch;
import lucidace.com.merchandising.models.branches.SuperMarket;
import lucidace.com.merchandising.models.products.Product;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.network.sync.SyncAdapter;
import lucidace.com.merchandising.realm.BranchDataController;
import lucidace.com.merchandising.realm.ProductsDataController;

public class ProductCategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mListView;
    ProductCategoryListAdapter mAdapter;
    Toolbar toolbar;
    ActionBar ab;
    private String TAG = ProductCategoriesActivity.class.getSimpleName();
    private Branch branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        getFromIntent();
        setActionBarText(branch.getBranch_name());




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


        mAdapter = new ProductCategoryListAdapter(this, ProductsDataController.with(this).getCategories());


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,mAdapter.getItem(position).getCategory_name()+mAdapter.getItem(position).getProducts().size());
        Intent i = new Intent(this, SurveyActivity.class);

        i.putExtra("product_category_id", Integer.toString(mAdapter.getItem(position).getCategory_id()));
        i.putExtra("branch_id",branch.getBranch_id());
        startActivity(i);
    }
    public void getFromIntent(){
        Intent intent = getIntent();
        branch= (Branch) intent.getSerializableExtra("branch");
    }
}
