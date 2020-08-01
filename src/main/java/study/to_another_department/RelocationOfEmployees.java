package study.to_another_department;

import java.math.BigDecimal;
import java.util.*;

import static study.to_another_department.Department.calculateAverageSalary;

public class RelocationOfEmployees {
    public static void main(String[] args) {
        if (args.length == 2) {
            Map<String, Department> departmentMap = WorkWithFile.downloadFromFile(args[0]);
            showTable(departmentMap);
            String transferInformation = transferAlternate(departmentMap);
            WorkWithFile.writeToFile(args[1], transferInformation);
            showTable(departmentMap);
        } else {
            System.out.println("Неверное количество аргументов \n" + " Нужное количество: 2!!!");
        }

    }

    static void showTable(Map<String, Department> map) {
        for (Department department : map.values()) {
            System.out.println("----Department " + department.getDepartmentName() + "----   " + department.getAverageSalary());
            for (int i = 0; i < department.getStaff().size(); i++) {
                System.out.println(department.getEmployee(i).toString());
            }
            System.out.println();
        }
    }

    static String transferAlternate(Map<String, Department> departmentMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Department fromDepartment : departmentMap.values()) {
            stringBuilder.append(performTransfer(departmentMap, fromDepartment));
        }
        return stringBuilder.toString();
    }

    static StringBuilder performTransfer(Map<String, Department> departmentMap, Department fromDepartment) {
        StringBuilder info = new StringBuilder("");
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
                    info.append("Сотрудники из отдела " + fromDepartment.getDepartmentName()
                            + ": ");
                    for (Employee employee : employeeList) {
                        info.append(employee.getFirstName()).append(" ").append(employee.getSecondName()).append(" ");
                        info.append(employee.getSalary()).append(",");
                    }
                    info.append("в отдел ").append(current.getKey()).append("\n");
                }
            }
        }
        return info;
    }

}

