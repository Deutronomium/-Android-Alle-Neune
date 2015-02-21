package patrickengelkes.com.alleneune.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalInputTextWatcher implements TextWatcher {

    private String previousValue;
    private int cursorPosition;
    private boolean restoringPreviousValueFlag;
    private int digitsAfterZero;
    private EditText editText;

    public DecimalInputTextWatcher(EditText editText, int digitsAfterZero) {
        this.digitsAfterZero = digitsAfterZero;
        this.editText = editText;
        previousValue = "";
        restoringPreviousValueFlag = false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!restoringPreviousValueFlag) {
            previousValue = s.toString();
            cursorPosition = editText.getSelectionStart();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!restoringPreviousValueFlag) {

            if (!isValid(s.toString())) {
                restoringPreviousValueFlag = true;
                restorePreviousValue();
            }

        } else {
            restoringPreviousValueFlag = false;
        }
    }

    private void restorePreviousValue() {
        editText.setText(previousValue);
        editText.setSelection(cursorPosition);
    }

    private boolean isValid(String s) {
        Pattern patternWithDot = Pattern.compile("[0-9]*((\\.[0-9]{0," + digitsAfterZero + "})?)||(\\.)?");
        Pattern patternWithComma = Pattern.compile("[0-9]*((,[0-9]{0," + digitsAfterZero + "})?)||(,)?");

        Matcher matcherDot = patternWithDot.matcher(s);
        Matcher matcherComa = patternWithComma.matcher(s);

        return matcherDot.matches() || matcherComa.matches();
    }
}