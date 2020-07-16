import java.math.BigDecimal;

public class Employee {
    private String firstName;
    private String secondName;
    private BigDecimal salary;
    private String position;

    private Employee(String[] employee) {
        firstName = employee[0];
        secondName = employee[1];
        salary = new BigDecimal(employee[3]);
        position = employee[4];
    }


    public static Employee createEmployee(int counter, String... em) {
        try {
            String[] decimalPoint = em[3].split("\\.");
            if (decimalPoint.length > 1) {
                if (decimalPoint[1].length() > 2) {
                    throw new IllegalAccessException();
                }
            }
            return new Employee(em);
        } catch (IllegalAccessException e) {
            System.out.println("Line " + counter + " contains an incorrect salary.");
        } catch (Exception e) {
            System.out.println("Incorrect data entered in line " + counter + ".");
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
}
