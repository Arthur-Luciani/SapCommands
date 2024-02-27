package ErrorHandler;

import SapGeneric.SapMessenger;
import com.jacob.activeX.ActiveXComponent;
import org.springframework.beans.factory.annotation.Autowired;

public class StatusBarHandler {

    @Autowired
    private SapMessenger sapMessenger;
    private static final String STATUS_BAR_ID = "wnd[0]/sbar";

    private ActiveXComponent statusObj;

    public void setStatusBar() {
        sapMessenger.isExisting(STATUS_BAR_ID);
        statusObj = new ActiveXComponent(sapMessenger.session.invoke("FindById", STATUS_BAR_ID).toDispatch());
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", "wnd[0]").toDispatch());
    }

    public void handleStatus() {
        long startTime = System.nanoTime();

        setStatusBar();
        String messageType = statusObj.getPropertyAsString("MessageType");

        switch (messageType) {
            case "W", "I" -> {
                sapMessenger.obj.invoke("sendKey", 0);
            }
        }

        long endTime = System.nanoTime();
        System.out.println(((endTime - startTime) / 1000000) + " milliseconds execution time");
    }
}