package Conn;

import SapGeneric.Standart;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SapConn {

    public ActiveXComponent getSession(ActiveXComponent Session, int sessionNumber) throws InterruptedException {
        ActiveXComponent SAPROTWr, GUIApp, Connection, Obj;
        Dispatch ROTEntry;
        Variant ScriptEngine;
        System.setProperty("jacob.debug", "true");
        ComThread.InitSTA();

        SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");

        ROTEntry = SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();
        ScriptEngine = Dispatch.call(ROTEntry, "GetScriptingEngine");
        GUIApp = new ActiveXComponent(ScriptEngine.toDispatch());

        Connection = new ActiveXComponent(GUIApp.invoke("Children", 0).toDispatch());
        Session = new ActiveXComponent(Connection.invoke("Children", sessionNumber).toDispatch());

        Obj = new ActiveXComponent(Session.invoke("FindById", "wnd[0]/tbar[0]/okcd").toDispatch());
        Obj.setProperty("Text", "/n");
        return Session;

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


}
