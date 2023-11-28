package Conn;

import SapGeneric.Standart;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.springframework.boot.web.servlet.server.Session;

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
        MainSession = new ActiveXComponent(Connection.invoke("Children", sessionNumber).toDispatch());

        Session = new ActiveXComponent(MainSession.invoke("Children",0).toDispatch());

        try {
            new ActiveXComponent(Connection.invoke("Children",1).toDispatch());
        } catch (Exception e) {
            MainSession.invoke("CreateSession");
        }
        MainSession.invoke("LockSessionUI");

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

    public void unlock(ActiveXComponent Session) {
        Session.invoke("UnlockSessionUI");
    }


}
