package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        SellerDao slrDao = DaoFactory.createSellerDao();

        System.out.println("\n________Test 1: seller findById________");
        Seller slr = slrDao.findById(3);
        System.out.println(slr);
    }
}
