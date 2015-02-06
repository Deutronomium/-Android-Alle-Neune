import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

public class CustomTestRunner extends RobolectricTestRunner {
    
    public CustomTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String appRoot = "Macintosh HD/Users/patrickengelkes/Development/workspace-android/AlleNeune/app/src/main";
        String manifestPath = appRoot + "AndroidManifest.xml";
        String resDir = appRoot + "res";
        String assetsDir = appRoot + "assets";
        AndroidManifest manifest = createAppManifest(Fs.fileFromPath(manifestPath),
        		Fs.fileFromPath(resDir),
        		Fs.fileFromPath(assetsDir));
        		
        //manifest.setPackageName("patrickengelkes.com.alleneune");
        // Robolectric is already going to look in the  'app' dir ...
        // so no need to add to package name
        return manifest;
    }
}