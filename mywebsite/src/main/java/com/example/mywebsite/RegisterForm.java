package com.example.mywebsite;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JDialog {
    User user = new User();

    private JTextField tfname;
    private JTextField tfemail;
    private JTextField tfphone;
    private JTextField tfaddress;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel RegisterPanel;

    public RegisterForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setVisible(true);
    }

    private void registerUser() {
        String name = tfname.getText();
        String email = tfemail.getText();
        String phone = tfphone.getText();
        String address = tfaddress.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter all Fields", "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match", "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            user = addUserToDatabase(name, email, phone, address, password);
            if (user != null) {

                JOptionPane.showMessageDialog(this, "Registration Complete", "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Successful registration of: " + user.getName());
                dispose();


            } else {
                JOptionPane.showMessageDialog(this, "Failed to register new user", "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private User addUserToDatabase(String name, String email, String phone, String address, String password)
            throws SQLException, ClassNotFoundException {
        User user = null;
        final String URL = "jdbc:mysql://localhost:3306/user";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (name, email, phone, address, password) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, password);

                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    user = new User();
                    user.name = name;
                    user.email = email;
                    user.phone = phone;
                    user.address = address;
                    user.password = password;
                }
               conn.close();
               preparedStatement.close();
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

        return user;
    }

public static void main(String[] args) throws Exception {
    try {
        RegisterForm registerForm = new RegisterForm(null);
        User user=registerForm.user;
        if
        (user != null) {

    }
    else
        {
      System.out.println("Registration canceled");}
    }

    catch (Exception e) {
        throw new RuntimeException(e);
    }

  }
}

