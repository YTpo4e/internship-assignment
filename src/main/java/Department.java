import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private List<Employee> staff = new ArrayList<>();

    Department() {

    }

    Department(List<Employee> staff) {
        this.staff = new ArrayList<>(staff);
    }



    public Employee getEmployee(int i) {
        return staff.get(i);
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void addEmployee(Employee employee) {
        staff.add(employee);
    }

    public void removeEmployee(Employee employee) {
        staff.remove(employee);
    }

    public int size() {
        return staff.size();
    }

    public double getAverageSalary() {
        double averageSalary = 0;

        for (int i = 0; i < staff.size(); i++) {
            averageSalary += staff.get(i).getSalary().doubleValue();
        }
        averageSalary /= staff.size();
        averageSalary = Math.round(averageSalary * 100.0) / 100.0;
        return averageSalary;
    }

    public void infoSalary() {
        System.out.printf("Average salary per department: %.2f", getAverageSalary());
        System.out.println();
    }

}
