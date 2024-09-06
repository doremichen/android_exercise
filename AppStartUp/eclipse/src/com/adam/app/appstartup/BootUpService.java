/**
 * Bootup Service
 *       Parse ui data xml file to trigger UI
 */
package com.adam.app.appstartup;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BootUpService extends IntentService {
    // Trigger ui
    private static final String ACTION_TRIGER_UI = "com.adam.app.appstartup.action.triger.ui";


    public static void startBootUpService(Context context) {
        Utils.info("startBootUpService@BootUpService");
        Intent intent = new Intent(context, BootUpService.class);
        intent.setAction(ACTION_TRIGER_UI);
        context.startService(intent);
    }
    
    
    public BootUpService() {
        super("BootUpService");
    }

    
    
    
    @Override
    public void onCreate() {
        Utils.info("onCreate@BootUpService");
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Utils.info("onHandleIntent@BootUpService");
        if (intent != null) {
            final String action = intent.getAction();
            
            if (ACTION_TRIGER_UI.equals(action)) {
                Utils.info("delay 5 sec to start ui~~~");
//                while(!getCurrentActivityName(this).contains("SCR0062"));
                // delay 5 second
                Utils.delay(20000L);
                Utils.info("start ui~~~");
                // start ui
                Intent it = new Intent(this, MainActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(it);
            }
        }
    }

    
}
