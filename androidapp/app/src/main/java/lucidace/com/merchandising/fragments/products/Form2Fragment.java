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
public class Form2Fragment extends Fragment {
    ProductCategory productCategory;
    RecyclerView mRecyclerView;
    SurveyAdapter mSurveyAdapter;
    Button nextButton,backButton;


    public Form2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_form2, container, false);
        nextButton= (Button) view.findViewById(R.id.next);
        backButton= (Button) view.findViewById(R.id.back);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getDataFromBundle(getArguments());
        addProducts();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getListValues();
                Fragment fragment = new Form3Fragment();

                ((SurveyActivity) getActivity()).shiftToFragment(fragment);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getListValues();
                Fragment fragment = new Form1Fragment();
                ((SurveyActivity) getActivity()).shiftToFragment(fragment);
            }
        });


        return view;
    }

    public void addProducts(){
        mSurveyAdapter = new SurveyAdapter(getContext(),productCategory.getProducts(),"competitor");
        mRecyclerView.setAdapter(mSurveyAdapter);
    }

    public void getDataFromBundle(Bundle bundle) {
        productCategory= ((SurveyActivity)getActivity()).getProductCategory();

    }

    private void getListValues(){
        for(int i=0;i<productCategory.getProducts().size();i++){
            View view=mRecyclerView.getChildAt(i);
            EditText editText= (EditText) view.findViewById(R.id.editText);
            EditText editText2= (EditText) view.findViewById(R.id.editText2);
            Realm realm = ProductsDataController.with(MyApplication.getInstance()).getRealm();
            realm.beginTransaction();
            productCategory.getProducts().get(i).setCompetitorA(editText.getText().toString());
            productCategory.getProducts().get(i).setCompetitorB(editText2.getText().toString());
            realm.commitTransaction();

        }
    }

}
