import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame implements ActionListener {

    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel usernameLabel, emailLabel, passwordLabel;
    private Connection connection;
    private LoginForm loginForm;

    public RegistrationForm() {
        // Database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_login", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // GUI components
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton, gbc);

        gbc.gridy = 4;
        JLabel loginLink = new JLabel("<html><u>Already have an account? Click here to login.</u></html>");
        loginLink.setForeground(Color.BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (loginForm == null) {
                    loginForm = new LoginForm();
                }
                loginForm.setVisible(true);
            }
        });
        panel.add(loginLink, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
