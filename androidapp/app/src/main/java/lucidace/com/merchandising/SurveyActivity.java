package lucidace.com.merchandising;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.fragments.products.Form1Fragment;
import lucidace.com.merchandising.models.branches.Branch;
import lucidace.com.merchandising.models.branches.SuperMarket;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.realm.BranchDataController;
import lucidace.com.merchandising.realm.ProductsDataController;

public class SurveyActivity extends AppCompatActivity {
    ProductCategory productCategory;
    private String TAG = SurveyActivity.class.getSimpleName();
    ActionBar ab;
    private Branch branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getFromIntent();
        ab = getSupportActionBar();
        setActionBarText(productCategory.getCategory_name());

        Fragment fragment = new Form1Fragment();
        Bundle bundle = new Bundle();
        Log.i(TAG,productCategory.getCategory_name()+productCategory.getProducts().size());
//        bundle.putSerializable("product_category", ProductsDataController.with(this).getRealm().copyFromRealm(productCategory));
        fragment.setArguments(bundle);
        shiftToFragment(fragment);



    }
    public void setActionBarText(String text){
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(text);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        else if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_home) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }
        else if(id==R.id.action_logout){
            MyApplication.getInstance().logout();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void shiftToFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void getFromIntent(){
        Intent intent = getIntent();

        productCategory= ProductsDataController.with(this).getCategory(intent.getStringExtra("product_category_id"));
        branch= BranchDataController.with(this).getBranch(intent.getStringExtra("branch_id"));
    }
    public Branch getBranch(){
        return this.branch;
    }
    public ProductCategory getProductCategory(){
        return this.productCategory;
    }
}
