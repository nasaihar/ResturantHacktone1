package exceptions;

public class InsufficientMoney extends RuntimeException{

    public InsufficientMoney(String message)
    {
        super(message);
    }
}
