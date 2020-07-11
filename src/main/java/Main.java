import main.java.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        File file = new File(args[0]);

        Map<String, Department> dps = new HashMap<>();
        try (Scanner scanner = new Scanner(file)){

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] employee = line.split(" ");
                Employee em = new Employee(employee);


                Department al = dps.get(employee[2]);

                if (al == null) {
                    al = new Department();
                    al.getStaff().add(em);
                    dps.put(employee[2], al);
                } else {
                    dps.get(employee[2]).getStaff().add(em);

                }

            }

        } catch (IOException e) {
            System.out.println("Cannot read file " + e.getMessage());
        }

        dps.get("IT").setAverageSalary();
        dps.get("IT").infoSalary();

    }
}
