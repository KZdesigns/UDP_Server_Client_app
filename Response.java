public class Response {
    // Define the fields of the Response class
    public int TML;
    public int result;
    public int errorCode;
    public short requestID;
    // Define the constructor of the Response class
    public Response(int TML, int result, int errorCode, short requestID) {
        this.TML = TML;
        this.result = result;
        this.errorCode = errorCode;
        this.requestID = requestID;
    }
    // Define the toString method of the Response class
    public String toString() {
        String value = "TML: " + TML + "\n" +
                "result: " + result + "\n" +
                "errorCode: " + errorCode + "\n" +
                "requestID: " + requestID + "\n";
        return value; 
    }
}
