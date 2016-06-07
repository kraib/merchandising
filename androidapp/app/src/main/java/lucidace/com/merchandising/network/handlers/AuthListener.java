package lucidace.com.merchandising.network.handlers;

import lucidace.com.merchandising.models.common.User;

/**
 * Created by kraiba on 16/05/16.
 */
public interface AuthListener {
    void  loginSuccessFull(User user);
    void  loginFailed();
    void  networkFailed();
}
