package Communication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Frames.ChatFrame;

public class msgAddToMsgPanel {
    ChatFrame chatFrame;
    String msg;
    int align;
    public msgAddToMsgPanel(String msg,ChatFrame chatFrame,int align){
        this.chatFrame = chatFrame;
        this.align = align;
        this.msg = msg;
    }
    public void addMegToPanel(){
        System.out.println("========msgAddToMsgPanel========"+msg);
        JLabel msgLabel = new JLabel(msg,SwingConstants.RIGHT);//文本在标签内的水平对齐方式为居中
        msgLabel.setForeground(Color.PINK);
        msgLabel.setBackground(Color.GREEN);
        msgLabel.setSize(10,10);
        msgLabel.setOpaque(true);

        JPanel itermPanel = new JPanel();
        itermPanel.setPreferredSize(new Dimension(chatFrame.msgListArea.getWidth()-30,25));
        itermPanel.add(msgLabel);

        //设置左右对齐
        FlowLayout layout = (FlowLayout) itermPanel.getLayout();
        layout.setAlignment(align);
        chatFrame.msgListArea.setLayout(new GridLayout(100,1));//这一步很关键
        chatFrame.msgListArea.add(itermPanel);
        //更新界面
        chatFrame.msgListArea.revalidate();
    }
}
