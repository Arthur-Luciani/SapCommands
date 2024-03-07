package Connection;

import Exceptions.SapException;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SapConnector {

    /**
     * Set initial properties to grant better performance in automation
     * and prevent some memory leaks
     */
        public static void initJacob() {
        System.setProperty("com.jacob.includeAllClassesInROT", "false");
        System.setProperty("com.jacob.autogc", "true"); //Jacob ROT will create Map as WeakHashMap, it allows run GC
        //Set classes to not include in ROT
        System.setProperty(Variant.class.getName() + ".PutInROT", "false");
        System.setProperty(Dispatch.class.getName() + ".PutInROT", "false");
        System.setProperty(ActiveXComponent.class.getName() + ".PutInROT", "false");
    }

    /**
     * Get Session as ActiveXComponent and set that Session as Unique
     * @param sessionNumber Window number to control
     * @return Session as ActiveXComponent
     * @throws SapException Invalid session number
     */
    public static ActiveXComponent getSession(int sessionNumber) throws SapException {
        ActiveXComponent sapRotWr, guiApp, connection, session, mainSession = null;
        Dispatch rotEntry;
        Variant scriptEngine;

        if (sessionNumber > 5) {
            throw new SapException("Session number invalid (greater than 5)");
        }

        if (!ComThread.haveSTA) {
            ComThread.InitSTA();
        }

        sapRotWr = new ActiveXComponent("SapROTWr.SapROTWrapper");
        rotEntry = sapRotWr.invoke("GetROTEntry", "SAPGUI").toDispatch();
        scriptEngine = Dispatch.call(rotEntry, "GetScriptingEngine");
        guiApp = new ActiveXComponent(scriptEngine.toDispatch());

        connection = new ActiveXComponent(guiApp.invoke("Children", 0).toDispatch());
        session = new ActiveXComponent(connection.invoke("Children",0).toDispatch());

        boolean isSessionSet = false;
        do {
            try {
                mainSession = new ActiveXComponent(connection.invoke("Children", sessionNumber).toDispatch());
                if (sessionNumber < 5) {
                    new ActiveXComponent(connection.invoke("Children", sessionNumber + 1).toDispatch());//Create extra session for user
                }
                isSessionSet = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                session.invoke("CreateSession");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } while (!isSessionSet);
        return mainSession;
    }

    public static SapMessenger getMessenger(int sessionNumber) throws SapException {
        return new SapMessenger(getSession(sessionNumber), sessionNumber);
    }

    @Deprecated
    public void disconnect(ActiveXComponent Session) {
        ComThread.Release();
        ComThread.quitMainSTA();
    }


}
