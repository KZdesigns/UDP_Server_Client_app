import java.net.*;
import java.util.Arrays;
import java.io.*;

public class ServerUDP {
    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.err.println("Error: Invalid number of arguments");
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("Server started on port " + port);
        DatagramSocket sock = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

        while (true) {
            sock.receive(packet);

            // initialize the decoder
            RequestDecoder requestDecoder = new RequestDecoder();

            // Decode the request
            byte[] requestData = Arrays.copyOf(packet.getData(), packet.getLength());
            Request request = requestDecoder.decode(requestData);

            // Display the request in hexadecimal
            System.out.println("Received request (hex):");
            for (byte b : requestData) {
                System.out.printf("%02X ", b);
            }
            System.out.println();

            // Display the request in a user-friendly format
            System.out.println("Request details: " + request.toString());

            // Perform the requested operation
            int result;
            byte errorCode = 0;
            try {
                result = performOperation(request.opCode, request.operand1, request.operand2);
            } catch (ArithmeticException e) {
                result = 0;
                errorCode = 127;
            }

            // Create the response
            Response response = new Response(8, result, errorCode, request.requestID);

            // Encode the response
            ResponseEncoder responseEncoder = new ResponseEncoder();
            byte[] responseData = responseEncoder.encode(response);

            // Send the response back to the client
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
            sock.send(sendPacket);

            // Display the response in hexadecimal
            System.out.println("Sent response (hex):");
            for (byte b : responseData) {
                System.out.printf("%02X ", b);
            }
            System.out.println();
        }
    }

    private static int performOperation(int opCode, int operand1, int operand2) {
        switch (opCode) {
            case 0:
                return operand1 + operand2;
            case 1:
                return operand1 - operand2;
            case 2:
                return operand1 | operand2;
            case 3:
                return operand1 & operand2;
            case 4:
                return operand1 / operand2;
            case 5:
                return operand1 * operand2;
            default:
                throw new IllegalArgumentException("Invalid operation code");
        }
    }
}
