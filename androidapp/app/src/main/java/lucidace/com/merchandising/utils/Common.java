package lucidace.com.merchandising.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by kraiba on 18/03/16.
 */
public class Common {
    public static Bitmap getBitmapImages(final String imagePath)
    {

        Bitmap b= BitmapFactory.decodeFile(imagePath);
        Bitmap out = Bitmap.createScaledBitmap(b, 320, 480, false);

        File file = new File(imagePath);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
        } catch (Exception e) {}

        return  out;
    }
}
