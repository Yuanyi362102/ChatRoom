package Communication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Frames.ChatFrame;

public class msgAddToMsgPanel {
    ChatFrame chatFrame;
    String msg;
    int align;
    Color fontColor;
    Color bgColor;
    public msgAddToMsgPanel(String msg,ChatFrame chatFrame,int align,Color fontColor,Color bgColor){
        this.chatFrame = chatFrame;
        this.align = align;
        this.msg = msg;
        this.fontColor = fontColor;
        this.bgColor = bgColor;
    }
    public void addMegToPanel(){
        System.out.println("========msgAddToMsgPanel========"+msg);
        JLabel msgLabel = new JLabel(msg,SwingConstants.RIGHT);//文本在标签内的水平对齐方式为居中
        msgLabel.setForeground(fontColor);//new Color(221,224,232));
        msgLabel.setBackground(bgColor);//new Color(58,165,237));RGB配色方案
        msgLabel.setSize(10,25);
        msgLabel.setOpaque(true);//设置为不透明
        
        JPanel itermPanel = new JPanel();
        itermPanel.setOpaque(false);  
        itermPanel.add(msgLabel); 

        //设置左右对齐
        FlowLayout layout = (FlowLayout) itermPanel.getLayout();
        layout.setAlignment(align);
        chatFrame.chatbgJLabel.setLayout(new GridLayout(50,1));//这一步很关键
        chatFrame.chatbgJLabel.add(itermPanel);
        
        //更新界面
        chatFrame.msgListArea.revalidate();
    }
}
