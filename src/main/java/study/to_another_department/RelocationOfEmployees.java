package study.to_another_department;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static study.to_another_department.Department.calculateAverageSalary;

public class RelocationOfEmployees {
    public static void main(String[] args){
        if (args.length == 2) {
            Map<String, Department> departmentMap = downloadFromFile(args[0]);
            showTable(departmentMap);
            FileWriter fileWriter = openFile(args[1]);
            if (!fileWriter.equals(null) ) {
            transferAlternate(departmentMap, fileWriter);
            showTable(departmentMap);
            closeFile(fileWriter);
            }

        } else {
            System.out.println("Wrong number of arguments entered.");
        }

    }

    private static Map<String, Department> downloadFromFile(String input) {
        File file = new File(input);

        Map<String, Department> departmentMap = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
        int counter = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] employeeInformation = line.split(";");

                Employee employee = Employee.createEmployee(counter, employeeInformation);

                if (employee == null) {
                    counter++;
                    continue;
                }

                Department department = departmentMap.getOrDefault(employeeInformation[2].trim(), new Department());
                department.addEmployee(employee);
                departmentMap.putIfAbsent(employeeInformation[2].trim(), department);

                counter++;

            }

        } catch (IOException e) {
            System.out.println("Cannot read file " + e.getMessage());
        }

        return departmentMap;
    }

    static void showTable(Map<String, Department> map) {
        for (Map.Entry<String, Department> m : map.entrySet()) {
            System.out.println("----Department " + m.getKey()  + "----   " + m.getValue().getAverageSalary());
            for (int i = 0; i < m.getValue().getStaff().size(); i++) {
                System.out.print(m.getValue().getEmployee(i).getSecondName() + " ");
                System.out.print(m.getValue().getEmployee(i).getFirstName() + " ");
                System.out.print(m.getValue().getEmployee(i).getPosition() + " ");
                System.out.print(m.getValue().getEmployee(i).getSalary() + " ");
                System.out.println();
            }
            System.out.println();
        }
    }

    static void transferAlternate(Map<String, Department> departmentMap, FileWriter fileWriter) {
        for (Map.Entry<String, Department> departments : departmentMap.entrySet()) {
            performTransfer(departmentMap, departments, fileWriter);
        }
    }

    static void performTransfer(Map<String, Department> tempDepartment, Map.Entry<String, Department> departments,
                                FileWriter fileWriter) {
        for (Map.Entry<String, Department> current : tempDepartment.entrySet()) {
            Department transferTo = current.getValue();
            if(transferTo.equals(departments.getValue())) {
                continue;
            }
            departments.getValue().fromTransferList(0);
            List<List<Employee>> listToTransfer = departments.getValue().transferList;

            //for (List<Employee> employeeList : listToTransfer){
             //   String information;
                 for (List<Employee> employeeList : listToTransfer) {
                     BigDecimal currentAverage = transferTo.getAverageSalary();

                     if (currentAverage.compareTo(calculateAverageSalary(employeeList)) == -1) {
                         String information = "Сотрудники из отдела " + departments.getKey() + ": ";
                         for (Employee employee: employeeList) {
                             information += employee.getFirstName() + " " + employee.getSecondName() + ",";
                         }

                         information += " в отдел "  + current.getKey() +"\n";
                         writeToFile(information, fileWriter);

                     }
                }
            }
        }



    static void writeToFile(String information, FileWriter fileWriter)  {
        try {
            fileWriter.write(information);
        } catch (IOException e) {
            System.out.println("Не удалось записать в файл");
        }
    }

    static FileWriter openFile(String out) {
        File file = new File(out);
        try {
             return new FileWriter(file, true);
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
        }
        return null;
    }

    static void closeFile(FileWriter fileWriter) {
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть файл");
        }
    }
}

