import java.io.*;
import java.nio.charset.StandardCharsets;

public class RequestEncoder {
    
    public byte[] encode(Request request) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        // Write the fields of the request
        out.writeByte(request.TML);                    // Write TML (1 byte)
        out.writeByte(request.opCode);                 // Write Op Code (1 byte)
        out.writeInt(request.operand1);                // Write Operand 1 (4 bytes)
        out.writeInt(request.operand2);                // Write Operand 2 (4 bytes)
        out.writeShort(request.requestID);             // Write Request ID (2 bytes)
        out.writeByte(request.opNameLength);           // Write Op Name Length (1 byte)

        byte[] opNameBytes = request.opName.getBytes(StandardCharsets.UTF_16);
        out.write(opNameBytes);                        // Write Op Name (variable length)
        // Flush the output stream
        out.flush();
        // Return the byte array
        return buf.toByteArray();
    }
}