import java.sql.*;

public class Job_Listing {
    public static void showApplicantsForJob() {
        String url = "jdbc:sqlserver://LAPTOP-KE2IGD3I;databaseName=application_portal;integratedSecurity=true;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);

            String sql = "SELECT Applicants.Name, Applicants.Surname FROM Applicants " +
                    "JOIN Jobs_Applicants ON Applicants.Applicant_ID = Jobs_Applicants.Applicant_ID " +
                    "JOIN Jobs_Listing ON Jobs_Applicants.Job_ID = Jobs_Listing.Job_ID " +
                    "WHERE Jobs_Listing.Job_Name = 'Business Analyst'";

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

    public static void deleteJobListing() {
        String url = "jdbc:sqlserver://LAPTOP-KE2IGD3I;databaseName=application_portal;integratedSecurity=true;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);

            String sql = "DELETE FROM Jobs_Listing WHERE Job_ID = 1";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Employer.displayJobListings();
            } else {
                System.out.println("No rows deleted; the specified Job_ID may not exist.");
            }

            // Close the prepared statement and connection
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }
}
