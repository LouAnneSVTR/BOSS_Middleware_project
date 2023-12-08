package com.middleware.app.network.udp;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ServerUDP {
    private static final int CHECKSUM_SIZE = Long.BYTES;
    private final DatagramSocket socket;

    private final int bufferSize;

    public ServerUDP(int port, int bufferSize) throws SocketException {
        socket = new DatagramSocket(port);
        this.bufferSize = bufferSize;
    }

    // Send a serializable object
    public void sendObject(InetAddress address, int port, Serializable obj) throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(obj);
        objStream.flush();

        sendPacket(address, port, byteStream.toByteArray());
    }

    // Receive a serializable object
    public Serializable receiveObject() throws IOException, ClassNotFoundException {
        byte[] data = receivePacket();

        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);

        return (Serializable) objStream.readObject();
    }

    // Send packet with checksum
    private void sendPacket(InetAddress address, int port, byte[] data) throws IOException {
        byte[] sendData = computeChecksum(data);
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(packet);
    }

    // Receive packet with checksum
    public byte[] receivePacket() throws IOException {
        byte[] buf = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        try {

            if (verifyChecksum(packet.getData(), packet.getLength())) {
                ByteBuffer buffer = ByteBuffer.wrap(packet.getData());
                buffer.position(CHECKSUM_SIZE); // Skip checksum bytes

                byte[] actualData = new byte[packet.getLength() - CHECKSUM_SIZE];
                buffer.get(actualData);

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

        byte[] actualData = new byte[length - Long.BYTES];
        buffer.get(actualData);

        long calculatedChecksum = calculateChecksumValue(actualData);
        return checksumValue == calculatedChecksum;
    }

    // Calculate CRC32 checksum value
    public long calculateChecksumValue(byte[] data) {
        Checksum checksum = new CRC32();
        checksum.update(data, 0, data.length);

        return checksum.getValue();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            close(); // Ensure the socket is closed
        } finally {
            super.finalize(); // Call the superclass finalize method
        }
    }

    public void close() {
        if (!socket.isClosed()) {
            socket.close();
        }
    }
}
