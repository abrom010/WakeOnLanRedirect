package com.example.wakeonlanredirect;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class PacketRedirect implements Runnable{
    private boolean exit = false;
    private int port;

    PacketRedirect(int port){
        this.port=port;
    }

    void stop(){
        exit=true;
    }

    public void run(){
        byte[] receiveData = new byte[108];
        DatagramPacket sendPacket;
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);


        try {
            InetAddress address = InetAddress.getByName(MainActivity.address);
            sendPacket = new DatagramPacket(receiveData, receiveData.length, address, port);
            DatagramSocket socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            InetSocketAddress socketAddress = new InetSocketAddress(port);
            while (!exit) {
                socket.bind(socketAddress);
                socket.receive(receivePacket);
                socket.send(sendPacket);
            }
            socket.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
