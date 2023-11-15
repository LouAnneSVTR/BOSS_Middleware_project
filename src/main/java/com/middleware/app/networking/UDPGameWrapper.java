package com.middleware.app.networking;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class UDPGameWrapper {
    private static final int CHECKSUM_SIZE = Long.BYTES;
    private DatagramSocket socket;

    private final int bufferSize;

    public UDPGameWrapper(int port, int bufferSize) throws SocketException {
        socket = new DatagramSocket(port);
        this.bufferSize = bufferSize;
    }

    // Send a serializable object
    public void sendObject(InetAddress address, int port, Serializable obj) throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(obj);
        objStream.flush();

        byte[] serializedData = byteStream.toByteArray();
        System.out.println("Serialized object data: " + Arrays.toString(serializedData));

        sendPacket(address, port, byteStream.toByteArray());
    }

    // Receive a serializable object
    public Serializable receiveObject() throws IOException, ClassNotFoundException {
        byte[] data = receivePacket();
        
        // Log the data before deserialization
        System.out.println("Data before deserialization (should be just the object data): " + Arrays.toString(data));

        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);

        Serializable receivedObject = (Serializable) objStream.readObject();
        System.out.println("Received Object Type: " + receivedObject.getClass().getName());

        return receivedObject;
    }

    // Send packet with checksum
    private void sendPacket(InetAddress address, int port, byte[] data) throws IOException {
        byte[] sendData = computeChecksum(data);

        ByteBuffer buffer = ByteBuffer.wrap(sendData);
        long checksumValue = buffer.getLong();
        
        System.out.println("Data to send (with checksum): " + Arrays.toString(sendData));
        System.out.println("Calculated Checksum (Client): " + checksumValue);

        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(packet);
    }

    // Receive packet with checksum
    public byte[] receivePacket() throws IOException {
        byte[] buf = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        try {
        
            System.out.println("Raw data (with checksum): " + Arrays.toString(Arrays.copyOf(packet.getData(), packet.getLength())));

            if (verifyChecksum(packet.getData(), packet.getLength())) {
                ByteBuffer buffer = ByteBuffer.wrap(packet.getData());
                buffer.position(CHECKSUM_SIZE); // Skip checksum bytes
                
                 // Log the data after the checksum has been skipped
                System.out.println("Data after checksum removal (should not include checksum): " + Arrays.toString(Arrays.copyOfRange(packet.getData(), CHECKSUM_SIZE, packet.getLength())));
            
                byte[] actualData = new byte[packet.getLength() - CHECKSUM_SIZE];
                buffer.get(actualData);
            
                // Log the actual data
                System.out.println("Actual data (should match the sent message): " + Arrays.toString(actualData));
                return actualData;
            } else {
                throw new IOException("Checksum mismatch");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after printing the stack trace
        }
    }

    // Compute checksum of data
    private byte[] computeChecksum(byte[] data) {

        ByteBuffer buffer = ByteBuffer.allocate(CHECKSUM_SIZE + data.length);
        buffer.putLong(calculateChecksumValue(data));
        buffer.put(data);
        return buffer.array();
    }

    // Verify checksum of received data
    private boolean verifyChecksum(byte[] data, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(data, 0, length);
        buffer.order(ByteOrder.BIG_ENDIAN);

        long checksumValue = buffer.getLong();

        System.out.println("Received Checksum (Server): " + checksumValue);

        byte[] actualData = new byte[length - Long.BYTES];
        buffer.get(actualData);

        long calculatedChecksum = calculateChecksumValue(actualData);
        System.out.println("Calculated Checksum (Server): " + calculatedChecksum);

        return checksumValue == calculatedChecksum;
    }

    // Calculate CRC32 checksum value
    public long calculateChecksumValue(byte[] data) {
        Checksum checksum = new CRC32();
        checksum.update(data, 0, data.length);
        long calculatedChecksum = checksum.getValue();
        System.out.println("Calculated Checksum (Client): " + calculatedChecksum);
        return calculatedChecksum;
    }

    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
