package Model.Exceptions;

public class InvalidCondition extends MyExceptions{
    public InvalidCondition() {
    }

    public InvalidCondition(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "InvalidCondition{"+getMessage()+"}";
    }
}
