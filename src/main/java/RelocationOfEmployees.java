import java.io.*;
import java.util.*;

public class RelocationOfEmployees {
    public static void main(String[] args){
        if (args.length == 2) {
            Map<String, Department> departmentMap = downloadFromFile(args[0]);
            showTable(departmentMap);
            transferOfEmployees(departmentMap, args[1]);
            System.out.println();
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
                Employee employee = Employee.createEmployee(counter, employeeInformation);
                if (employee == null) {
                    counter++;
                    continue;
                }

                Department department = departmentMap.get(employeeInformation[2].trim());

                if (!departmentMap.containsKey(employeeInformation[2].trim())) {
                    department = new Department();
                    departmentMap.put(employeeInformation[2].trim(), department);
                }

                department.addEmployee(employee);
                counter++;
            }

        } catch (IOException e) {
            System.out.println("Cannot read file " + e.getMessage());
        }

        return departmentMap;
    }

    static void showTable(Map<String, Department> map) {
        for (Map.Entry<String, Department> m : map.entrySet()) {
            System.out.println("----Department " + m.getKey()  + "----");
            for (int i = 0; i < m.getValue().size(); i++) {
                System.out.print(m.getValue().getEmployee(i).getSecondName() + " ");
                System.out.print(m.getValue().getEmployee(i).getFirstName() + " ");
                System.out.print(m.getValue().getEmployee(i).getPosition() + " ");
                System.out.print(m.getValue().getEmployee(i).getSalary() + " ");
                System.out.println();
            }
            System.out.println();
        }
    }

    static void transferOfEmployees(Map<String, Department> departmentMap, String out) {

        String[] keys = departmentMap.keySet().toArray(new String[departmentMap.size()]);
        Department[] departments = departmentMap.values().toArray(new Department[departmentMap.size()]);

        for (int from = 0; from < keys.length - 1; from++) {
            int toTheDepartment = from;
            for (int employeeNumber  = 0; employeeNumber < departments[from].size();  ++toTheDepartment) {
                Map<String, Department> copy = deepCloneMap(departmentMap);

                Employee employee = copy.get(keys[from]).getEmployee(employeeNumber );
                copy.get(keys[from]).removeEmployee(employee);
                copy.get(keys[toTheDepartment]).addEmployee(employee);

                if (copy.get(keys[from]).getAverageSalary() > departmentMap.get(keys[from]).getAverageSalary() &&
                    copy.get(keys[toTheDepartment]).getAverageSalary() > departmentMap.get(keys[toTheDepartment]).getAverageSalary()) {
                    System.out.println("When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[from] + " department to " + keys[toTheDepartment]);
                    System.out.println("Was: " + keys[from] + "department " + departmentMap.get(keys[from]).getAverageSalary());
                    System.out.println("Now: " + keys[from] +"department  " + copy.get(keys[from]).getAverageSalary());
                    System.out.println("Was: " + keys[toTheDepartment] + "department " + departmentMap.get(keys[toTheDepartment]).getAverageSalary());
                    System.out.println("Now: " + keys[toTheDepartment] +"department " + copy.get(keys[toTheDepartment]).getAverageSalary());
                    System.out.println();
                    String transferInformation = "When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[from] + " department to " + keys[toTheDepartment] + "\n";
                    writeToFile(transferInformation, out);

                }

                if (toTheDepartment == keys.length - 1) {
                    toTheDepartment = from;
                    employeeNumber ++;
                }
            }

        }

        for (int from = keys.length - 1; from >= 1; from--) {
            int  toTheDepartment = from;
            for (int employeeNumber = 0; employeeNumber < departments[from].size();  toTheDepartment--) {
                Map<String, Department> copy = deepCloneMap(departmentMap);

                Employee employee = copy.get(keys[from]).getEmployee(employeeNumber);
                copy.get(keys[from]).removeEmployee(employee);
                copy.get(keys[ toTheDepartment]).addEmployee(employee);
                if (copy.get(keys[from]).getAverageSalary() > departmentMap.get(keys[from]).getAverageSalary() &&
                        copy.get(keys[ toTheDepartment]).getAverageSalary() > departmentMap.get(keys[ toTheDepartment]).getAverageSalary()) {
                    System.out.println("When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[from] + " department to " + keys[ toTheDepartment]);
                    System.out.println("Was: " + keys[from] + "department " + departmentMap.get(keys[from]).getAverageSalary());
                    System.out.println("Now: " + keys[from] + "department " + copy.get(keys[from]).getAverageSalary());
                    System.out.println("Was " + keys[ toTheDepartment] + "department " + departmentMap.get(keys[ toTheDepartment]).getAverageSalary());
                    System.out.println("Now: " + keys[ toTheDepartment] +" department " + copy.get(keys[ toTheDepartment]).getAverageSalary());
                    System.out.println();

                    String transferInformation = "When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[from] + " department to " + keys[ toTheDepartment] + "\n";
                    writeToFile(transferInformation, out);

                }
                if (toTheDepartment == 0) {
                    employeeNumber++;
                    toTheDepartment = from;
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

