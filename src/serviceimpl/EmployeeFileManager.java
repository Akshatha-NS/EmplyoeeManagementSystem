package serviceimpl;


import CustomException.AgeNotWithInRangeException;
import model.Employee;
import CustomException.NameNotValidException;
import employeemenu.EmployeeManagementMenu;
import service.EmployeeManagement;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeFileManager implements EmployeeManagement {

	private static final Logger logger=LogManager.getLogger(EmployeeManagementMenu.class);
    private final ArrayList<Employee> empList = new ArrayList<>();
  //Adding the employee to an arraylist
    public void addEmployee(Employee emp) throws AgeNotWithInRangeException, NameNotValidException {
        //validate if age is less than 18
        if (emp.getAge() < 18) {
            throw new AgeNotWithInRangeException();
        }
        if (!emp.getName().matches("^[a-zA-Z]*$")) {
            throw new NameNotValidException();
        }

        empList.add(emp);
        System.out.println("EMployee added successfully!");
        logger.info("This is an info message for adding employee");
    }
  //Finding the employee from an arraylist and updating it with new Employee
    public void updateEmployee(String employeeId, Employee updatedEmployee)throws AgeNotWithInRangeException,NameNotValidException {
        for (int i = 0; i < empList.size(); i++) {
            Employee emp1 = empList.get(i);
            if (emp1.getEmployeeid().equals(employeeId)) {
                    //validate if age is less than 18
                if (updatedEmployee.getAge() < 18) {
                    throw new AgeNotWithInRangeException();
                }
                // Validate name (no special characters or numbers)
                if (!updatedEmployee.getName().matches("^[a-zA-Z]*$")) {
                    throw new NameNotValidException();
                }
                empList.set(i, updatedEmployee);
                logger.info("This is an info message for updating employee");
                break;
            }
        }
    }

    //to delete the employee
    public void deleteEmployee(String employeeId) {
        empList.removeIf(employee -> employee.getEmployeeid().equals(employeeId));
    }

    //to find a particular employee with his id
    public Employee findEmployee(String employeeid) {
        for (Employee obj : empList) {
            if (obj.getEmployeeid().equals(employeeid)) {
                return obj;
            }
        }
        return null;
    }
//to list all the employees in the arraylist 
    public ArrayList<Employee> getAllEmployee() {
        return empList;
    }
  //to save all the values in arraylist to a file 
    public void saveToFile(String fileName) {
        try {
            FileWriter Writer = new FileWriter(fileName);
            Writer.write(String.valueOf(empList));
            Writer.close();
            System.out.println("Successfully saved the employees to the file.");
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }


    // to read it from a file
    public void listAllEmployeesFromFile(String fileName) {
        try {
            System.out.println("Listing Employees from the file");
            File Obj = new File(fileName);
            Scanner Reader = new Scanner(Obj);
            while (Reader.hasNextLine()) {
                String data = Reader.nextLine();
                System.out.println(data);
            }
            Reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
//dummy method for testing FileOperations
    public static void main (String args[])
    {
    	EmployeeManagement fileManager = new EmployeeFileManager();
    	String filemanager = "Testfile.txt";
        //Employee emp1=new Employee("2","%&^*^*",22);
       // Employee emp2=new Employee("3","kiara",16);
        Scanner scan =new Scanner(System.in);
        EmployeeFileManager test = new EmployeeFileManager();
        System.out.println("Enter the employee Id ");
        String employeeID = scan.nextLine();
        System.out.println("Enter the employee name ");
        String name =scan.nextLine();
        System.out.println("Enter the employee age ");
        int age= scan.nextInt();
        Employee emp =new Employee(employeeID,name,age);
       
        try {
            test.addEmployee(emp);
        } catch (AgeNotWithInRangeException e) {
        	System.out.println("you have entered the age below agelimit!!!! Thus not added to the file");
        	logger.error("This is an error message caused by AgeNotWithInRangeException.");
        } catch (NameNotValidException e) {
        	System.out.println("you have entered an invalid name!!! Thus not added to the file");
        	logger.error("This is an error message cased by NameNotValidException .");
        }
        System.out.println("Enter the the id to find an employee");
        scan.nextLine();
        String FindID=scan.nextLine();
        Employee foundemp = test.findEmployee(FindID);
        
        if (foundemp!= null) {
            System.out.println("Found Employee: " + foundemp);
        } else {
            System.out.println("Employee not found!");
        }
        System.out.println("get all the employees");
        for (Employee e : test.getAllEmployee()) {
            System.out.println(e);
        }
        System.out.println("Enter the the id to delete an employee");
        String deleteId = scan.nextLine();
       test.deleteEmployee(deleteId);
       System.out.println("Employee deleted successfully!");
        test.getAllEmployee();
        
       test.saveToFile(filemanager);
       test.listAllEmployeesFromFile(filemanager);
       
       /* try {
            test.addEmployee(emp1);
        } catch (AgeNotWithInRangeException e) {
           System.out.println("you have entered the age below agelimit!!!! Thus not added to the file");
        } catch (NameNotValidException e) {
        	System.out.println("you have entered an invalid name!!! Thus not added to the file");
        }
        try {
            test.addEmployee(emp2);
        } catch (AgeNotWithInRangeException e) {
           System.out.println("you have entered the age below agelimit!!!! Thus not added to the file");
        } catch (NameNotValidException e) {
        	System.out.println("you have entered an invalid name!!! Thus not added to the file");
        }
    }*/
    
}
	@Override
	public void deleteEmployee(Connection connection, String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Employee readEmployee(Connection connection, String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void createEmployee(Connection con, Employee newEmployee)
			throws AgeNotWithInRangeException, NameNotValidException, SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateEmployee(Connection connection, String id, String newName,int age)
			throws SQLException, AgeNotWithInRangeException, NameNotValidException {
		// TODO Auto-generated method stub
		
	}
	 public void displayEmployee(Connection connection)throws SQLException{
	 }

}

