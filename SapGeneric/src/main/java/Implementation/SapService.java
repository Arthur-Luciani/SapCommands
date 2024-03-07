package Implementation;

import Controller.Sap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;

public abstract  class SapService implements SapInterface{
    @Autowired
    private ApplicationContext context;

    protected Sap sap;

    public void update(){
        System.out.println("UpdateMethod SapService");
        DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) context.getAutowireCapableBeanFactory();
        registry.destroySingleton("standart");
        registry.destroySingleton("sap");
        registry.destroySingleton("okCode");
        registry.destroySingleton("basic");
        registry.destroySingleton("basicKey");
        registry.destroySingleton("comboBox");
        registry.destroySingleton("gridControl");
        registry.destroySingleton("standart");
        registry.destroySingleton("tableControl");
        registry.destroySingleton("tree");
        registry.destroySingleton("userArea");
        sap = context.getBean(Sap.class);
    }

    public void finish(){
        System.out.println("FinishMethod SapService");
        sap.sapMessenger.disconnect();
    }
}
