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
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by patrickengelkes on 14/02/15.
 */
public class CreateDrinkDialog extends Dialog{
    @Inject
    protected CurrentClub currentClub;
    @Inject
    protected DrinkController drinkController;

    protected EditText nameEditText;
    protected EditText priceEditText;
    protected Button createDrinkButton;


    public CreateDrinkDialog(final Context context) {
        super(context);
        final RoboInjector injector = RoboGuice.getInjector(context);

        injector.injectMembersWithoutViews(this);

        this.setContentView(R.layout.dialog_create_drink);
        this.setTitle(context.getString(R.string.create_drink_button));

        nameEditText = (EditText) findViewById(R.id.drink_name_edit_text);
        priceEditText = (EditText) findViewById(R.id.drink_price_edit_text);
        priceEditText.addTextChangedListener(new DecimalInputTextWatcher(priceEditText, 2));
        createDrinkButton = (Button) findViewById(R.id.create_drink_button);
        createDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim();
                double price = Double.valueOf(priceEditText.getText().toString().trim());

                ApiCall response = drinkController.create(name, price, currentClub.getClubID());
                if (response == ApiCall.CREATED) {
                    dismiss();
                } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                    ErrorDialog dialog = new ErrorDialog(context,
                            context.getString(R.string.drink_used_warning));
                    dialog.show();
                }
            }
        });
    }
}
