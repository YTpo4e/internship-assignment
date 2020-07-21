package study.to_another_department;

import java.io.*;
import java.util.*;

public class RelocationOfEmployees {
    public static void main(String[] args){
        if (args.length == 2) {
            Map<String, Department> departmentMap = downloadFromFile(args[0]);
            showTable(departmentMap);
            transferAlternate(departmentMap, args[1]);
            showTable(departmentMap);

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

                if (employeeInformation.length == 5) {
                    Employee employee = Employee.createEmployee(counter, employeeInformation);

                    if (employee == null) {
                        counter++;
                        continue;
                    }

                    Department department = departmentMap.getOrDefault(employeeInformation[2].trim(), new Department());
                    department.addEmployee(employee);
                    departmentMap.putIfAbsent(employeeInformation[2].trim(), department);

                    counter++;
                } else {
                    System.out.println("Неверное количство аргументов в строке " + counter + ".");
                    counter++;
                }
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

    static void transferAlternate(Map<String, Department> departmentMap, String out) {
        for (Map.Entry<String, Department> departments : departmentMap.entrySet()) {
            Map<String, Department> tempDepartments = deepCloneMap(departmentMap);
            Department department = departments.getValue();
            String nameDepartment = departments.getKey();

            performTransfer(tempDepartments, department, nameDepartment, out);
        }
    }

    static void performTransfer(Map<String, Department> tempDepartment, Department department, String nameDepartment,
                                String out) {
        for (Map.Entry<String, Department> current : tempDepartment.entrySet()) {
            Department transferTo = current.getValue();
            String nameDepartment1 = current.getKey();
            if(transferTo.equals(department)) {
                continue;
            }

            List<Employee> listToTransfer = department.fromTransferList();

            for (Employee employee : listToTransfer) {
                double currentAverage = transferTo.getAverageSalary();
                if (currentAverage < employee.getSalary().doubleValue()) {
                    String information = "Сотрудник " + employee.getFirstName() + " " + employee.getSecondName() +
                            " : из " + nameDepartment + " в " + nameDepartment1 +"\n";
                    System.out.println(information);
                    writeToFile(information, out);
                }
            }
        }
    }

    static Map<String, Department> deepCloneMap(Map<String, Department> original)  {
        Map<String, Department> copy = new HashMap<>(original.size());

        for (String s : original.keySet()) {
            Department department = new Department(original.get(s).getStaff());
            copy.put(s, department);
        }
        return copy;
    }

    static void writeToFile(String information, String out) {
        File file = new File(out);
        try (FileWriter fileWriter = new FileWriter(out, true)) {
            fileWriter.write(information);
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
        }
    }
}

