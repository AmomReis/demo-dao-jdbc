package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao dptDao = DaoFactory.createDepartmentDao();
        Scanner sc = new Scanner(System.in);

        System.out.println("\n________Test 1: department findById________");
        Department dpt = dptDao.findById(2);
        System.out.println(dpt);

        System.out.println("\n________Test 2: department insert________");
        Department newDepartment = new Department(null, "Food");
        dptDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("\n________Test 3: department update________");
        dpt = dptDao.findById(1);
        dpt.setName("Computer");
        dptDao.update(dpt);

        System.out.println("\n________Test 4: department delete________");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        dptDao.deleteById(id);
        System.out.println("Delete completed");

        System.out.println("\n________Test 5: department findByAll________");
        List<Department> list = dptDao.findAll();
        list.forEach(System.out::println);
    }
}
