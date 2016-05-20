package hillbillies.part3.programs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bram on 8-5-2016.
 */
public class VariableCollection {

    private Map<String, Variable<?>> variables;

    public VariableCollection(){
        this.variables = new HashMap<>();
    }

    public <T> void add(String variableName, T variableValue) throws IllegalArgumentException{
        if(this.contains(variableName))
            throw new IllegalArgumentException("There is already a variable assigned with this name. To assign a new value, use the assign method.");
        variables.put(variableName, new Variable<>(variableName, variableValue));
    }

    public <T> void assign(String variableName, T newValue) throws ClassCastException{
        this.<T>get(variableName).assign(newValue);
    }

    private <T> Variable<T> get(String variableName) throws ClassCastException{
        Variable<?> value = variables.get(variableName);
        try {
            return (Variable<T>) value;
        }catch(ClassCastException e){
            throw new ClassCastException("The variable's name and type do not match.");
        }
    }

    public <T> T getValue(String variableName) throws ClassCastException{
        return this.<T>get(variableName).getValue();
    }

    public boolean contains(String variableName){
        return this.variables.containsKey(variableName);
    }

    private static class Variable<T>{

        private String variableName;
        private T value;

        private Variable(String variableName, T value){
            this.variableName = variableName;
            this.value = value;
        }

        private void assign(T newValue) throws ClassCastException{
            if(!this.value.getClass().isInstance(newValue))
                throw new ClassCastException("The variable's name and type do not match.");
            this.value = newValue;
        }

        private String getName(){
            return this.variableName;
        }

        private T getValue(){
            return this.value;
        }

    }
}
