import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.Locale;

public class Employer extends JFrame {


    private JTextField jobNameField, jobDescriptionField, employerIDField;
    private JLabel jobNameLabel, jobDescriptionLabel, employerIDLabel;

    public void createJobListing() {
        setTitle("Create Job Listing");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 7, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;

        jobNameLabel = new JLabel("Job Name*");
        jobNameField = new JTextField(20);
        add(jobNameLabel, gbc);
        gbc.gridy++;
        add(jobNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        jobDescriptionLabel = new JLabel("Job Description*");
        jobDescriptionField = new JTextField(20);
        add(jobDescriptionLabel, gbc);
        gbc.gridy++;
        add(jobDescriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        employerIDLabel = new JLabel("Employer ID*");
        employerIDField = new JTextField(20);
        add(employerIDLabel, gbc);
        gbc.gridy++;
        add(employerIDField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;


        JButton submitButton = new JButton("Post Job");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    String jobName = jobNameField.getText();
                    String jobDescription = jobDescriptionField.getText();
                    String employerID = employerIDField.getText();

                    String url = "jdbc:sqlserver://LAPTOP-KE2IGD3I;databaseName=application_portal;integratedSecurity=true;trustServerCertificate=true";
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection connection = DriverManager.getConnection(url);

                        // SQL query to retrieve all "Name" values from the "Employers" table
                        String sql = "INSERT INTO Jobs_Listing VALUES (?, ?, ?)";

                        // Create a PreparedStatement to execute the query
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);

                        preparedStatement.setString(1, jobName);
                        preparedStatement.setString(2, jobDescription);
                        preparedStatement.setString(3, employerID);


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

        String jobName = jobNameField.getText();
        String jobDescription = jobDescriptionField.getText();
        String employerID = employerIDField.getText();

        if (jobName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Job Name", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (jobDescription.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Job Description", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (employerID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in the required field: Employer ID", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    public static void displayJobListings() {
        String url = "jdbc:sqlserver://LAPTOP-KE2IGD3I;databaseName=application_portal;integratedSecurity=true;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);

            // SQL query to retrieve job name and job description
            String sql = "SELECT Job_Name, Job_Description FROM Jobs_Listing " +
                    "JOIN Employers ON Jobs_Listing.Employer_ID = Employers.Employer_ID " +
                    "WHERE Employers.Employer_ID = 1";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set, e.g., store it in variables and display
            while (resultSet.next()) {
                String jobName = resultSet.getString("Job_Name");
                String jobDescription = resultSet.getString("Job_Description");

                // Display the values or use them as needed
                System.out.println("Job Name: " + jobName);
                System.out.println("Job Description: " + jobDescription);
            }

            // Close the result set, prepared statement, and connection
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void displayAllApplicants() {
        String url = "jdbc:sqlserver://LAPTOP-KE2IGD3I;databaseName=application_portal;integratedSecurity=true;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);

            // SQL query to retrieve job name and job description
            String sql = "SELECT Applicants.Name, Applicants.Surname FROM Applicants " +
                    "JOIN Jobs_Applicants ON Applicants.Applicant_ID = Jobs_Applicants.Applicant_ID " +
                    "JOIN Jobs_Listing ON Jobs_Applicants.Job_ID = Jobs_Listing.Job_ID" +
                    "JOIN Employers ON Jobs_Listing.Employer_ID = Employers.Employer_ID" +
                    "WHERE Employers.Employer_ID = 1";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set, e.g., store it in variables and display
            while (resultSet.next()) {
                String applicantName = resultSet.getString("Name");
                String applicantSurname = resultSet.getString("Surname");

                // Display the values or use them as needed
                System.out.println("Name: " + applicantName + ", Surname: " + applicantSurname);
            }

            // Close the result set, prepared statement, and connection
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }
}
