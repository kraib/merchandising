package lucidace.com.merchandising.utils;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by kraiba on 27/01/16.
 */
public interface PhotoUtilsCallBack {
    void onImageTaken(Bitmap image, Uri mImageCaptureUri);
    void onVideoTaken(Bitmap image, Uri mImageCaptureUri);
}
