package Frames;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DataBase.*;;


public class RegisterFrame extends JFrame{

    public RegisterFrame(){
        super("注册");
        init();
        setVisible(true);
    }
    void init(){
        setLayout(new FlowLayout());
        setBounds(500, 300, 290, 300);
        setResizable(false);
        setIconImage(new ImageIcon("images/0.png").getImage());
        getContentPane().setBackground(Color.PINK);

        JLabel nameLabel = new JLabel("昵称");
        JLabel passLabel = new JLabel("密码*");
        JLabel headLabel = new JLabel("头像");
        JLabel cPassLabel = new JLabel("确认密码*");

        JTextField name = new JTextField(10);
        JPasswordField pass = new JPasswordField(10);
        JPasswordField cPass = new JPasswordField(10);

        JComboBox <ImageIcon> head = new JComboBox<ImageIcon>();
        for(int i=0;i<11;i++){
            head.addItem(new ImageIcon("images/"+i+".png"));
        }
        
        ButtonGroup group = new ButtonGroup();
        JRadioButton male = new JRadioButton("男");
        JRadioButton female = new JRadioButton("女");
        group.add(male);
        group.add(female);

        JButton ok = new JButton("确认");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (pass.getPassword().length==0 || pass.getPassword().length==0) {
                    //JOptionPane.showMessageDialog 用于显示一个简单的对话框，其中包含一条消息以及一个关闭按钮
                    JOptionPane.showMessageDialog(RegisterFrame.this, "带 “ * ” 为必填内容!");
                    //判断用户名和密码是否为空
                } else if (!new String(pass.getPassword()).equals(new String(cPass.getPassword()))) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "两次输入密码不一致!");
                    pass.setText("");
                    cPass.setText("");
                    pass.requestFocusInWindow();
                    //判断两次密码是否一致
                } else {
                    int ID = RandomEightDigitNumber.RandomGenerate();
                    String info[] = {"","","",""};
                    info[0] = name.getText();
                    if(male.isSelected()){
                        info[1] = "男";
                    }
                    else{
                        info[1] = "女";
                    }
                    info[2] = head.getSelectedItem().toString();
                    info[3] = new String(pass.getPassword());
                    AddUser.add(ID,info[0],info[1] ,info[2], info[3]);
                    RegisterFrame.this.dispose();//关闭当前窗口
                    JOptionPane.showMessageDialog(RegisterFrame.this, "恭喜您注册成功",
                    "您的ID:"+ID,JOptionPane.INFORMATION_MESSAGE);
                    new LoginFrame();
                }
            }
        });

        JButton reset = new JButton("重置");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                name.setText("");
                pass.setText("");
                cPass.setText("");
                name.requestFocusInWindow();//用户名获得焦点
            }
        });//匿名类实现接口

        JButton cancel = new JButton("取消");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent event) {
                RegisterFrame.this.dispose();//关闭当前窗口
            }
        });

        add(nameLabel);  add(name);
        add(male);       add(female);
        add(passLabel);  add(pass);
        add(headLabel);  add(head);
        add(cPassLabel); add(cPass);
        add(ok);         add(reset);    add(cancel);
    }
}
