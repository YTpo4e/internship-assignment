package study.to_another_department;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Department {

    private List<Employee> staff = new ArrayList<>();
    private String departmentName;

    Department() {

    }

    Department(List<Employee> staff) {
        this.staff = new ArrayList<>(staff);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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


    public BigDecimal getAverageSalary() {
        return calculateAverageSalary(staff);
    }

    public static BigDecimal calculateAverageSalary(List<Employee> list) {
        BigDecimal averageSalary = new BigDecimal(0);

        for (int i = 0; i < list.size(); i++) {
            averageSalary = list.get(i).getSalary().add(averageSalary);
        }
        averageSalary = averageSalary.divide(new BigDecimal(list.size()), RoundingMode.CEILING);
        return averageSalary;
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


    public List<List<Employee>> fromTransferList(LinkedList<Employee> temp, int i) {

        List<List<Employee>> lists = new LinkedList<>();

        BigDecimal averageSalary = getAverageSalary();
        Employee employee = staff.get(i);

        if (temp.isEmpty()) {
            if (employee.getSalary().compareTo(averageSalary) < 0) {
                temp.add(employee);
                lists.add(new ArrayList<>(temp));
                if (i + 1 < staff.size()) {
                    lists.addAll(fromTransferList(temp, i + 1));
                }
                temp.remove(employee);
            }
        } else {
            BigDecimal salary = calculateAverageSalary(temp).multiply(new BigDecimal(temp.size()));
            salary = salary.add(employee.getSalary());
            salary = salary.divide(new BigDecimal(temp.size() + 1), RoundingMode.CEILING);

            if (salary.compareTo(averageSalary) < 0) {
                temp.add(employee);
                lists.add(new ArrayList<>(temp));
                if (i + 1 < staff.size()) {
                    lists.addAll(fromTransferList(temp, i + 1));
                }
                temp.remove(employee);
            }
        }

        if (i + 1 < staff.size()) {
            lists.addAll(fromTransferList(temp, i + 1));
        }

        return lists;
    }

}

