package lucidace.com.merchandising.network.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import lucidace.com.merchandising.R;
import lucidace.com.merchandising.app.MyApplication;
import lucidace.com.merchandising.models.branches.BranchPojo;
import lucidace.com.merchandising.models.products.ProductCategory;
import lucidace.com.merchandising.network.handlers.BranchHandler;
import lucidace.com.merchandising.network.handlers.BranchListener;
import lucidace.com.merchandising.network.handlers.ProductsHandler;
import lucidace.com.merchandising.network.handlers.ProductsListener;
import lucidace.com.merchandising.realm.BranchDataController;
import lucidace.com.merchandising.realm.ProductsDataController;


public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = SyncAdapter.class.getSimpleName();

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize,true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        if (MyApplication.getInstance().getPrefManager().getUser() != null) {
            Log.d(LOG_TAG, "onPerformSync Called.");
            ProductsHandler productsHandler = new ProductsHandler();
            productsHandler.getProductCategories(new ProductsListener() {
                @Override
                public void productsCategoryReceived(List<ProductCategory> productCategories) {
//                    Log.d(LOG_TAG,productCategories.get(0).getPhoto());
                    ProductsDataController.with(MyApplication.getInstance()).refresh();

                }

                @Override
                public void productsReceived(String messages) {
                    Log.d(LOG_TAG, messages);
                }


                @Override
                public void networkFailure(String localizedMessage) {

                    Log.d(LOG_TAG, localizedMessage);
                }

                @Override
                public void emptyDataSet(String localizedMessage) {

                    Log.d(LOG_TAG, localizedMessage);
                }
            });

            BranchHandler branchHandler = new BranchHandler();
            branchHandler.getMyBranches(new BranchListener() {
                @Override
                public void productsBranchesReceived(List<BranchPojo> branchPojos) {
                    Log.d(LOG_TAG, "done updating");
                    Toast.makeText(MyApplication.getInstance(), "Sync Complete", Toast.LENGTH_LONG).show();
                    BranchDataController.with(MyApplication.getInstance()).refresh();

                }

                @Override
                public void networkFailure(String localizedMessage) {
                    Log.d(LOG_TAG, localizedMessage);
                }

                @Override
                public void emptyDataSet(String localizedMessage) {
                    Log.d(LOG_TAG, localizedMessage);

                }
            });
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }

        }
        return newAccount;
    }
}