package lucidace.com.merchandising.fragments.products;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;

import io.realm.Realm;
import lucidace.com.merchandising.R;
import lucidace.com.merchandising.SurveyActivity;
import lucidace.com.merchandising.adapters.products.SurveyAdapter;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.realm.ProductsDataController;
import lucidace.com.merchandising.utils.PhotoHandler;
import lucidace.com.merchandising.utils.PhotoUtilsCallBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class Form3Fragment extends Fragment {

    ProductCategory productCategory;
    Button nextButton,backButton;
    EditText brands,why,generalFeedBack;
    private PhotoHandler photoHandler;
    Fragment context;
    View view;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    public Form3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_form3, container, false);
        getDataFromBundle(getArguments());
        brands= (EditText) view.findViewById(R.id.brands);
        why= (EditText) view.findViewById(R.id.why);
        generalFeedBack= (EditText) view.findViewById(R.id.feedback);

        backButton= (Button) view.findViewById(R.id.back);
        nextButton= (Button) view.findViewById(R.id.next);
        context=this;

         imageView1=(ImageView)view.findViewById(R.id.image1);
         imageView2=(ImageView)view.findViewById(R.id.image2);
         imageView3=(ImageView)view.findViewById(R.id.image3);

        setValues();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditTextValues();
                Fragment fragment = new SurveyResultsFragment();
                ((SurveyActivity) getActivity()).shiftToFragment(fragment);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditTextValues();
                Fragment fragment = new Form2Fragment();
                ((SurveyActivity) getActivity()).shiftToFragment(fragment);
            }
        });


        RelativeLayout image1Container = (RelativeLayout) view.findViewById(R.id.image1PhotoContainer);
        RelativeLayout image2Container = (RelativeLayout) view.findViewById(R.id.image2PhotoContainer);
        RelativeLayout image3Container = (RelativeLayout) view.findViewById(R.id.image3PhotoContainer);

        image1Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2=(ImageView)view.findViewById(R.id.image1);

                photoHandler = new PhotoHandler(context, new PhotoUtilsCallBack() {
                    @Override
                    public void onImageTaken(Bitmap image, Uri mImageCaptureUri) {
                        imageView2.setImageBitmap(image);
                        productCategory.getGallery().add(mImageCaptureUri.getPath());
                    }

                    @Override
                    public void onVideoTaken(Bitmap image, Uri mImageCaptureUri) {

                    }
                });

                photoHandler.takePhoto();
            }
        });

        image2Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3=(ImageView)view.findViewById(R.id.image2);

                photoHandler = new PhotoHandler(context, new PhotoUtilsCallBack() {
                    @Override
                    public void onImageTaken(Bitmap image, Uri mImageCaptureUri) {
                        imageView3.setImageBitmap(image);
                        productCategory.getGallery().add(mImageCaptureUri.getPath());
                    }

                    @Override
                    public void onVideoTaken(Bitmap image, Uri mImageCaptureUri) {

                    }
                });
                photoHandler.takePhoto();
            }
        });

        image3Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView imageView=(ImageView)view.findViewById(R.id.image3);

                photoHandler = new PhotoHandler(context, new PhotoUtilsCallBack() {
                    @Override
                    public void onImageTaken(Bitmap image, Uri mImageCaptureUri) {
                        imageView.setImageBitmap(image);
                        productCategory.getGallery().add(mImageCaptureUri.getPath());
                    }

                    @Override
                    public void onVideoTaken(Bitmap image, Uri mImageCaptureUri) {

                    }
                });
                photoHandler.takePhoto();
            }
        });

        return view;
    }



    private void getEditTextValues() {

        productCategory.setBrands(brands.getText().toString());
        productCategory.setWhy(why.getText().toString());
        productCategory.setFeedback(generalFeedBack.getText().toString());

    }
    private void setValues(){
        Realm realm = ProductsDataController.with(MyApplication.getInstance()).getRealm();
        realm.beginTransaction();
        brands.setText(productCategory.getBrands());
        why.setText(productCategory.getWhy());
        generalFeedBack.setText(productCategory.getFeedback());

        if(productCategory.getGallery().size() > 0) {
            if (!productCategory.getGallery().get(0).equals("")) {
                Uri uri = Uri.fromFile(new File(productCategory.getGallery().get(0)));
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    imageView1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if(productCategory.getGallery().size() > 1) {
            if (!productCategory.getGallery().get(1).equals("")) {
                Uri uri = Uri.fromFile(new File(productCategory.getGallery().get(1)));
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    imageView2.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        if(productCategory.getGallery().size() > 2) {

            if (!productCategory.getGallery().get(2).equals("")) {
                Uri uri = Uri.fromFile(new File(productCategory.getGallery().get(2)));
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    imageView3.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        realm.commitTransaction();


    }

    public void getDataFromBundle(Bundle bundle) {
        productCategory= ((SurveyActivity)getActivity()).getProductCategory();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        photoHandler.onActivityResultCallBack(requestCode,resultCode,data);
    }

}
