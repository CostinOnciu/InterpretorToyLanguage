package Model.Exceptions;

public class DivisionBy0 extends Exception{
    public DivisionBy0() {
    }

    public DivisionBy0(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "DivisionBy0{"+getMessage()+"}";
    }
}
