package utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.DPIUtil;

/**
 * Utils
 * 
 * @author Phillip Beauvoir
 */
public class Utils {

    /**
     * System property to enable to scale the application on runtime when a DPI
     * change is detected.
     * <ul>
     * <li>"force": the application is scaled on DPI changes even if an unsupported
     * value for swt.autoScale are defined. See allowed values in
     * {@link org.eclipse.swt.internal.AutoScaleCalculation}.</li>
     * <li>"true": the application is scaled on DPI changes</li>
     * <li>"false": the application will remain in its initial scaling</li>
     * </ul>
     * <b>Important:</b> This flag is only parsed and used on Win32. Setting it to
     * true on GTK or cocoa will be ignored.
     */
    public static final String SWT_AUTOSCALE_UPDATE_ON_RUNTIME = "swt.autoScale.updateOnRuntime";
   
    /**
     * System property that controls the enabled of the Draw2D autoScale
     * functionality (replacing the native SWT autoScale functionality).
     * <p>
     * Currently it only has effects when executed on Windows.
     * </p>
     *
     * <ul>
     * <li><b>true</b>: autoScale functionality is enabled</li>
     * <li><b>false</b>: autoScale functionality is disabled<</li>
     *
     * </ul>
     * The current default is "true".
     */
    public static final String DRAW2D_ENABLE_AUTOSCALE = "draw2d.enableAutoscale"; 
    
    /**
     * @return true if on Windows and "draw2d.enableAutoscale" is true and SWT version > 4971
     */
    public static boolean isAutoScaleEnabled() {
        return "win32".equals(SWT.getPlatform())
                && Boolean.getBoolean(DRAW2D_ENABLE_AUTOSCALE)
                && SWT.getVersion() >= 4971; // SWT 2025-12 release or higher
    }
    
    /**
     * @return the device zoom as a float - 1.0, 1.25, 1.5, 1.75, 2.0 etc
     */
    @SuppressWarnings("restriction")
    public static float getDeviceZoom() {
        // if "swt.autoScale.updateOnRuntime" is true we can call DPIUtil.getDeviceZoom() else we have to call DPIUtil.getNativeDeviceZoom()
        int deviceZoom = Boolean.getBoolean(SWT_AUTOSCALE_UPDATE_ON_RUNTIME) ? DPIUtil.getDeviceZoom() : DPIUtil.getNativeDeviceZoom();
        return deviceZoom / 100.0f;
    }

}
