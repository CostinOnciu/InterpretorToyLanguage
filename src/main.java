import Controller.Controller;
import Model.Exceptions.MyExceptions;
import Model.Expression.*;
import Model.Expression.ArithmeticExpression.MultiplicationExpression;
import Model.Expression.ArithmeticExpression.SumExpression;
import Model.Value.*;
import Model.Statement.*;
import Model.Type.*;
import Model.ProgramState;
import Repository.*;
import View.*;

import java.util.*;

public class main {
    public static void main(String[] args) throws MyExceptions {
        var statement = new ForkStatement(new VarDeclStatement(new IntType(),"x"));
        var stack = new Stack<Statement>();
        stack.push(statement);
        var prg = new ProgramState(stack,new HashMap<>(),new ArrayList<>(),new HashMap<>(),new HashMap<>(),1,0);
        statement.execute(prg);
    }
}

//620

// My implementation of garbage collector (IS BAD) work on it do it better

/*

 */

/*currentState.setHeap(currentState.getHeap().entrySet().stream().
                    filter(e->{
                        if(!(e.getValue() instanceof ReferenceValue))
                        {
                            var address = e.getKey();
                            var x= currentState.getSymbolTable().values().stream().map(val->{
                                if(val instanceof ReferenceValue)
                                    return val;
                                return null;
                            }).filter(Objects::nonNull).collect(Collectors.toList());
                            return x.stream().map(values->{
                                var y= (ReferenceValue)values;
                                return y.getAddress();
                            }).collect(Collectors.toList()).contains(address);
                        }
                        else{
                            var ref=(ReferenceValue)e.getValue();
                            while(ref.getValueType() instanceof ReferenceValue)
                            {
                                ref = (ReferenceValue) currentState.getHeap().get(ref.getAddress());
                            }
                            return currentState.getHeap().get(ref.getAddress())!=null;
                        }

                    }).
                    collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));*/