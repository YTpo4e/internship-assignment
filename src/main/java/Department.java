import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {

    private List<Employee> staff = new ArrayList<>();
    private final List<Employee> transferList = new ArrayList<>();

    Department() {

    }

    Department(List<Employee> staff) {
        this.staff = new ArrayList<>(staff);
    }

    public List<Employee> getTransferList() {
        return transferList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(staff, that.staff) &&
                Objects.equals(transferList, that.transferList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staff, transferList);
    }

    public void fromTransferList() {
        List<Employee> currentStaff = new ArrayList<>(staff);
        for (Employee employee : staff) {
            List<Employee> tempStaff = new ArrayList<>(currentStaff);
            tempStaff.remove(employee);
            double currentAverage = calculateAverage(tempStaff);

            if (currentAverage > getAverageSalary()) {
                transferList.add(employee);
            }
        }
    }

    public  static double calculateAverage(List<Employee> staff) {
        double averageSalary = 0;
        for (Employee employee : staff) {
            averageSalary += employee.getSalary().doubleValue();
        }

        averageSalary /= staff.size();
        return averageSalary;
    }

}
