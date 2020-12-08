package Model.Exceptions;

public class IOException extends MyExceptions{
    public IOException() {
    }
    public IOException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "IOException{"+getMessage()+"}";
    }
}
