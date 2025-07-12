package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SellerDao slrDao = DaoFactory.createSellerDao();

        System.out.println("\n________Test 1: seller findById________");
        Seller slr = slrDao.findById(2);
        System.out.println(slr);

        System.out.println("\n________Test 2: seller findByDepartment________");
        Department dpt = new Department(4, null);
        List<Seller> list = slrDao.findByDepartment(dpt);

        List<Seller> finalList = list;
        list.stream()
                .findAny()
                .ifPresentOrElse(
                        any -> finalList.forEach(System.out::println),
                        () -> System.out.println("No sellers found for this department...")
                );

        System.out.println("\n________Test 3: seller findByAll________");
        list = slrDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n________Test 4: seller insert________");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dpt);
        slrDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n________Test 5: seller update________");
        slr = slrDao.findById(1);
        slr.setName("Marie Curie");
        slrDao.update(slr);
        System.out.println("Update completed");
    }
}