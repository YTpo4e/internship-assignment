package study.to_another_department;


import java.math.BigDecimal;
import java.util.*;

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


    public BigDecimal getAverageSalary() {
        BigDecimal averageSalary = new BigDecimal(0);

        for (int i = 0; i < staff.size(); i++) {
            averageSalary = staff.get(i).getSalary().add(averageSalary);
        }
        averageSalary = averageSalary.divide(new BigDecimal(staff.size()), 2);
        return averageSalary;
    }

    public static BigDecimal calculateAverageSalary(List<Employee> list) {
        BigDecimal averageSalary = new BigDecimal(0);

        for (int i = 0; i < list.size(); i++) {
            averageSalary = list.get(i).getSalary().add(averageSalary);
        }
        averageSalary = averageSalary.divide(new BigDecimal(list.size()), 2);
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

    public final List<List<Employee>> transferList = new ArrayList<>();
    List<Employee> couple = new ArrayList<>();

    public void fromTransferList(int i) {

        BigDecimal averageSalary = getAverageSalary();
        Employee employee = getEmployee(i);

        if(couple.isEmpty()) {
            if (employee.getSalary().compareTo(averageSalary) == -1) {
                couple.add(employee);
                if (!transferList.contains(couple)) {
                transferList.add(new ArrayList<>(couple));
                fromTransferList(0);
                }
                couple.remove(employee);
            }
        } else  {
            BigDecimal salary = calculateAverageSalary(couple).multiply(new BigDecimal(couple.size()));
            salary = salary.add(employee.getSalary());
            salary = salary.divide(new BigDecimal(couple.size() + 1), 2);

            if (salary.compareTo(averageSalary) == -1 && !couple.contains(employee)) {
                couple.add(employee);
                Collections.sort(couple);
                if (!transferList.contains(couple)) {
                transferList.add(new ArrayList<>(couple));

                fromTransferList(0);
                }
                couple.remove(employee);
            }
        }

        if (i + 1 < staff.size()){
            fromTransferList(i + 1);
        }

    }




}

