package ui;

import skype.SkypeController;

import javax.swing.*;

/**
 * Created by Serhii on 10/27/2015.
 */
public class LoginForm extends JFrame {
    private String login;
    private String password;
    private static JTextField userText;
    private static JPasswordField passwordText;

    private void makeUI() {
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        // panel.addAc
        setVisible(true);
    }

    private  void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.addActionListener(e -> doLogin());
        loginButton.setBounds(10, 80, 80, 25);

        panel.add(loginButton);

    }

    private  void doLogin() {
        try {
            SkypeController skypeController = SkypeController.getInstance();
            skypeController.signIn(userText.getText(), passwordText.getText());
            skypeController.addRedirectEventListener();
            this.setVisible(false);
            ComboCellInsetsDemo.getInstance().createAndShowGUI();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginForm().makeUI();
    }

}
