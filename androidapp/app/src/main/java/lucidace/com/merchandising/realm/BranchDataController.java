package lucidace.com.merchandising.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import lucidace.com.merchandising.models.branches.Branch;


/**
 * Created by kraiba on 16/05/16.
 */
public class BranchDataController {


        private static BranchDataController instance;
        private final Realm realm;

        public BranchDataController(Application application) {
            realm = Realm.getDefaultInstance();
        }

        public static BranchDataController with(Fragment fragment) {

            if (instance == null) {
                instance = new BranchDataController(fragment.getActivity().getApplication());
            }
            return instance;
        }

        public static BranchDataController with(Activity activity) {

            if (instance == null) {
                instance = new BranchDataController(activity.getApplication());
            }
            return instance;
        }

        public static BranchDataController with(Application application) {

            if (instance == null) {
                instance = new BranchDataController(application);
            }
            return instance;
        }

        public static BranchDataController getInstance() {

            return instance;
        }

        public Realm getRealm() {

            return realm;
        }


        public void refresh() {
            realm.refresh();
        }

        public void clearAll() {

            realm.beginTransaction();
            realm.clear(Branch.class);
            realm.commitTransaction();
        }

        public RealmResults<Branch> getBranches() {

            return realm.where(Branch.class).findAll();
        }

        public Branch getBranch(String id) {

            return realm.where(Branch.class).equalTo("branch_id", id).findFirst();
        }

        public boolean hasCategories() {
            return !realm.allObjects(Branch.class).isEmpty();
        }



    }
