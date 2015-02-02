package patrickengelkes.com.alleneune;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import patrickengelkes.com.alleneune.activities.MainActivity;
import patrickengelkes.com.alleneune.activities.PhoneNumberActivity;
import patrickengelkes.com.alleneune.activities.SignUpActivity;

/**
 * Created by patrickengelkes on 30/01/15.
 */
public class TestSignUp extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public TestSignUp() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testSignUp() {
        Button signUpButton = (Button) solo.getView(R.id.signUpButton);

        solo.clickOnView(signUpButton);

        assertTrue(solo.waitForActivity(SignUpActivity.class));

        EditText userNameEditText = (EditText) solo.getView(R.id.sign_up_name_edit_text);
        EditText passwordEditText = (EditText) solo.getView(R.id.sign_up_password_edit_text);
        EditText passwordConfirmationEditText = (EditText) solo.getView(R.id.password_confirmation_edit_text);
        EditText emailEditText = (EditText) solo.getView(R.id.email_edit_text);
        Button continueButton = (Button) solo.getView(R.id.continue_button);

        NameGenerator nameGenerator = new NameGenerator();

        solo.enterText(userNameEditText, nameGenerator.getName());
        solo.enterText(passwordEditText, "test");
        solo.enterText(passwordConfirmationEditText, "test");
        solo.enterText(emailEditText, nameGenerator.getName());

        solo.clickOnView(continueButton);
        assertTrue(solo.waitForActivity(PhoneNumberActivity.class));
    }

    class NameGenerator {

        private List<String> vocals = new ArrayList<String>();
        private List<String> startConsonants = new ArrayList<String>();
        private List<String> endConsonants = new ArrayList<String>();
        private List<String> nameInstructions = new ArrayList<String>();

        public NameGenerator() {
            String demoVocals[] = {"a", "e", "i", "o", "u", "ei", "ai", "ou", "j",
                    "ji", "y", "oi", "au", "oo"};

            String demoStartConsonants[] = {"b", "c", "d", "f", "g", "h", "k",
                    "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z",
                    "ch", "bl", "br", "fl", "gl", "gr", "kl", "pr", "st", "sh",
                    "th"};

            String demoEndConsonants[] = {"b", "d", "f", "g", "h", "k", "l", "m",
                    "n", "p", "r", "s", "t", "v", "w", "z", "ch", "gh", "nn", "st",
                    "sh", "th", "tt", "ss", "pf", "nt"};

            String nameInstructions[] = {"vd", "cvdvd", "cvd", "vdvd"};

            this.vocals.addAll(Arrays.asList(demoVocals));
            this.startConsonants.addAll(Arrays.asList(demoStartConsonants));
            this.endConsonants.addAll(Arrays.asList(demoEndConsonants));
            this.nameInstructions.addAll(Arrays.asList(nameInstructions));
        }

        /**
         * The names will look like this
         * (v=vocal,c=startConsonsonant,d=endConsonants): vd, cvdvd, cvd, vdvd
         *
         * @param vocals          pass something like {"a","e","ou",..}
         * @param startConsonants pass something like {"s","f","kl",..}
         * @param endConsonants   pass something like {"th","sh","f",..}
         */
        public NameGenerator(String[] vocals, String[] startConsonants,
                             String[] endConsonants) {
            this.vocals.addAll(Arrays.asList(vocals));
            this.startConsonants.addAll(Arrays.asList(startConsonants));
            this.endConsonants.addAll(Arrays.asList(endConsonants));
        }

        /**
         * see {@link NameGenerator#NameGenerator(String[], String[], String[])}
         *
         * @param vocals
         * @param startConsonants
         * @param endConsonants
         * @param nameInstructions Use only the following letters:
         *                         (v=vocal,c=startConsonsonant,d=endConsonants)! Pass something
         *                         like {"vd", "cvdvd", "cvd", "vdvd"}
         */
        public NameGenerator(String[] vocals, String[] startConsonants,
                             String[] endConsonants, String[] nameInstructions) {
            this(vocals, startConsonants, endConsonants);
            this.nameInstructions.addAll(Arrays.asList(nameInstructions));
        }

        public String getName() {
            return firstCharUppercase(getNameByInstructions(getRandomElementFrom(nameInstructions)));
        }

        private int randomInt(int min, int max) {
            return (int) (min + (Math.random() * (max + 1 - min)));
        }

        private String getNameByInstructions(String nameInstructions) {
            String name = "";
            int l = nameInstructions.length();

            for (int i = 0; i < l; i++) {
                char x = nameInstructions.charAt(0);
                switch (x) {
                    case 'v':
                        name += getRandomElementFrom(vocals);
                        break;
                    case 'c':
                        name += getRandomElementFrom(startConsonants);
                        break;
                    case 'd':
                        name += getRandomElementFrom(endConsonants);
                        break;
                }
                nameInstructions = nameInstructions.substring(1);
            }
            return name;
        }

        private String firstCharUppercase(String name) {
            return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
        }

        private String getRandomElementFrom(List<String> v) {
            return v.get(randomInt(0, v.size() - 1));
        }
    }
}
