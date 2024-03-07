package Connection;


public interface MessengerPool {
    SapMessenger getMessenger();
    boolean releaseMessenger (SapMessenger sapMessenger);

}