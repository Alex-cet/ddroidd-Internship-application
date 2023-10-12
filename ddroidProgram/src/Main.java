import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

         Employer employer = new Employer();  // Task 1
         employer.createJobListing();

        // Applicant applicant = new Applicant();  // Task 2
        // applicant.apply();

        // Employer.displayJobListings(); // Task 3

        // Employer.displayAllApplicants(); // Task 4

        // Job_Listing.showApplicantsForJob(); // Task 5

        // Job_Listing.deleteJobListing(); // Task 6

    }
}