package fun;

public class FunResponse {
    private int numSyntaxErrors;
    private int numContextualErrors;
    private String objectCode;
    private String output;

    public FunResponse() {};

    public int getNumSyntaxErrors() {
        return numSyntaxErrors;
    }

    public void setNumSyntaxErrors(int numSyntaxErrors) {
        this.numSyntaxErrors = numSyntaxErrors;
    }

    public int getNumContextualErrors() {
        return numContextualErrors;
    }

    public void setNumContextualErrors(int numContextualErrors) {
        this.numContextualErrors = numContextualErrors;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
