import java.io.*;

public class ResponseEncoder {
    public byte[] encode(Response response) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        out.writeByte(response.TML);                    // Write TML (1 byte)
        out.writeInt(response.result);                  // Write Result (4 bytes)
        out.writeByte(response.errorCode);              // Write Error Code (4 bytes)
        out.writeShort(response.requestID);             // Write Request ID (2 bytes)

        out.flush();
        return buf.toByteArray();
    }
}
