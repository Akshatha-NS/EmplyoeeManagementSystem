package employeemenu;

import CustomException.AgeNotWithInRangeException;
import CustomException.NameNotValidException;
import Database.EmployeeDatabase;
import model.Employee;
import service.EmployeeManagement;
import serviceimpl.EmployeeFileManager;
import serviceimpl.EmployeeSerializationManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class EmployeeManagementMenu {

	private static final Logger logger=LogManager.getLogger(EmployeeManagementMenu.class);
    public static void main(String[] args) throws SQLException {


        Scanner sc = new Scanner(System.in);
        int fileManagerType;
        System.out.println("\n\n Choose the File Implementation Type:");
        System.out.println("\n\t1. Normal File Operation");
        System.out.println("\n\t2. Serialization");
        System.out.println("\n\t3. Database");
        System.out.println("\n\t4. Exit");
        fileManagerType = sc.nextInt();
        EmployeeManagement employeeFileManager = null;
        String fileName = null;
        if(fileManagerType == 1) {
            employeeFileManager = new EmployeeFileManager();
            fileName = "employee_records.txt";
        }
        else if(fileManagerType==2) {
            employeeFileManager = new EmployeeSerializationManager();
            fileName = "employee_list.ser";
        }
        else if(fileManagerType==3)
        {
        	employeeFileManager = new EmployeeDatabase();
        }
        

        if (fileManagerType == 1 || fileManagerType == 2) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nEmployee Record Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Find Employee");
                System.out.println("5. Display All Employees");
                System.out.println("6. Save to File");
                System.out.println("7. Load from File");
                System.out.println("8. exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Employee ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Employee name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Employee age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        Employee newEmployee = new Employee(id, name, age);
                        try {
                            employeeFileManager.addEmployee(newEmployee);
                            logger.info("This is an info message for adding employee");
                        } catch (AgeNotWithInRangeException e) {
                            System.out.println(e);
                            logger.error("This is an error message caused by AgeNotWithInRangeException.");
                        } catch (NameNotValidException e1) {
                            System.out.println(e1);
                            logger.error("This is an error message cased by NameNotValidException .");
                        }
                        break;
                    case 2:
                        System.out.print("Enter employee ID to update: ");
                        String updateId = scanner.nextLine();
                        Employee empToUpdate = employeeFileManager.findEmployee(updateId);
                        if (empToUpdate != null) {
                            System.out.print("Enter new employee name: ");
                            String updatedName = scanner.nextLine();
                            System.out.print("Enter new employee age: ");
                            int updatedAge = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                employeeFileManager.updateEmployee(updateId, new Employee(updateId, updatedName, updatedAge));
                            } catch (AgeNotWithInRangeException e) {
                                System.out.println(e);
                                logger.error("This is an error message caused by AgeNotWithInRangeException.");
                                
                            } catch (NameNotValidException e) {
                                System.out.println(e);
                                logger.error("This is an error message cased by NameNotValidException .");
                            }
                            System.out.println("Employee updated successfully!");
                            logger.info("This is an info message for updating employee");
                        } else {
                            System.out.println("Employee not found!");
                        }
                        break;
                    case 3:
                        System.out.print("Enter employee ID to delete: ");
                        String deleteId = scanner.nextLine();
                        employeeFileManager.deleteEmployee(deleteId);
                        System.out.println("Employee deleted successfully!");
                        logger.info("This is an info message for deleting employee");
                        break;
                    case 4:
                        System.out.print("Enter Employee ID to find: ");
                        String findId = scanner.nextLine();
                        Employee foundemp = employeeFileManager.findEmployee(findId);
                        if (foundemp != null) {
                            System.out.println("Found Employee: " + foundemp);
                        } else {
                            System.out.println("Employee not found!");
                        }
                        logger.info("This is an info message for finding employee");
                        break;
                    case 5:
                        System.out.println("All Employees:");
                        for (Employee e : employeeFileManager.getAllEmployee()) {
                            System.out.println(e);
                        }
                        logger.info("This is an info message for displaying all employee");
                        break;
                    case 6:
                        employeeFileManager.saveToFile(fileName);
                        System.out.println("Data saved to file successfully!");
                        logger.info("This is an info message for saving employees to file");
                        break;
                    case 7:
                        employeeFileManager.listAllEmployeesFromFile(fileName);
                        System.out.println("Data loaded from file successfully!");
                        logger.info("This is an info message for loading employees to file");
                        break;
                    case 8:
                        System.out.println("Exiting program. Goodbye!");
                        logger.info("This is an info message for exiting program ");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        logger.info("This is an info message for invalid choice  ");
                }
            }
        }
        else if(fileManagerType == 3)
        {
        	 Scanner scanner = new Scanner(System.in);
            
   		 
   		 Properties properties = new Properties();
   		
		Connection con=null;
		PreparedStatement ps1 = null;
		ResultSet result = null;
   		 try {
   		 FileInputStream input = new
   		 FileInputStream("C:\\Users\\MSIS\\Downloads\\EmployeeFileManagementSystem\\EmployeeFileManagementSystem\\src\\database.properties");
   		 properties.load(input);
   		 String url = properties.getProperty("url");
   		 String username = properties.getProperty("user");
   		 String password = properties.getProperty("password");
	    con=DriverManager.getConnection(url,username,password);
	    if(con!=null)
		{
			System.out.println("Connection created successfully using the properties file");
		}
		else
		{
			System.out.println("unable to create a connection");
		}
		 
		 
   		 } catch (IOException e) {
   		 e.printStackTrace();
   		 return;
   		 } finally {
   		 System.out.println("Database connection successfull");
   		 }
  
   		while(true) {
             System.out.println("\nEmployee Record Management System");
             System.out.println("1. Add Employee");
             System.out.println("2. Update Employee");
             System.out.println("3. Delete Employee");
             System.out.println("4. Find Employee");
             System.out.println("5. Display All Employees");
             System.out.println("6. exit");
             System.out.print("Enter your choice: ");

             int choice = scanner.nextInt();
             scanner.nextLine(); // Consume the newline

             switch (choice) {
                 case 1:
                     System.out.print("Enter Employee ID: ");
                     String id = scanner.nextLine();
                     System.out.print("Enter Employee name: ");
                     String name = scanner.nextLine();
                     System.out.print("Enter Employee age: ");
                     int age = scanner.nextInt();
                     scanner.nextLine(); // Consume the newline
                     Employee newEmployee = new Employee(id, name, age);
                     try {
                         employeeFileManager.createEmployee(con, newEmployee);
                         logger.info("This is an info message for adding employee");
                     } catch (AgeNotWithInRangeException e) {
                         System.out.println(e);
                         logger.error("This is an error message caused by AgeNotWithInRangeException.");
                     } catch (NameNotValidException e1) {
                         System.out.println(e1);
                         logger.error("This is an error message cased by NameNotValidException .");
                     }
                     break;
                 case 2:
                     System.out.print("Enter employee ID to update: ");
                     String updateId = scanner.nextLine();
                   
                         System.out.print("Enter new employee name: ");
                         String updatedName = scanner.nextLine();
                         System.out.print("Enter new employee age: ");
                         int updatedAge = scanner.nextInt();
                         scanner.nextLine();
                         try {
                             employeeFileManager.updateEmployee(con,updateId,updatedName,updatedAge);
                         } catch (AgeNotWithInRangeException e) {
                             System.out.println(e);
                             logger.error("This is an error message caused by AgeNotWithInRangeException.");
                             
                         } catch (NameNotValidException e) {
                             System.out.println(e);
                             logger.error("This is an error message cased by NameNotValidException .");
                         }
                         System.out.println("Employee updated successfully!");
                         logger.info("This is an info message for updating employee");
                  
                     break;
                 case 3:
                     System.out.print("Enter employee ID to delete: ");
                     String deleteId = scanner.nextLine();
                     employeeFileManager.deleteEmployee(con,deleteId);
                     System.out.println("Employee deleted successfully!");
                     logger.info("This is an info message for deleting employee");
                     break;
                 case 4:
                     System.out.print("Enter Employee ID to find: ");
                    String findId = scanner.nextLine();
                     Employee foundemp = employeeFileManager.readEmployee(con,findId);
                     if (foundemp != null) {
                         System.out.println("Found Employee: " + foundemp);
                     } else {
                         System.out.println("Employee not found!");
                     }
                     logger.info("This is an info message for finding employee");
                     break;
                 case 5:
                     System.out.println("The employees details are :");
                    // System.out.println(" Employee: ");
					employeeFileManager.displayEmployee(con);
					
   		           break;
   		
        }
        if (fileManagerType == 4) {
        	System.out.println("Exiting program. Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Please enter the right option!!");
        }

    }
   
        }
    }
}
   
    




