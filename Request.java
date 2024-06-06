public class Request {
    // Define the fields of the Request class
    public int TML;
    public int opCode;
    public int operand1;
    public int operand2;
    public short requestID;
    public int opNameLength;
    public String opName;
    // Define the constructor of the Request class
    public Request(int TML, int opCode, int operand1, int operand2, short requestID, int opNameLength, String opName) {
        this.TML = TML;
        this.opCode = opCode;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.requestID = requestID;
        this.opNameLength = opNameLength;
        this.opName = opName;
    }
    // Define the toString method of the Request class
    public String toString() {
        String value = "TML: " + TML + "\n" +
                "opCode: " + opCode + "\n" +
                "operand1: " + operand1 + "\n" +
                "operand2: " + operand2 + "\n" +
                "requestID: " + requestID + "\n" +
                "opNameLength: " + opNameLength + "\n" +
                "opName: " + opName + "\n";
        return value; 
    }
}
