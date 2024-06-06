package Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import Communication.ReceiveMsg;
import Communication.SendBtListener;
   
public class ChatFrame extends JFrame{

    /*用户信息*/
    public int uid;
    public String uname;
    public String usex;
    public String uhead;

    /*当前主机的ip和端口*/
    public int hostPort;
    public String hostIp;

    public JTextField msg;
    public JTextField ipField;
    public JTextField portField;

    public JPanel mainPanel;
    public JPanel displayPanel;
    public JPanel msgListArea;//消息展示面板

    public DatagramSocket socket;
 
    private JLabel otherInfoLbl;//聊天对方的信息Label
    public JLabel chatbgJLabel;//聊天背景标签


    public ChatFrame(int uid,String uname,String usex,String uhead){
        this.uid = uid;
        this.uname = uname;
        this.usex = usex;
        this.uhead = uhead;
        this.init();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void init(){
        this.setTitle("💬"+"聊天室"+"💬");
        this.setSize(550, 500);
        this.setResizable(false);

        //设置默认窗体在屏幕中央
        int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);

         //左边主面板
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
         //右边用户面板
         displayPanel = new JPanel();
         displayPanel.setLayout(new GridLayout());
         // 创建一个分隔窗格
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                mainPanel, displayPanel);//将主面板和展示面板放在分割窗格的左右两边
        splitPane.setDividerLocation(380);//设置分隔条的初始位置
        splitPane.setDividerSize(10);//设置分隔条的大小
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.CENTER);//将分割窗格放在布局的中央

        //左上边信息显示面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        //左下边发送消息面板
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());
        // 创建一个分隔窗格
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, sendPanel);//
        splitPane2.setDividerLocation(300);
        splitPane2.setDividerSize(5);
        mainPanel.add(splitPane2, BorderLayout.CENTER);

        try {
            socket = new DatagramSocket();
            hostPort = socket.getLocalPort();
            hostIp = InetAddress.getLocalHost().getHostAddress();
            otherInfoLbl = new JLabel("好友当前状态:在线..."+"  您的ip:"+hostIp+"  您的port:"+hostPort);
            ReceiveMsg receiveMsg = new ReceiveMsg(this,socket);
            receiveMsg.start();//启动线程，后台等待接受消息
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoPanel.add(otherInfoLbl, BorderLayout.NORTH);

        msgListArea = new JPanel();
        infoPanel.add(new JScrollPane(msgListArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));


        chatbgJLabel = new JLabel(new ImageIcon("images/beach copy.png"));
        chatbgJLabel.setOpaque(false);
        msgListArea.add(chatbgJLabel);

        // 聊天按钮面板
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        JPanel sendmsgJPanel = new JPanel();
        sendmsgJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        sendmsgJPanel.setOpaque(false);
        JLabel sendPanelJLabel = new JLabel(new ImageIcon("images/beach.png"));
        sendPanelJLabel.setLayout(new GridLayout(4,1));
        sendPanelJLabel.setOpaque(false);
        sendPanelJLabel.add(btnPanel);
        sendPanelJLabel.add(sendmsgJPanel);
        sendPanel.add(sendPanelJLabel);

        //字体按钮
        JButton fontBtn = new JButton(new ImageIcon("images/font.png"));
        fontBtn.setMargin(new Insets(0,0,0,0));
        fontBtn.setToolTipText("设置字体和格式");
        btnPanel.add(fontBtn);

         //表情按钮
         JButton faceBtn = new JButton(new ImageIcon("images/sendFace.png"));
         faceBtn.setMargin(new Insets(0,0,0,0));
         faceBtn.setToolTipText("选择表情");
         btnPanel.add(faceBtn);
 
         //发送文件按钮
         JButton shakeBtn = new JButton(new ImageIcon("images/shake.png"));
         shakeBtn.setMargin(new Insets(0,0,0,0));
         shakeBtn.setToolTipText("向对方发送窗口振动");
         btnPanel.add(shakeBtn);

         //退出聊天按钮
        JButton sendFileBtn = new JButton(new ImageIcon("images/sendPic.png"));
        sendFileBtn.setMargin(new Insets(0,0,0,0));
        sendFileBtn.setToolTipText("退出当前聊天");//提示文字
        sendFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatFrame.this.dispose();
            }
            
        });
        btnPanel.add(sendFileBtn);

        msg = new JTextField(20);
        JButton SendButton = new JButton("发送");
        SendBtListener sendMgsListener = new SendBtListener(this,socket);
        SendButton.addActionListener(sendMgsListener);
        sendmsgJPanel.add(msg);
        sendmsgJPanel.add(SendButton);


        //dispaly面板布局
        JLabel disLabel = new JLabel(new ImageIcon("images/beach.png"));
        displayPanel.add(disLabel);
        disLabel.setOpaque(false);

        disLabel.setLayout(new GridLayout(10,3));
        JLabel ipJLabel = new JLabel("ip:");
        disLabel.add(ipJLabel);
        ipField = new JTextField(10);
        ipField.setPreferredSize(new Dimension(10,1));
        disLabel.add(ipField);

        JLabel portJLabel = new JLabel("port:");
        disLabel.add(portJLabel);
        portField = new JTextField(9);
        disLabel.add(portField);

        JLabel idJLabel = new JLabel("id:");
        disLabel.add(idJLabel);
        JLabel id = new JLabel(String.valueOf(uid));
        disLabel.add(id);
 
        JLabel namJLabel = new JLabel("name:");
        disLabel.add(namJLabel);
        JLabel name = new JLabel(uname);
        disLabel.add(name);

        JLabel sexJLabel = new JLabel("sex:");
        disLabel.add(sexJLabel);
        JLabel sex = new JLabel(usex);
        disLabel.add(sex);
    
        JLabel headlabel = new JLabel("headInco:");
        disLabel.add(headlabel);
        String part[] = uhead.split("\\.");//因为点号在正则表达式中是一个特殊字符，它匹配任何单个字符。所以要加上双反斜线 \\ 来转义点号
        JLabel head = new JLabel(new ImageIcon(part[0]+" copy."+part[1]));
        disLabel.add(head);  
    }

}
