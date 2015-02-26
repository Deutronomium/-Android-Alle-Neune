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
import patrickengelkes.com.alleneune.adapters.array_adapters.DrinkArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.DrinkPaymentController;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by patrickengelkes on 25/02/15.
 */
public class DrinkPaymentDialog extends Dialog {

    protected Spinner drinkSpinner;
    protected Button addDrinkPaymentButton;
    @Inject
    private DrinkPaymentController drinkPaymentController;

    public DrinkPaymentDialog(Context context, List<Drink> drinkList, final int userID, final int eventID) {
        super(context);

        //inject all member variables
        final RoboInjector injector = RoboGuice.getInjector(context);
        injector.injectMembersWithoutViews(this);

        this.setContentView(R.layout.dialog_drink_payment);

        setTitle(context.getString(R.string.add_drink_to_user));

        drinkSpinner = (Spinner) findViewById(R.id.drink_spinner);
        DrinkArrayAdapter drinkArrayAdapter = new DrinkArrayAdapter((Activity) context,
                drinkList);
        drinkSpinner.setAdapter(drinkArrayAdapter);
        addDrinkPaymentButton = (Button) findViewById(R.id.add_drink_payment_button);
        addDrinkPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drink selectedDrink = (Drink) drinkSpinner.getSelectedItem();
                drinkPaymentController.create(userID, eventID, selectedDrink.getId());
                dismiss();
            }
        });
    }
}
