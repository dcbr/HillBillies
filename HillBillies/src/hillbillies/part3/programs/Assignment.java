package hillbillies.part3.programs;

/**
 * Created by Bram on 27-4-2016.
 */
public class Assignment implements Statement {

    private final String variableName;
    private final Expression value;
    private final SourceLocation sourceLocation;

    public Assignment(String variableName, Expression value, SourceLocation sourceLocation){
        this.variableName = variableName;
        this.value = value;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public void execute() {
        value.evaluate();
    }
}
