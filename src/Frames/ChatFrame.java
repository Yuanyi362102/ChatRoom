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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Communication.ReceiveMsg;
import Communication.SendBtListener;
import DataBase.*;

class Chat{
    public static void main(String[] args) {
        new LoginFrame();
    }
}
   
public class ChatFrame extends JFrame{
    public int uid;
    public String uname;
    public String usex;
    public String uhead;
    public String mString ;
    public String ip ;
    public int port ;
    public JTextField msg;
    public JTextField ipField;
    public JTextField portField;
    public JPanel mainPanel;
    public JPanel displayPanel ;
    public DatagramSocket socket;
    public int hostPort;
    public String hostIp;
    public JScrollPane jScrollPane;
    /**聊天对方的信息Label*/
    private JLabel otherInfoLbl;
    /** 当前用户信息Lbl */
    private JLabel currentUserLbl;
    /**聊天信息列表区域*/
    public JPanel msgListArea;
    /**要发送的信息区域*/
    public static JTextArea sendArea;
    /** 在线用户列表 */
    public static JList onlineList;
    /** 在线用户数统计Lbl */
    public static JLabel onlineCountLbl;
    /** 准备发送的文件 */
    //public static FileInfo sendFile;

    /** 私聊复选框 */
    public JCheckBox rybqBtn;

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
        this.setTitle("聊天室");
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
        //右下连发送消息面板
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());

        // 创建一个分隔窗格
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, sendPanel);//
        splitPane2.setDividerLocation(300);
        splitPane2.setDividerSize(5);
        mainPanel.add(splitPane2, BorderLayout.CENTER);//将左边的主面板再添加一个上下分割的窗格，上面是消息展示面板，下面是发送消息面板

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
        //msgListArea.setLineWrap(true);//自动换行
        infoPanel.add(new JScrollPane(msgListArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        sendPanel.add(tempPanel, BorderLayout.NORTH);

        // 聊天按钮面板
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(btnPanel, BorderLayout.NORTH);

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


        //dispaly面板布局

        displayPanel.setLayout(new GridLayout(10,3));
        JLabel ipJLabel = new JLabel("ip:");
        displayPanel.add(ipJLabel);
        ipField = new JTextField(10);
        ipField.setPreferredSize(new Dimension(10,1));
        displayPanel.add(ipField);

        JLabel portJLabel = new JLabel("port:");
        displayPanel.add(portJLabel);
        portField = new JTextField(9);
        displayPanel.add(portField);

        JLabel idJLabel = new JLabel("id:");
        displayPanel.add(idJLabel);
        JLabel id = new JLabel(String.valueOf(uid));
        displayPanel.add(id);
 
        JLabel namJLabel = new JLabel("name:");
        displayPanel.add(namJLabel);
        JLabel name = new JLabel(uname);
        displayPanel.add(name);

        JLabel sexJLabel = new JLabel("sex:");
        displayPanel.add(sexJLabel);
        JLabel sex = new JLabel(usex);
        displayPanel.add(sex);
    
        JLabel headlabel = new JLabel("headInco:");
        displayPanel.add(headlabel);
        String part[] = uhead.split("\\.");//因为点号在正则表达式中是一个特殊字符，它匹配任何单个字符。所以要加上双反斜线 \\ 来转义点号
        JLabel head = new JLabel(new ImageIcon(part[0]+" copy."+part[1]));
        displayPanel.add(head);
  
        for(int i=0;i<8;i++){
            displayPanel.add(new JLabel(new ImageIcon("images/image.png")));
        }
        
 
        msg = new JTextField(10);
        tempPanel.add(msg,BorderLayout.CENTER);
        JButton SendButton = new JButton("发送");
        tempPanel.add(SendButton,BorderLayout.EAST);
        SendBtListener sendMgsListener = new SendBtListener(this,socket);
        SendButton.addActionListener(sendMgsListener);
        
    }

}
