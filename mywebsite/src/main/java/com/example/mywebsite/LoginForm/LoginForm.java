package com.example.mywebsite.LoginForm;

import com.example.mywebsite.User;

import  java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JDialog {


    private JTextField tfemail;
    private JPasswordField pfPassword;
    private JButton OKButton;
    private JButton cancelBtn;
    private JPanel loginPanel;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        OKButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String email = tfemail.getText();
                String password = String.valueOf(pfPassword.getPassword());
                user = getAuthenticated(email, password);


                if (user != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid", "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                dispose();
                }
            });
        setVisible(true);
        }


    public User user;
    private User getAuthenticated(String email, String password) {
        User user = null;
        final String URL = "jdbc:mysql://localhost:3306/user";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

            Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM users WHERE email= ? AND password= ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return user;
    }



    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if (user != null) {
            System.out.println("Successful Authentication of: " + user.name);
            System.out.println("Email: " + user.email);
            System.out.println("Phone: " + user.email);
            System.out.println("Address: " + user.phone);
        } else {
            System.out.println("Authentication cancelled");
        }
    }
}
