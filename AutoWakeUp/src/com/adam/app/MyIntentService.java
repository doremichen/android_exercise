
package com.adam.app;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService
{

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.adam.app.action.FOO";

    private static final String ACTION_BAZ = "com.adam.app.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.adam.app.extra.PARAM1";

    private static final String EXTRA_PARAM2 = "com.adam.app.extra.PARAM2";
    
    
    private interface ACTION {
        String START = "com.adam.app.start";
        String STOP = "com.adam.app.stop";
    }
    
    
    private enum HandleIntent {
        
        Start(ACTION.START) {

            @Override
            public void process()
            {
                Utils.info("[HandleIntent@Start] enter");
                Test.newInstance().run();
            }
            
        },
        Stop(ACTION.STOP) {

            @Override
            public void process()
            {
                Utils.info("[HandleIntent@Stop] enter");
                Test.newInstance().stop();
            }
            
        };
        
        private String mAction;
        
        private HandleIntent(String action) {
            this.mAction = action;
        }
        
        public abstract void process();
        
        // FACTORY
        public static HandleIntent get(String action) {
            for (HandleIntent it: HandleIntent.values()) {
                if (it.mAction.equals(action)) {
                    return it;
                }
            }
            return null;
        }
        
    }
    
    
    public static void startTest(Context context)
    {
        Utils.info("[startTest] enter");
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION.START);
        context.startService(intent);
        Utils.info("[startTest] exit");
    }
    
    
    public static void stopTest(Context context)
    {
        Utils.info("[stopTest] enter");
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION.STOP);
        context.startService(intent);
        Utils.info("[stopTest] exit");
    }
    

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     * 
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2)
    {

        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     * 
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2)
    {

        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public MyIntentService()
    {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

        if (intent != null)
        {
            final String action = intent.getAction();
            HandleIntent request = HandleIntent.get(action);
            
            if (request == null) {
                Utils.showToast(this, "No process for this action!!! " + action);
                return;
            }
            
            request.process();
            
//            if (ACTION_FOO.equals(action))
//            {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            }
//            else if (ACTION_BAZ.equals(action))
//            {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2)
    {

        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2)
    {

        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
