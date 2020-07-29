package study.to_another_department;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WorkWithFile {
    public static Map<String, Department> downloadFromFile(String input) {
        File file = new File(input);

        Map<String, Department> departmentMap = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            int counter = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!Employee.checkingArguments(counter, line)) {
                    counter++;
                    continue;
                }
                String departmentName = line.split(";")[2].trim();
                Employee employee = new Employee(line);
                Department department = departmentMap.getOrDefault(departmentName, new Department());
                department.setDepartmentName(departmentName);
                department.addEmployee(employee);
                departmentMap.putIfAbsent(departmentName, department);
                counter++;
            }
        } catch (IOException e) {
            System.out.println("Cannot read file " + e.getMessage());
        }

        return departmentMap;
    }

    public static void writeToFile(String information, FileWriter fileWriter) {
        try {
            fileWriter.write(information);
        } catch (IOException e) {
            System.out.println("Не удалось записать в файл");
        }
    }
}
