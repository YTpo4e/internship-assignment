import main.java.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        File file = new File(args[0]);

        Departments dps = new Departments();
        try (Scanner scanner = new Scanner(file)){

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] employee = line.split(" ");
                Employee em = new Employee(employee);


                Department al = dps.departments.get(employee[2]);

                if (al == null) {
                    al = new Department();
                    al.getStaff().add(em);
                    dps.departments.put(employee[2], al);
                } else {
                    dps.departments.get(employee[2]).getStaff().add(em);

                }

            }

        } catch (IOException e) {
            System.out.println("Cannot read file " + e.getMessage());
        }


        for (Map.Entry<String, Department> d : dps.departments.entrySet()) {
            for (int i = 0; i < d.getValue().getStaff().size(); i++) {
                System.out.println(d.getKey() + " " + d.getValue().getStaff().get(i).getFullName());
            }
        }

        dps.departments.get("IT").setAverageSalary();
        System.out.println(dps.departments.get("IT").getAverageSalary());

    }
}
