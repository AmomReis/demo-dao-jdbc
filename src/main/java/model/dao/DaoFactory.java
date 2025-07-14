package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

/**
 * Factory class responsible for instantiating DAO implementations.
 * This promotes loose coupling by abstracting the concrete DAO classes from the application code.
 */
public class DaoFactory {

    /**
     * Creates an instance of SellerDao using a JDBC implementation.
     *
     * @return a new instance of SellerDaoJDBC with an active database connection
     */
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    /**
     * Creates an instance of DepartmentDao using a JDBC implementation.
     *
     * @return a new instance of DepartmentDaoJDBC with an active database connection
     */
    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
