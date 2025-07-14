package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of the DepartmentDao interface.
 * Provides CRUD operations for the Department entity using raw SQL and JDBC.
 */
public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    /**
     * Constructs the DAO with a database connection.
     * @param conn JDBC connection to be used for database operations.
     */
    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new department into the database.
     * The department's ID is automatically generated and set after insertion.
     * @param obj Department object to be inserted.
     */
    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                    INSERT INTO department
                        (Name)
                    VALUES
                        (?)
                    """,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id); // Set the generated ID back to the object
                } else {
                    throw new DbException("Unexpected error! No rows affected.");
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /**
     * Updates an existing department in the database.
     * @param obj Department object with updated data.
     */
    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    """
                    UPDATE department
                    SET Name = ?
                    WHERE Id = ?
                    """
            );

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update completed. Number of rows affected: " + rowsAffected);
            } else {
                throw new DbException("No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Deletes a department by its ID.
     * @param id The ID of the department to delete.
     */
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ?"
            );

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("No department found with the given ID: " + id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Finds a department by its ID.
     * @param id The ID to search for.
     * @return Department object if found, or null if not found.
     */
    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                    SELECT department.*
                    FROM department
                    WHERE Id = ?
                    """
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateDepartment(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /**
     * Helper method to instantiate a Department object from the ResultSet.
     * @param rs ResultSet from a query.
     * @return Department object.
     * @throws SQLException if an SQL error occurs.
     */
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

    /**
     * Finds all departments ordered by ID.
     * @return List of all departments.
     */
    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                    SELECT department.*
                    FROM department
                    ORDER BY Id
                    """
            );

            rs = st.executeQuery();
            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department obj = instantiateDepartment(rs);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
