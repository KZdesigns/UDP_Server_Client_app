import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Error: Invalid number of arguments");
            return;
        }

        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
        // Create a new datagram socket
        DatagramSocket sock = new DatagramSocket();

        Scanner scanner = new Scanner(System.in);
        // Get the server address
        InetAddress serverAddress = InetAddress.getByName(serverName);

        while (true) {
            // Get the user input
            System.out.println("Enter OpCode (0-5), Operand1, and Operand2 separated by spaces, or 'quit' to exit:");
            String input = scanner.nextLine();
            // Exit if the user enters 'quit'
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
            // Split the input into tokens
            String[] tokens = input.split(" ");
            if (tokens.length != 3) {
                System.err.println("Invalid input. Please enter OpCode, Operand1, and Operand2.");
                continue;
            }
            // Parse the tokens
            int opCode = Integer.parseInt(tokens[0]);
            int operand1 = Integer.parseInt(tokens[1]);
            int operand2 = Integer.parseInt(tokens[2]);
            short requestID = (short) (Math.random() * 65535);

            String opName = getOpName(opCode);
            int TML = 13 + opName.length() * 2; // 13 bytes fixed + opName length in bytes (UTF-16)
            // Create the request
            Request request = new Request(TML, opCode, operand1, operand2, requestID, opName.length(), opName);
            // initialize the encoder
            RequestEncoder encoder = new RequestEncoder();
            // Encode the request
            byte[] requestData = encoder.encode(request);

            // Display the request in hexadecimal
            System.out.println("Sending request (hex):");
            for (byte b : requestData) {
                System.out.printf("%02X ", b);
            }
            System.out.println();
            // Send the request to the server
            DatagramPacket sendPacket = new DatagramPacket(requestData, requestData.length, serverAddress, port);
            long startTime = System.currentTimeMillis();
            sock.send(sendPacket);
            // Receive the response from the server
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            sock.receive(receivePacket);
            long endTime = System.currentTimeMillis();

            // Decode the response
            ResponseDecoder decoder = new ResponseDecoder();
            byte[] responseData = new byte[receivePacket.getLength()];
            System.arraycopy(receivePacket.getData(), 0, responseData, 0, receivePacket.getLength());
            Response response = decoder.decode(responseData);

            // Display the response in hexadecimal
            System.out.println("Received response (hex):");
            for (byte b : responseData) {
                System.out.printf("%02X ", b);
            }
            System.out.println();

            // Display the response details
            System.out.println("Response details: " + response.toString());

            // Display round trip time
            long roundTripTime = endTime - startTime;
            System.out.println("Round trip time: " + roundTripTime + " ms");
        }
    }
    // Get the operation name based on the operation code
    private static String getOpName(int opCode) {
        switch (opCode) {
            case 0: return "addition";
            case 1: return "subtraction";
            case 2: return "or";
            case 3: return "and";
            case 4: return "division";
            case 5: return "multiplication";
            default: throw new IllegalArgumentException("Invalid operation code: " + opCode);
        }
    }
}
