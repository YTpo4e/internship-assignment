package study.to_another_department;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return new ArrayList<>(staff);
    }

    public void addEmployee(Employee employee) {
        staff.add(employee);
    }

    public void removeEmployee(Employee employee) {
        staff.remove(employee);
    }


    public double getAverageSalary() {
        BigDecimal averageSalary = new BigDecimal(0);

        for (int i = 0; i < staff.size(); i++) {
            averageSalary = staff.get(i).getSalary().add(averageSalary);
        }
        averageSalary = averageSalary.divide(new BigDecimal(staff.size()), 2);
        return averageSalary.doubleValue();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(staff, that.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staff);
    }

    public List<Employee> fromTransferList() {
        final List<Employee> transferList = new ArrayList<>();
        double averageSalary = getAverageSalary();
        for (Employee employee : staff) {
            if (employee.getSalary().doubleValue() < averageSalary) {
                transferList.add(employee);
            }
        }
        return transferList;
    }



}
