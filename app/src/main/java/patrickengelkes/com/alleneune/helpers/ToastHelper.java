package patrickengelkes.com.alleneune.helpers;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by patrickengelkes on 21/02/15.
 */
public class ToastHelper {

    public static void centerToast(Toast toast) {
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }
}
