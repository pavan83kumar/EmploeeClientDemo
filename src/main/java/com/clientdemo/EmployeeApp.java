package com.clientdemo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.daoImpl.EmployeeDaoImpl;
import com.entities.Bonus;
import com.entities.Employee;
import com.service.BonusService;
import com.service.EmployeeBonusService;

public class EmployeeApp {

	static List<Employee> empList = new ArrayList<>();
	static List<Bonus> bonusList = new ArrayList<>();
	final static Logger logger = Logger.getLogger(EmployeeApp.class);

	public static void main(String[] args) {

		BonusService bonusImpl = new BonusService();
		EmployeeDaoImpl employeeImpl = new EmployeeDaoImpl();
		EmployeeBonusService service = new EmployeeBonusService();

		// Reading the files through BufferedReader

		try (BufferedReader employeeReader = new BufferedReader(
				new FileReader("..\\DBassignment\\resources\\employee.txt"));
				BufferedReader bonusReader = new BufferedReader(
						new FileReader("..\\DBassignment\\resources\\bonus.txt"))) {

			String str;
			while ((str = employeeReader.readLine()) != null) {
				String[] tokens = str.split(",");

				// Creating an Employee Object and adding the objects to the
				// list.

				Employee emp = new Employee(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
						new SimpleDateFormat("yyyy-MM-dd").parse(tokens[2]),
						new SimpleDateFormat("yyyy-MM-dd").parse(tokens[3]), Integer.parseInt(tokens[4]),
						Integer.parseInt(tokens[5]));
				empList.add(emp);
			}

			// Creating the bonus objects and adding them to the bonus list.

			String bonusString;
			while ((bonusString = bonusReader.readLine()) != null) {
				String[] str2 = bonusString.split(",");
				Bonus bonus = new Bonus(Integer.parseInt(str2[0]), Integer.parseInt(str2[1]),
						Integer.parseInt(str2[2]));
				bonusList.add(bonus);

			}

			// Invoking the methods
			bonusImpl.loadBonus(bonusList);
			employeeImpl.loadEmployee(empList);
			service.allocateBonus();

		} catch (FileNotFoundException e) {

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

	}

}
