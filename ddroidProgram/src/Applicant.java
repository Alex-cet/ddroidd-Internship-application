import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;

public class Applicant extends JFrame {
    private JTextField firstNameField, lastNameField, phoneNumberField, emailField, addressLine1Field, addressLine2Field;
    private JLabel firstNameLabel, lastNameLabel, phoneNumberLabel, emailLabel, addressLine1Label, addressLine2Label, countryLabel, stateLabel, cityLabel;
    private JComboBox<String> countryComboBox, stateComboBox, cityComboBox;

    public void apply() {
        setTitle("Application Form");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 7, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;

        firstNameLabel = new JLabel("First Name*");
        firstNameField = new JTextField(20);
        add(firstNameLabel, gbc);
        gbc.gridy++;
        add(firstNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        lastNameLabel = new JLabel("Last Name*");
        lastNameField = new JTextField(20);
        add(lastNameLabel, gbc);
        gbc.gridy++;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        phoneNumberLabel = new JLabel("Phone Number*");
        phoneNumberField = new JTextField(20);
        add(phoneNumberLabel, gbc);
        gbc.gridy++;
        add(phoneNumberField, gbc);

        gbc.gridx = 1;
        gbc.gridy--;

        emailLabel = new JLabel("Email Address*");
        emailField = new JTextField(20);
        add(emailLabel, gbc);
        gbc.gridy++;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        add(new JLabel("Address"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        addressLine1Label = new JLabel("Address Line 1*");
        addressLine1Field = new JTextField(20);
        add(addressLine1Label, gbc);
        gbc.gridy++;
        add(addressLine1Field, gbc);


        gbc.gridx = 0;
        gbc.gridy++;

        addressLine2Label = new JLabel("Address Line 2");
        addressLine2Field = new JTextField(20);
        add(addressLine2Label, gbc);
        gbc.gridy++;
        add(addressLine2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        String[] countries = Locale.getISOCountries();
        Arrays.sort(countries);
        String[] countriesWithDefault = new String[countries.length + 1];
        countriesWithDefault[0] = "Select Country";
        System.arraycopy(countries, 0, countriesWithDefault, 1, countries.length);
        countryComboBox = new JComboBox<>(countriesWithDefault);
        countryLabel = new JLabel("Country*");
        add(countryLabel, gbc);
        gbc.gridy++;
        add(countryComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy--;

        String[] states = {"Select State", "State 1", "State 2", "State 3"};
        stateComboBox = new JComboBox<>(states);
        stateLabel = new JLabel("State");
        add(stateLabel, gbc);
        gbc.gridy++;
        add(stateComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy--;

        String[] cities = {"Select City", "City 1", "City 2", "City 3"};
        cityComboBox = new JComboBox<>(cities);
        cityLabel = new JLabel("City*");
        add(cityLabel, gbc);
        gbc.gridy++;
        add(cityComboBox, gbc);

        JButton submitButton = new JButton("Join Us");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Process the data here
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String phoneNumber = phoneNumberField.getText();
                    String emailAddress = emailField.getText();
                    String addressLine1 = addressLine1Field.getText();
                    String addressLine2 = addressLine2Field.getText();
                    String selectedCountry = countryComboBox.getSelectedItem().toString();
                    String selectedState = stateComboBox.getSelectedItem().toString();
                    String selectedCity = cityComboBox.getSelectedItem().toString();

                    String url = "jdbc:sqlserver://LAPTOP-KE2IGD3I;databaseName=application_portal;integratedSecurity=true;trustServerCertificate=true";
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection connection = DriverManager.getConnection(url);

                        String sql = "INSERT INTO Applicants VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        // Create a PreparedStatement to execute the query
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);

                        preparedStatement.setString(1, firstName);
                        preparedStatement.setString(2, lastName);
                        preparedStatement.setString(3, phoneNumber);
                        preparedStatement.setString(4, emailAddress);
                        preparedStatement.setString(5, addressLine1);
                        preparedStatement.setString(6, addressLine2);
                        preparedStatement.setString(7, selectedCountry);
                        preparedStatement.setString(8, selectedState);
                        preparedStatement.setString(9, selectedCity);


                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Insert successful!");
                        } else {
                            System.out.println("Insert failed.");
                        }

                        // Close the prepared statement and connection
                        preparedStatement.close();
                        connection.close();
                    } catch (ClassNotFoundException | SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        setVisible(true);
    }

    private boolean validateInput() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String emailAddress = emailField.getText();
        String addressLine1 = addressLine1Field.getText();
        String country = countryComboBox.getSelectedItem().toString();
        String city = cityComboBox.getSelectedItem().toString();

        if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: First Name", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Last Name", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Phone Number", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (emailAddress.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Email Address", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (addressLine1.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Address Line 1", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (country.equals("Select Country")) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Country", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (city.equals("Select City")) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: City", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
