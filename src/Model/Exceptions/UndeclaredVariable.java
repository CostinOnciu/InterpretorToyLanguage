package Model.Exceptions;

public class UndeclaredVariable extends MyExceptions{
    public UndeclaredVariable() {
    }

    public UndeclaredVariable(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Undeclared Variable{"+getMessage()+"}";
    }
    
}