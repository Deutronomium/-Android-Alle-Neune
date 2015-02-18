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
import patrickengelkes.com.alleneune.array_adapters.adapters.DrinksArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by patrickengelkes on 14/02/15.
 */
public class DrinkDialog extends Dialog {
    @Inject
    protected CurrentClub currentClub;
    @Inject
    protected DrinkController drinkController;

    protected EditText nameEditText;
    protected EditText priceEditText;
    protected Button drinkButton;


    public DrinkDialog(final Context context, final DrinksArrayAdapter drinksArrayAdapter,
                       Drink drink) {
        super(context);
        final RoboInjector injector = RoboGuice.getInjector(context);

        injector.injectMembersWithoutViews(this);

        this.setContentView(R.layout.dialog_drink);

        nameEditText = (EditText) findViewById(R.id.drink_name_edit_text);
        priceEditText = (EditText) findViewById(R.id.drink_price_edit_text);
        priceEditText.addTextChangedListener(new DecimalInputTextWatcher(priceEditText, 2));
        drinkButton = (Button) findViewById(R.id.drink_button);

        if (drink != null) {
            nameEditText.setText(drink.getName());
            priceEditText.setText(String.valueOf(drink.getPrice()));
            drinkButton.setText(context.getString(R.string.update_drink));
        } else {
            setTitle(context.getString(R.string.create_drink));
            drinkButton.setText(context.getString(R.string.create_drink));
            drinkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEditText.getText().toString().trim();
                    double price = Double.valueOf(priceEditText.getText().toString().trim());

                    Drink drink = new Drink(name, price, currentClub.getClubID());

                    ApiCall response = drinkController.create(drink);
                    if (response == ApiCall.CREATED) {
                        dismiss();
                        drinksArrayAdapter.add(drink);
                    } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                        ErrorDialog dialog = new ErrorDialog(context,
                                context.getString(R.string.drink_used_warning));
                        dialog.show();
                    }
                }
            });
        }

    }
}
