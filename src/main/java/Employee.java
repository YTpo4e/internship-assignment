package main.java;



public class Employee {
    private String firstName;
    private String secondName;
    private String department;
    private int salary;
    private String position;
    private String fullName;

    public Employee(String[] employee) {
        firstName = employee[0];
        secondName = employee[1];
        department = employee[2];
        salary = Integer.parseInt(employee[3]);
        position = employee[4];
        fullName = firstName + " " + secondName;
    }

    public String getFullName() {return fullName;};

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }
}
