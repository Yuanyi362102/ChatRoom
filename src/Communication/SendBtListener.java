package Communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;

import javax.swing.ImageIcon;

import Frames.ChatFrame;

public class SendBtListener implements ActionListener{
    ChatFrame chatFrame;
    DatagramSocket socket;

    public SendBtListener(ChatFrame chatFrame,DatagramSocket socket){
        this.chatFrame = chatFrame;
        this.socket = socket;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        SendMsg sendMsg = new SendMsg(chatFrame,socket);
        new Thread(()->{sendMsg.sendMsg();}).start();
    }
    
}
