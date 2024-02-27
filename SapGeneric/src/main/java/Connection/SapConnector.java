package Connection;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SapConnector {

    public ActiveXComponent getSession(ActiveXComponent MainSession, int sessionNumber) throws InterruptedException {
        ComThread.InitSTA();

        ActiveXComponent SAPROTWr, GUIApp, Connection, Obj, Session;
        Dispatch ROTEntry;
        Variant ScriptEngine;
        System.setProperty("jacob.debug", "true");
        ComThread.InitSTA(true);

        SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");
        ROTEntry = SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();

        ScriptEngine = Dispatch.call(ROTEntry, "GetScriptingEngine");
        GUIApp = new ActiveXComponent(ScriptEngine.toDispatch());

        Connection = new ActiveXComponent(GUIApp.invoke("Children", 0).toDispatch());
        Session = new ActiveXComponent(Connection.invoke("Children",0).toDispatch());

        boolean isSessionSet = false;
        do {
            try {
                MainSession = new ActiveXComponent(Connection.invoke("Children", sessionNumber).toDispatch());
                System.out.println(MainSession.getPropertyAsString("Name"));

                new ActiveXComponent(Connection.invoke("Children", sessionNumber + 1).toDispatch());//Create extra session for user
                isSessionSet = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Session.invoke("CreateSession");

                Thread.sleep(1000);
            }
        } while (!isSessionSet);
        return MainSession;
    }

    public SapMessenger getMessenger(int sessionNumber){
        ActiveXComponent session = null;
        try {
            session = getSession(session, sessionNumber);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new SapMessenger(session){
        };
    }

    public void disconnect(ActiveXComponent Session) {

        ComThread.Release();
        ComThread.quitMainSTA();
    }


}
