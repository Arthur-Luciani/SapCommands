package Controller;

import Components.*;
import Connection.SapConnector;
import Connection.SapMessenger;
import Exceptions.SapException;


public class Sap {
    private int session;
    public SapMessenger sapMessenger;
    public Basic basic;
    public BasicKey basicKey;
    public ComboBox comboBox;
    public GridControl gridControl;
    public OkCode okCode;
    public TableControl tableControl;
    public UserArea userArea;
    public Tree tree;

    public Sap(int session) {
        SapConnector.initJacob();
        this.session = session;
        SapMessenger messenger = null;
        try {
            messenger = SapConnector.getMessenger(session);
        } catch (SapException e) {
            System.out.println(e.getMessage());
        }
        setMessenger(messenger);
    }

    public Sap(int session, SapMessenger sapMessenger) {
        this.session = session;
        setMessenger(sapMessenger);
    }

    private void setMessenger(SapMessenger sapMessenger) {
        this.sapMessenger = sapMessenger;
        this.basic = new Basic(sapMessenger);
        this.basicKey = new BasicKey(sapMessenger);
        this.comboBox = new ComboBox(sapMessenger);
        this.gridControl = new GridControl(sapMessenger);
        this.okCode = new OkCode(sapMessenger);
        this.tableControl = new TableControl(sapMessenger);
        this.userArea = new UserArea(sapMessenger);
        this.tree = new Tree(sapMessenger);
    }
}
