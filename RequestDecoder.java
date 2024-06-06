import java.io.*;
import java.nio.charset.*;

public class RequestDecoder {
    public Request decode(byte[] data) throws IOException {
        // Create a new DataInputStream to read the data
        ByteArrayInputStream buf = new ByteArrayInputStream(data);
        DataInputStream in = new DataInputStream(buf);
        // Read the fields of the request
        int TML = in.readUnsignedByte();                    // Read TML (1 byte)
        int opCode = in.readUnsignedByte();                 // Read Op Code (1 byte)
        int operand1 = in.readInt();                        // Read Operand 1 (4 bytes)
        int operand2 = in.readInt();                        // Read Operand 2 (4 bytes)
        short requestID = in.readShort();                   // Read Request ID (2 bytes)
        int opNameLength = in.readUnsignedByte();           // Read Op Name Length (1 byte)

        byte[] opNameBytes = new byte[opNameLength * 2];    // Allocate byte array for Op Name
        in.readFully(opNameBytes);                          // Read Op Name (variable length)
        String opName = new String(opNameBytes, StandardCharsets.UTF_16);  // Convert to UTF-16 String
        // Return the new Request object
        return new Request(TML, opCode, operand1, operand2, requestID, opNameLength, opName);
    }
}
