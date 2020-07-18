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
            String[] decimalPoint = em[3].trim().split("\\.");
            if (decimalPoint.length > 1) {
                if (decimalPoint[1].length() > 2) {
                    throw new NumberFormatException();
                }
            }
            return new Employee(em);
        } catch (NumberFormatException e) {
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
