package networking;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class UDPGameWrapper {

    private DatagramSocket socket;

    private final int bufferSize = 1024; // TO DO...

    public UDPGameWrapper(int port) throws SocketException {
        socket = new DatagramSocket(port);
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
    private byte[] receivePacket() throws IOException {

        byte[] buf = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        if (verifyChecksum(packet.getData())) {
            ByteBuffer buffer = ByteBuffer.wrap(packet.getData());
            buffer.getLong(); // Skip checksum bytes
            byte[] actualData = new byte[buffer.remaining()];
            buffer.get(actualData);
            return actualData;
        } else {
            throw new IOException("Checksum mismatch");
        }
    }

    // Compute checksum of data
    private byte[] computeChecksum(byte[] data) {

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + data.length);
        buffer.putLong(calculateChecksumValue(data));
        buffer.put(data);

        return buffer.array();
    }

    // Verify checksum of received data
    private boolean verifyChecksum(byte[] data) {

        ByteBuffer buffer = ByteBuffer.wrap(data);
        long checksumValue = buffer.getLong();

        byte[] actualData = new byte[buffer.remaining()];
        buffer.get(actualData);

        return checksumValue == calculateChecksumValue(actualData);
    }

    // Calculate CRC32 checksum value
    private long calculateChecksumValue(byte[] data) {
        Checksum checksum = new CRC32();
        checksum.update(data, 0, data.length);
        return checksum.getValue();
    }

    // TO DO CLOSE THE SOCKET
}