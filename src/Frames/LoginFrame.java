package Frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import DataBase.*;


class Login{
    public static void main(String[] args) {
        new LoginFrame();
    }
}

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = -3426717670093483287L;

    private JTextField account;
    private JPasswordField password;

    public LoginFrame(){
        init();
        setVisible(true);
    }
    void init(){
        setTitle("登录");
        this.setSize(330, 230);

        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);
        this.setResizable(false);
        //logo
        Icon icon = new ImageIcon("images/logo.png");
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(324,47));
        this.add(label, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        //创建一个样式为LOWERED的蚀刻边框,EtchedBorder.LOWERED是这种边框的一种样式，它有一个下凹的3D效果
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        //主面板设置边框
        mainPanel.setBorder(BorderFactory.createTitledBorder(border, "输入登录信息", TitledBorder.CENTER,TitledBorder.TOP));
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(null);

        JLabel nameLbl = new JLabel("账号:");
        nameLbl.setBounds(50, 30, 40, 22);
        mainPanel.add(nameLbl);
        account = new JTextField();
        account.setBounds(95, 30, 150, 22);
        account.requestFocusInWindow();//用户名获得焦点
        mainPanel.add(account);

        JLabel passLbl = new JLabel("密码:");
        passLbl.setBounds(50, 60, 40, 22);
        mainPanel.add(passLbl);
        password = new JPasswordField();
        password.setBounds(95, 60, 150, 22);
        mainPanel.add(password);

        //按钮面板放置在JFrame的南边
        JPanel btnPanel = new JPanel();
        this.add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(new BorderLayout());
        btnPanel.setBorder(new EmptyBorder(2, 8, 4, 8));//空白边框

        JButton registeBtn = new JButton("注册");
        btnPanel.add(registeBtn, BorderLayout.WEST);
        JButton submitBtn = new JButton("登录");
        btnPanel.add(submitBtn, BorderLayout.EAST);

        //注册
        registeBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();  //打开注册窗体
            }
        });
        //登录
        submitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                login();
            }

            private void login() {
                if (account.getText().length() == 0
                || password.getPassword().length == 0) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "账号和密码是必填的",
                        "输入有误",JOptionPane.ERROR_MESSAGE);
                account.requestFocusInWindow();
                return;
            }
                if(!account.getText().matches("^\\d*$")){
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "账号必须是数字",
                            "输入有误",JOptionPane.ERROR_MESSAGE);
                    account.requestFocusInWindow();
                    return;
                }

                String id = account.getText();
                int ID = Integer.parseInt(id);
                String pass = new String(password.getPassword());
                boolean verify = VerifyUser.Verify(ID,pass);
                if(verify == true){
                    LoginFrame.this.dispose();
                    LoadInfo.Load(ID);
                }
                else{
                    JOptionPane.showMessageDialog(LoginFrame.this,"用户不存在",
                    "登录失败",JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        
    }
}
