package patrickengelkes.com.alleneune.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.HashMap;
import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.helpers.DecimalInputTextWatcher;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.DrinkArrayAdapter;
import patrickengelkes.com.alleneune.entities.controllers.DrinkController;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import patrickengelkes.com.alleneune.enums.ApiCall;
import patrickengelkes.com.alleneune.helpers.ToastHelper;
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


    public DrinkDialog(final Context context, final DrinkArrayAdapter drinkArrayAdapter,
                       final List<Drink> drinkList, final Drink drink) {
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
            drinkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEditText.getText().toString().trim();
                    double price = Double.valueOf(priceEditText.getText().toString().trim());

                    HashMap<String, Object> updateMap = new HashMap<String, Object>();
                    if (!name.equals(drink.getName())) {
                        updateMap.put(Drink.NAME, name);
                    }
                    if (price != drink.getPrice()) {
                        updateMap.put(Drink.PRICE, price);
                    }
                    if (updateMap.size() > 0) {
                        ApiCall response = drinkController.update(updateMap, drink);
                        if (response == ApiCall.UPDATED) {
                            drink.setName(name);
                            drink.setPrice(price);
                            drinkArrayAdapter.notifyDataSetChanged();
                            dismiss();
                        } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                            ErrorDialog dialog = new ErrorDialog(context,
                                    context.getString(R.string.drink_used_warning));
                            dialog.show();
                        }
                    } else {
                        ToastHelper.centerToast(Toast.makeText(context,
                                context.getString(R.string.no_drink_updates_message), Toast.LENGTH_SHORT));
                        dismiss();
                    }
                }
            });
        } else {
            setTitle(context.getString(R.string.create_drink));
            drinkButton.setText(context.getString(R.string.create_drink));
            drinkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEditText.getText().toString().trim();
                    double price = Double.valueOf(priceEditText.getText().toString().trim());

                    Drink drink = new Drink(name, price, currentClub.getId());

                    ApiCall response = drinkController.create(drink);
                    if (response == ApiCall.CREATED) {
                        dismiss();
                        drinkList.add(drink);
                        drinkArrayAdapter.notifyDataSetChanged();
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
