package patrickengelkes.com.alleneune.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.inject.Inject;

import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.FineArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.FinePaymentController;
import patrickengelkes.com.alleneune.entities.objects.Fine;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by patrickengelkes on 26/02/15.
 */
public class FinePaymentDialog extends Dialog {

    protected Spinner fineSpinner;
    protected Button addFinePaymentButton;
    @Inject
    private FinePaymentController finePaymentController;

    public FinePaymentDialog(Context context, List<Fine> fineList, final int userID, final int eventID) {
        super(context);

        //inject all member variables
        final RoboInjector injector = RoboGuice.getInjector(context);
        injector.injectMembersWithoutViews(this);

        this.setContentView(R.layout.dialog_fine_payment);

        setTitle(context.getString(R.string.add_fine_to_user));

        fineSpinner = (Spinner) findViewById(R.id.fine_spinner);
        FineArrayAdapter fineArrayAdapter = new FineArrayAdapter((Activity) context,
                fineList);
        fineSpinner.setAdapter(fineArrayAdapter);
        addFinePaymentButton = (Button) findViewById(R.id.add_fine_payment_button);
        addFinePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fine selectedFine = (Fine) fineSpinner.getSelectedItem();
                finePaymentController.create(userID, eventID, selectedFine.getId());
                dismiss();
            }
        });
    }
}
