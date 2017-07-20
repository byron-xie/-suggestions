package suggestions.example.com.utils;

/**
 * Created by yang on 16-9-9.
 */
public class CommonDrive {
    static {
        System.loadLibrary("byron_version");
    }

    public static native String getHWVersion();

    public static native String getHWVersionOld(String str);

}
