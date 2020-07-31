package study.to_another_department;

import java.math.BigDecimal;


public class Employee implements Comparable<Employee> {
    final private String firstName;
    final private String secondName;
    private BigDecimal salary;
    private String position;


    Employee(String line) {
        String[] infoEmployee = line.split(";");
        firstName = infoEmployee[0].trim();
        secondName = infoEmployee[1].trim();
        salary = new BigDecimal(infoEmployee[3].trim());
        position = infoEmployee[4].trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    public static boolean checkingArguments(int counter, String line) {
        String[] infoEmployee = line.split(";");
        try {
            if (infoEmployee.length != 5) {
                throw new Exception("Неверное количество аргументов в строке " + counter + "\n" + "Нужное количество 5!!!");
            }
            if (infoEmployee[0].trim().length() == 0) {
                throw new Exception("Не указано имя!");
            }
            if (infoEmployee[1].trim().length() == 0) {
                throw new Exception("Не указана фамилия!");
            }
            if (infoEmployee[2].trim().length() == 0) {
                throw new Exception("Не укзан отдел!");
            }
            if (infoEmployee[3].trim().length() == 0) {
                throw new Exception("Не указана зарплата!");
            }
            String[] decimalPoint = infoEmployee[3].trim().split("\\.");
            if (decimalPoint.length > 1) {
                if (decimalPoint[1].length() > 2) {
                    throw new Exception("Зарплата указана неверна!");
                }
            }
            if (infoEmployee[4].trim().length() == 0) {
                throw new Exception("Не указана должность!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage() + "Строка " + counter);
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(Employee o) {
        return getSalary().compareTo(o.getSalary());
    }

    @Override
    public String toString() {
        return secondName + " " + firstName + " " + position + " " + salary + ". ";
    }
}
