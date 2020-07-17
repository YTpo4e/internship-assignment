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

                Department department = departmentMap.get(employeeInformation[2]);

                if (!departmentMap.containsKey(employeeInformation[2])) {
                    department = new Department();
                    departmentMap.put(employeeInformation[2], department);
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

    static void transferOfEmployees(Map<String, Department> map, String out) {

        String[] keys = map.keySet().toArray(new String[map.size()]);
        Department[] departments = map.values().toArray(new Department[map.size()]);

        for (int i = 0; i < keys.length - 1; i++) {
            int counter = i;
            for (int j = 0; j < departments[i].size(); counter++) {
                Map<String, Department> copy = deepCloneMap(map);

                Employee employee = copy.get(keys[i]).getEmployee(j);
                copy.get(keys[i]).removeEmployee(employee);
                copy.get(keys[counter]).addEmployee(employee);

                if (copy.get(keys[i]).getAverageSalary() > map.get(keys[i]).getAverageSalary() &&
                    copy.get(keys[counter]).getAverageSalary() > map.get(keys[counter]).getAverageSalary()) {
                    System.out.println("When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[i] + " department to " + keys[counter]);
                    System.out.println("Was: " + keys[i] + "department " + map.get(keys[i]).getAverageSalary());
                    System.out.println("Now: " + keys[i] +"department  " + copy.get(keys[i]).getAverageSalary());
                    System.out.println("Was: " + keys[counter] + "department " + map.get(keys[counter]).getAverageSalary());
                    System.out.println("Now: " + keys[counter] +"department " + copy.get(keys[counter]).getAverageSalary());
                    System.out.println();
                    String transferInformation = "When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[i] + " department to " + keys[counter] + "\n";
                    writeToFile(transferInformation, out);

                }

                if (counter == keys.length - 1) {
                    counter = i;
                    j++;
                }
            }

        }

        for (int i = keys.length - 1; i >= 1; i--) {
            int counter = i;
            for (int j = 0; j < departments[i].size(); counter--) {
                Map<String, Department> copy = deepCloneMap(map);

                Employee employee = copy.get(keys[i]).getEmployee(j);
                copy.get(keys[i]).removeEmployee(employee);
                copy.get(keys[counter]).addEmployee(employee);
                if (copy.get(keys[i]).getAverageSalary() > map.get(keys[i]).getAverageSalary() &&
                        copy.get(keys[counter]).getAverageSalary() > map.get(keys[counter]).getAverageSalary()) {
                    System.out.println("When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[i] + " department to " + keys[counter]);
                    System.out.println("Was: " + keys[i] + "department " + map.get(keys[i]).getAverageSalary());
                    System.out.println("Now: " + keys[i] + "department " + copy.get(keys[i]).getAverageSalary());
                    System.out.println("Was " + keys[counter] + "department " + map.get(keys[counter]).getAverageSalary());
                    System.out.println("Now: " + keys[counter] +" department " + copy.get(keys[counter]).getAverageSalary());
                    System.out.println();

                    String transferInformation = "When transferring an employee " + employee.getFirstName() + " " +
                            employee.getSecondName() + " the " + keys[i] + " department to " + keys[counter] + "\n";
                    writeToFile(transferInformation, out);

                }
                if (counter == 0) {
                    j++;
                    counter = i;
                }
            }
        }

    }

    static Map<String, Department> deepCloneMap(Map<String, Department> original)  {
        Map<String, Department> copy = new HashMap<>(original.size());

        for (String s : original.keySet()) {
            Department d = new Department(original.get(s).getStaff());
            copy.put(s, d);
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

