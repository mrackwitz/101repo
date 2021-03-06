package org.softlang.company.impl.pojo;

import org.softlang.company.*;
import org.softlang.visitor.*;

public class EmployeeImpl extends ComponentImpl implements Employee {

	private String address;
	private double salary;
	private boolean manager = false;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public boolean getManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public void accept(VoidVisitor v) {
		v.visit(this);
	}

	public <R> R accept(ReturningVisitor<R> v) {
		return v.visit(this);
	}
}
