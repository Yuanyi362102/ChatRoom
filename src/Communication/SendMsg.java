package Communication;

import java.awt.FlowLayout;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.ImageIcon;

import Frames.ChatFrame;

public class SendMsg {
    ChatFrame chatFrame;
    DatagramSocket socket;
    public SendMsg(ChatFrame chatFrame,DatagramSocket socket){
        this.chatFrame = chatFrame;
        this.socket = socket;
    }
    public void sendMsg(){
        String ip = chatFrame.ipField.getText();
        String portStr = chatFrame.portField.getText();
        int port = Integer.parseInt(portStr);
        //获取要发送的信息
        String msg = chatFrame.msg.getText();
        byte[] data = msg.getBytes();
        try {
            //地址
            InetAddress addr = InetAddress.getByName(ip);
            //包裹对象
            DatagramPacket packet = new DatagramPacket(data, data.length,addr,port);
            //发送包裹
            socket.send(packet);
            System.out.println(">>>>>====发出消息到"+ip+"==="+port);
            //将发送的消息添加到面板上
            msgAddToMsgPanel sendMsg = new msgAddToMsgPanel(msg,chatFrame,FlowLayout.RIGHT);
            sendMsg.addMegToPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
