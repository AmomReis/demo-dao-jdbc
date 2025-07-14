package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {

        // Instantiate the Department DAO using the factory
        DepartmentDao dptDao = DaoFactory.createDepartmentDao();
        Scanner sc = new Scanner(System.in);

        /**
         * Test 1 - Find a department by its ID
         * Demonstrates the use of findById to retrieve a specific department
         */
        System.out.println("\n________Test 1: department findById________");
        Department dpt = dptDao.findById(2);
        System.out.println(dpt);

        /**
         * Test 2 - Insert a new department
         * Creates a new department and inserts it into the database
         */
        System.out.println("\n________Test 2: department insert________");
        Department newDepartment = new Department(null, "Food");
        dptDao.insert(newDepartment); // ID is automatically generated
        System.out.println("Inserted! New id = " + newDepartment.getId());

        /**
         * Test 3 - Update an existing department
         * Changes the name of an existing department and updates it in the database
         */
        System.out.println("\n________Test 3: department update________");
        dpt = dptDao.findById(1);     // Retrieve department with ID 1
        dpt.setName("Computer");      // Update its name
        dptDao.update(dpt);           // Save changes to the database

        /**
         * Test 4 - Delete a department by ID
         * Asks the user to input an ID to delete the corresponding department
         */
        System.out.println("\n________Test 4: department delete________");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        dptDao.deleteById(id);
        System.out.println("Delete completed");

        /**
         * Test 5 - Retrieve all departments
         * Lists and prints all departments from the database
         */
        System.out.println("\n________Test 5: department findAll________");
        List<Department> list = dptDao.findAll();
        list.forEach(System.out::println); // Print each department in the list

        // Close the scanner resource
        sc.close();
    }
}