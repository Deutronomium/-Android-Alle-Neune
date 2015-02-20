package patrickengelkes.com.alleneune.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.DecimalInputTextWatcher;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.FineArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.FineController;
import patrickengelkes.com.alleneune.entities.objects.Fine;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by patrickengelkes on 19/02/15.
 */
public class FineDialog extends Dialog {
    @Inject
    protected CurrentClub currentClub;
    @Inject
    protected FineController fineController;

    protected EditText nameEditText;
    protected EditText amountEditText;
    protected Button fineButton;

    public FineDialog(final Context context, final FineArrayAdapter fineArrayAdapter,
                      Fine fine) {
        super(context);
        final RoboInjector injector = RoboGuice.getInjector(context);

        injector.injectMembersWithoutViews(this);

        this.setContentView(R.layout.dialog_fine);

        nameEditText = (EditText) findViewById(R.id.fine_name_edit_text);
        amountEditText = (EditText) findViewById(R.id.fine_amount_edit_text);
        amountEditText.addTextChangedListener(new DecimalInputTextWatcher(amountEditText, 2));
        fineButton = (Button) findViewById(R.id.fine_button);

        if (fine != null) {
            nameEditText.setText(fine.getName());
            amountEditText.setText(String.valueOf(fine.getAmount()));
            fineButton.setText(context.getString(R.string.update_fine));
        } else {
            setTitle(context.getString(R.string.create_fine));
            fineButton.setText(context.getString(R.string.create_fine));
            fineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEditText.getText().toString().trim();
                    double amount = Double.valueOf(amountEditText.getText().toString().trim());

                    Fine fine = new Fine(name, amount, currentClub.getClubID());

                    ApiCall response = fineController.create(fine);
                    if (response == ApiCall.CREATED) {
                        dismiss();
                        fineArrayAdapter.add(fine);
                    } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                        ErrorDialog dialog = new ErrorDialog(context,
                                context.getString(R.string.fine_used_warning));
                        dialog.show();
                    }
                }
            });
        }

    }
}
