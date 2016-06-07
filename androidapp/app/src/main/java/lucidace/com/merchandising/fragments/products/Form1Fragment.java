package lucidace.com.merchandising.fragments.products;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import lucidace.com.merchandising.R;
import lucidace.com.merchandising.SurveyActivity;
import lucidace.com.merchandising.adapters.products.SurveyAdapter;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.realm.ProductsDataController;

/**
 * A simple {@link Fragment} subclass.
 */
public class Form1Fragment extends Fragment {
    private String TAG = Form1Fragment.class.getSimpleName();

    ProductCategory productCategory;
    RecyclerView mRecyclerView;
    SurveyAdapter mSurveyAdapter;
    Button button;

    public Form1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_form1, container, false);
        button= (Button) view.findViewById(R.id.next);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getDataFromBundle(getArguments());
        addProducts();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getListValues();
                Fragment fragment = new Form2Fragment();
                ((SurveyActivity) getActivity()).shiftToFragment(fragment);
            }
        });


        return view;
    }

    public void addProducts(){
        mSurveyAdapter = new SurveyAdapter(getContext(),productCategory.getProducts(),"sos");
        mRecyclerView.setAdapter(mSurveyAdapter);
    }

    public void getDataFromBundle(Bundle bundle) {
        productCategory= ((SurveyActivity)getActivity()).getProductCategory();
        Log.i(TAG, productCategory.getCategory_name());

    }

    private void getListValues(){
        for(int i=0;i<productCategory.getProducts().size();i++){
            View view=mRecyclerView.getChildAt(i);
            EditText editText= (EditText) view.findViewById(R.id.editText);
            EditText editText2= (EditText) view.findViewById(R.id.editText2);

            //save to db
            Realm realm = ProductsDataController.with(MyApplication.getInstance()).getRealm();
            realm.beginTransaction();
            productCategory.getProducts().get(i).setSos(editText.getText().toString());
            productCategory.getProducts().get(i).setPrice(editText2.getText().toString());
            realm.commitTransaction();

        }
    }


}
