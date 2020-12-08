package Model.Exceptions;

public class AlreadyDeclaredVariable extends MyExceptions{
    public AlreadyDeclaredVariable() {
    }

    public AlreadyDeclaredVariable(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "AlreadyDeclaredVariable{"+getMessage()+"}";
    }
}
