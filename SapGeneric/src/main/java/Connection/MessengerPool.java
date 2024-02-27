package Connection;

import SapGeneric.SapMessenger;

public interface MessengerPool {
    SapMessenger getMessenger();
    boolean releaseMessenger (SapMessenger sapMessenger);

}