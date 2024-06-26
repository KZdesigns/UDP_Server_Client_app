import java.io.*;

public class ResponseDecoder {
    public Response decode(byte[] data) throws IOException {
        ByteArrayInputStream buf = new ByteArrayInputStream(data);
        DataInputStream in = new DataInputStream(buf);
        // Read the fields of the response
        int TML = in.readUnsignedByte();                    // Read TML (1 byte)
        int result = in.readInt();                          // Read Result (4 bytes)
        int errorCode = in.readUnsignedByte();              // Read Error Code (1 byte)
        short requestID = in.readShort();                   // Read Request ID (2 bytes)
        // Return the new Response object
        return new Response(TML, result, errorCode, requestID);
    }
}
