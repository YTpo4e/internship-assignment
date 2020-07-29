package study.to_another_department;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import static study.to_another_department.Department.calculateAverageSalary;

public class RelocationOfEmployees {
    public static void main(String[] args) {
        if (args.length == 2) {
            Map<String, Department> departmentMap = WorkWithFile.downloadFromFile(args[0]);
            showTable(departmentMap);
            try (FileWriter fileWriter = new FileWriter(args[1])) {
                transferAlternate(departmentMap, fileWriter);
                showTable(departmentMap);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Неверное количество аргументов \n" + " Нужное количество: 2!!!");
        }

    }

    static void showTable(Map<String, Department> map) {
        for (Department department : map.values()) {
            System.out.println("----Department " + department.getDepartmentName() + "----   " + department.getAverageSalary());
            for (int i = 0; i < department.getStaff().size(); i++) {
                System.out.print(department.getEmployee(i).getSecondName() + " ");
                System.out.print(department.getEmployee(i).getFirstName() + " ");
                System.out.print(department.getEmployee(i).getPosition() + " ");
                System.out.print(department.getEmployee(i).getSalary() + " ");
                System.out.println();
            }
            System.out.println();
        }
    }

    static void transferAlternate(Map<String, Department> departmentMap, FileWriter fileWriter) {
        for (Department fromDepartment : departmentMap.values()) {
            performTransfer(departmentMap, fromDepartment, fileWriter);
        }
    }

    static void performTransfer(Map<String, Department> departmentMap, Department fromDepartment, FileWriter fileWriter) {
        for (Map.Entry<String, Department> current : departmentMap.entrySet()) {
            Department transferTo = current.getValue();
            if (fromDepartment.getAverageSalary().compareTo(calculateAverageSalary(transferTo.getStaff())) < 0) {
                continue;
            }
            Collections.sort(fromDepartment.getStaff());
            List<List<Employee>> listToTransfer = fromDepartment.fromTransferList(new LinkedList<>(), 0);

            for (List<Employee> employeeList : listToTransfer) {
                BigDecimal currentAverage = transferTo.getAverageSalary();
                if (currentAverage.compareTo(calculateAverageSalary(employeeList)) < 0) {
                    StringBuffer stringBuffer = new StringBuffer("Сотрудники из отдела " + fromDepartment.getDepartmentName()
                            + ": ");
                    for (Employee employee : employeeList) {
                        stringBuffer.append(employee.getFirstName()).append(" ").append(employee.getSecondName()).append(",");
                    }
                    stringBuffer.append("в отдел ").append(current.getKey()).append("\n");
                    String information = String.valueOf(stringBuffer);
                    WorkWithFile.writeToFile(information, fileWriter);
                }
            }
        }
    }

}

