package lucidace.com.merchandising.network.handlers;

import java.util.List;

import lucidace.com.merchandising.models.branches.BranchPojo;
import lucidace.com.merchandising.models.products.ProductCategory;

/**
 * Created by kraiba on 16/05/16.
 */
public interface BranchListener {
    void productsBranchesReceived(List<BranchPojo> branchPojos);
    void networkFailure(String localizedMessage);
    void emptyDataSet(String localizedMessage);
}
