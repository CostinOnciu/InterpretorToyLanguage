package Model.Expression;
import Model.Exceptions.MyExceptions;
import Model.Value.Value;

import java.util.*;

public interface Expression {
    Value evaluate(Map<String,Value> symbolTable, Map<Integer,Value> heap) throws MyExceptions;
}
