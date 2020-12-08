package Model.Exceptions;

public class VariableTypeDifferent extends MyExceptions{
    public VariableTypeDifferent() {
    }

    public VariableTypeDifferent(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "VariableTypeDifferent{"+getMessage()+"}";
    }
}
