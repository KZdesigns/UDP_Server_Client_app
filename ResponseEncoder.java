import java.io.*;

public class ResponseEncoder {
    public byte[] encode(Response response) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        // Write the fields of the response
        out.writeByte(response.TML);                    // Write TML (1 byte)
        out.writeInt(response.result);                  // Write Result (4 bytes)
        out.writeByte(response.errorCode);              // Write Error Code (4 bytes)
        out.writeShort(response.requestID);             // Write Request ID (2 bytes)
        // Flush the output stream
        out.flush();
        // Return the byte array
        return buf.toByteArray();
    }
}
