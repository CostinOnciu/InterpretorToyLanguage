package Model.Exceptions;

public class MyExceptions extends Exception{
    public MyExceptions() {
    }

    public MyExceptions(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "MyExceptions{"+getMessage()+"}";
    }
}
