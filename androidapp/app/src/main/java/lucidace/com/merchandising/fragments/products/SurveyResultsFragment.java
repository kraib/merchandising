package lucidace.com.merchandising.fragments.products;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import lucidace.com.merchandising.ProductCategoriesActivity;
import lucidace.com.merchandising.R;
import lucidace.com.merchandising.SurveyActivity;
import lucidace.com.merchandising.adapters.products.GalleryAdapter;
import lucidace.com.merchandising.adapters.products.SurveyAdapter;
import lucidace.com.merchandising.adapters.products.SurveyResultsAdapter;
import lucidace.com.merchandising.helpers.SimpleDividerItemDecoration;
import lucidace.com.merchandising.models.branches.Branch;
import lucidace.com.merchandising.models.branches.SuperMarket;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.realm.BranchDataController;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyResultsFragment extends Fragment {
    ProductCategory productCategory;
    RecyclerView mRecyclerSosView,mRecyclerCompetitorView;
    SurveyResultsAdapter mSurveySosAdapter,mSurveyCompetitorAdapter;
    Button saveButton,draftButton,backButton;
    TextView brands,why,generalFeedback;
    GridView gridView;
    GalleryAdapter galleryAdapter;
    MaterialDialog mMaterialDialog;



    public SurveyResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDataFromBundle(getArguments());
        View view=inflater.inflate(R.layout.fragment_survey_results, container, false);
        mRecyclerSosView= (RecyclerView) view.findViewById(R.id.recycler_view_a);
        mRecyclerCompetitorView= (RecyclerView) view.findViewById(R.id.recycler_view_b);
        gridView=(GridView)view.findViewById(R.id.grid);

        brands= (TextView) view.findViewById(R.id.brands);
        why= (TextView) view.findViewById(R.id.why);
        generalFeedback= (TextView) view.findViewById(R.id.feedback);
        backButton= (Button) view.findViewById(R.id.back);
        saveButton= (Button) view.findViewById(R.id.next);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerSosView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        mRecyclerCompetitorView.setLayoutManager(layoutManager2);

        mRecyclerCompetitorView.addItemDecoration(new SimpleDividerItemDecoration(
                getContext()
        ));

        mRecyclerSosView.addItemDecoration(new SimpleDividerItemDecoration(
                getContext()
        ));

        fillData();
        mRecyclerSosView.setAdapter(mSurveySosAdapter);
        mRecyclerCompetitorView.setAdapter(mSurveyCompetitorAdapter);
        gridView.setAdapter(galleryAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Form3Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("product_category", productCategory);
                fragment.setArguments(bundle);
                ((SurveyActivity) getActivity()).shiftToFragment(fragment);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        return view;
    }

    public void getDataFromBundle(Bundle bundle) {
        productCategory= ((SurveyActivity)getActivity()).getProductCategory();
    }
    public void fillData(){
        mSurveySosAdapter =new SurveyResultsAdapter(getContext(),productCategory.getProducts(),"sos");
        mSurveyCompetitorAdapter =new SurveyResultsAdapter(getContext(),productCategory.getProducts(),"competitor");
        brands.setText(productCategory.getBrands());
        why.setText(productCategory.getWhy());
        generalFeedback.setText(productCategory.getFeedback());
        galleryAdapter=new GalleryAdapter(getContext(),productCategory.getGallery());

    }
    public void save(){
        mMaterialDialog = new MaterialDialog(getContext())
                .setTitle("Thank You")
                .setMessage("Your Report Has been Submitted")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Branch branch = ((SurveyActivity) getActivity()).getBranch();
                        Intent i = new Intent(getContext(), ProductCategoriesActivity.class);
                        i.putExtra("branch", BranchDataController.with(getActivity()).getRealm().copyFromRealm(branch));
                        startActivity(i);

                    }
                });


        mMaterialDialog.show();


    }

}
