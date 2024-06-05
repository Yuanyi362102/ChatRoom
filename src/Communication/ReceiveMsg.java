package Communication;

import java.awt.FlowLayout;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.*;
import Frames.ChatFrame;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;


public class ReceiveMsg extends Thread{
    ChatFrame chatFrame;
    DatagramSocket socket;
    
    public ReceiveMsg(ChatFrame chatFrame,DatagramSocket socket){
        this.chatFrame = chatFrame;
        this.socket = socket;
    }
    public void run(){
        System.out.println("======启动后台接收消息线程====");
        byte[] data = new byte[1024];

        while(true){
            //创建包裹对象
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                //等待接受消息，同时阻塞线程
                socket.receive(packet);
                //如果接收到消息后，上面阻塞解除，并将本地空的packet对象填充接收到的消息
                int len = packet.getLength();
                String msg = new String(data,0,len);
                System.out.println("接收到消息:"+msg);
                //接收的消息添加到消息面板
                Color fontColor = new Color(0,0,0);
                Color bgColor = new Color(221,224,232);
                msgAddToMsgPanel recevieMsg = new msgAddToMsgPanel(msg,chatFrame,FlowLayout.LEFT,fontColor,bgColor);
                recevieMsg.addMegToPanel();
                new Thread(() -> {
                    try {
                        RecevieTips recevieTips = new RecevieTips();
                        recevieTips.fun();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
    
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}

