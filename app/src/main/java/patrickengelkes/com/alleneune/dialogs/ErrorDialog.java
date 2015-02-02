package patrickengelkes.com.alleneune.dialogs;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by patrickengelkes on 30/01/15.
 */
public class ErrorDialog extends AlertDialog.Builder {
    public ErrorDialog(Context context, String errorMessage) {
        super(context);
        this.setTitle(errorMessage);
        this.setCancelable(true);
        this.setPositiveButton(android.R.string.ok, null);
    }
}
