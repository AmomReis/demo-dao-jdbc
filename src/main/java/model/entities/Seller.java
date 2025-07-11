package model.entities;

import java.util.Date;

public record Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, Department department) {
}
