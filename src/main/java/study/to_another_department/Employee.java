package study.to_another_department;

import java.math.BigDecimal;

public class Employee {
    final private String firstName;
    final private String secondName;
    private BigDecimal salary;
    private String position;

    private Employee(String[] employee) {
        firstName = employee[0].trim();
        secondName = employee[1].trim();
        salary = new BigDecimal(employee[3].trim());
        position = employee[4].trim();
    }


    public static Employee createEmployee(int counter, String... em) {
        try {
            if (checkingForEmptyArguments(em)) {
                throw new NullPointerException();
            }
            String[] decimalPoint = em[3].trim().split("\\.");
            if (decimalPoint.length > 1) {
                if (decimalPoint[1].length() > 2) {
                    throw new NumberFormatException(String.valueOf(counter));
                }
            }
            return new Employee(em);
        } catch (NumberFormatException e) {
            System.out.println("Line " + e.getMessage() + " contains an incorrect salary. ");
        } catch (NullPointerException e) {
            System.out.println("Какое-то значение пустое в строке "  + counter + ".");
        }
        return null;
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

    static boolean checkingForEmptyArguments(String... infoEmployee) {
        for (String string : infoEmployee) {
            if (string.matches("\\s*")) {
                return true;
            }
        }
        return false;
    }
}
