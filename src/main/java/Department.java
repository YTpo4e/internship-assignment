import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.java.Employee;

public class Department {

    private ArrayList<Employee> staff = new ArrayList<>();
    private int averageSalary = 0;

    public void setAverageSalary() {

        for (int i = 0; i < staff.size(); i++) {
            averageSalary += staff.get(i).getSalary();
        }
    }


    public ArrayList<Employee> getStaff() {
        return staff;
    }

    public int getAverageSalary() {
        return averageSalary;
    }
}
