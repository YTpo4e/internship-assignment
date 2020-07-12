import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.java.Employee;

public class Department {

    private ArrayList<Employee> staff = new ArrayList<>();
    private double averageSalary = 0;

    public void setAverageSalary() {

        for (int i = 0; i < staff.size(); i++) {
            averageSalary += staff.get(i).getSalary();
        }

        averageSalary /= staff.size();
    }


    public ArrayList<Employee> getStaff() {
        return staff;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void infoSalary() {
        System.out.printf("Average salary per department: %.2f", averageSalary);
        System.out.println();
    }
}
