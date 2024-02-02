package Conn;

import SapGeneric.Standart;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SapConn {

    public ActiveXComponent getSession(ActiveXComponent MainSession, int sessionNumber) throws InterruptedException {
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
                //MainSession.invoke("ClearErrorList");
                System.out.println(MainSession.getPropertyAsString("Name"));
                System.out.println("Response time " + MainSession.getPropertyAsComponent("Info").getPropertyAsInt("ResponseTime"));
                System.out.println("Group " + MainSession.getPropertyAsComponent("Info").getPropertyAsString("Group"));
                System.out.println("Flush queue " + MainSession.getPropertyAsComponent("Info").getPropertyAsInt("Flushes"));
                System.out.println("IsSlow " + MainSession.getPropertyAsComponent("Info").getPropertyAsBoolean("IsLowSpeedConnection"));

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

    public Standart getStandart(int sessionNumber){
        ActiveXComponent session = null;
        try {
            session = getSession(session, sessionNumber);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Standart(session){

        };
    }

    public void disconnect(ActiveXComponent Session) {
        ComThread.Release();
        ComThread.quitMainSTA();

    }


}
