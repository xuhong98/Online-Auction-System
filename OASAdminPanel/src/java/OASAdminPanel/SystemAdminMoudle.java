package OASAdminPanel;

import ejb.session.stateless.EmployeeControllerRemote;
import entity.Employee;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeType;
import util.exception.EmployeeExistException;
import util.exception.EmployeeNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author mango
 */
public class SystemAdminMoudle {

    private EmployeeControllerRemote employeeControllerRemote;
    private Long currentEmployeeId;

    //constructor 
    public SystemAdminMoudle() {

    }

    public SystemAdminMoudle(EmployeeControllerRemote employeeControllerRemote, Long currentId) {
        this();
        this.currentEmployeeId = currentId;
        this.employeeControllerRemote = employeeControllerRemote;
    }

    public void menuSystemAdmin() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS Admin Panel :: System Admin ***\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View Employee Details");
            System.out.println("3: View All Employees");
            System.out.println("4: Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 4) {
                System.out.print("> \033[34m");
                try {
                    response = scanner.nextInt();
                    switch (response) {
                        case 1:
                            doCreateNewEmployee();
                            break;
                        case 2:
                            doViewEmployeeDetails();
                            break;
                        case 3:
                            doViewAllEmployees();
                            break;
                        case 4:
                            break OUTER;
                        default:
                            System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number!\033[34m");
                    scanner.nextLine();
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    public void doCreateNewEmployee() {
        Scanner scanner = new Scanner(System.in);
        Employee newEmployee = new Employee();

        System.out.println("\n*** OAS Admin Panel :: System Admin :: Create New Employee ***\n");
        while (true) {
            System.out.print("Enter First Name> ");
            String firstName = scanner.nextLine().trim();
            if (firstName.length() > 0) {
                newEmployee.setFirstName(firstName);
                break;
            }
        }

        while (true) {
            System.out.print("Enter Last Name> ");
            String lastName = scanner.nextLine().trim();
            if (lastName.length() > 0) {
                newEmployee.setLastName(lastName);
                break;
            }
        }

        while (true) {
            System.out.print("Select Employee Type (1: System Admin, 2: Finance, 3: Sales)> ");
            try {
                Integer employeeTypeInt = scanner.nextInt();

                if (employeeTypeInt >= 1 && employeeTypeInt <= 3) {
                    newEmployee.setEmployeeType(EmployeeType.values()[employeeTypeInt - 1]);
                    break;
                } else {
                    System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        while (true) {
            System.out.print("Enter Username> ");
            String username = scanner.nextLine().trim();
            if (username.length() > 0) {
                newEmployee.setUsername(username);
                break;
            }
        }

        while (true) {
            System.out.print("Enter Password([6,16] characters)> ");
            String pass1 = scanner.nextLine().trim();

            System.out.print("Confirm Password> ");
            String pass2 = scanner.nextLine().trim();

            if (pass1.length() > 16 || pass1.length() < 6) {
                System.out.println("\033[31m[Warning] Password length should be in range [6,16]\033[34m");
                continue;
            }
            if (pass1.equals(pass2)) {
                newEmployee.setPassword(pass1);
                break;
            } else {
                System.out.println("\033[31m[Warning]Two input did not match! Please input again!\033[34m");
            }
        }

        try {
            newEmployee = employeeControllerRemote.createNewEmployee(newEmployee);
            System.out.println("\n[System] Emoloyee " + newEmployee.getUsername() + " has been successfully created!");
            System.out.print("\nPress Enter to continue...>");
            scanner.nextLine();
        } catch (EmployeeExistException ex) {
            System.out.println("\033[31m\n[Warning] Employee with same username already exist! \n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        } catch (GeneralException ex) {
            System.out.println("\033[31m\n[Error] An unexpected error has occurred while creating the new staff: " + ex.getMessage() + "\n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        }
    }

    public void doViewEmployeeDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** OAS Admin Panel :: System Admin :: View Employee Details ***\n");

        System.out.print("Enter Employee ID> ");
        try {
            Long employeeId = scanner.nextLong();
            try {
                while (true) {
                    Employee employee = employeeControllerRemote.retrieveEmployeeByEmployeeId(employeeId);

                    String username = employee.getUsername();
                    if (employee.getEmployeeId().equals(currentEmployeeId)) {
                        username += "[YOU]";
                    }

                    String leftAlignFormat = "| %-20s | %-20s |%n";

                    System.out.format("\033[34m\n|------------ Employee Details  --------------|%n\033[0m");

                    System.out.format("+----------------------+----------------------+%n");
                    System.out.format("| Column               |Details               |%n");
                    System.out.format("+----------------------+----------------------+%n");

                    System.out.printf(leftAlignFormat, "Employee ID", employee.getEmployeeId());
                    System.out.printf(leftAlignFormat, "First Name", employee.getFirstName());
                    System.out.printf(leftAlignFormat, "Last Name", employee.getLastName());
                    System.out.printf(leftAlignFormat, "Employee Type", employee.getEmployeeType());
                    System.out.printf(leftAlignFormat, "Username", username);
                    System.out.format("+----------------------+----------------------+%n\n");

                    System.out.println("1: Update employee");
                    System.out.println("2: Delete employee");
                    System.out.println("3: Back\n");
                    response = 0;

                    while (response < 1 || response > 3) {
                        try {
                            System.out.print("> ");
                            response = scanner.nextInt();

                            if (response == 1) {
                                doUpdateEmployee(employee);
                            } else if (response == 2) {
                                doDeleteEmployee(employee);
                            } else if (response == 3) {
                                break;
                            } else {
                                System.out.println("\033[31mInvalid option, please try again!\n\033[34m");
                            }

                        } catch (InputMismatchException ex) {
                            System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                            scanner.nextLine();
                        }
                    }

                    if (response == 3) {
                        break;
                    }
                }
            } catch (EmployeeNotFoundException ex) {
                System.out.println("\033[31m\n[Warning] Employee ID " + employeeId + " does not exist. Please try again. \n\033[34m");
            }
        } catch (InputMismatchException ex) {
            System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\n\033[34m");
            scanner.nextLine();
        }

    }

    public void doViewAllEmployees() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** OAS Admin Panel :: System Admin :: View All Employees ***\n");

        List<Employee> employees = employeeControllerRemote.retrieveAllEmployees();
        System.out.printf("%5s%20s%20s%20s%20s\n", "ID", "First Name", "Last Name", "Employee Type", "Username");

        for (Employee employee : employees) {
            String username = employee.getUsername();
            if (employee.getEmployeeId().equals(currentEmployeeId)) {
                username += "[YOU]";
            }
            System.out.printf("%5s%20s%20s%20s%20s\n", employee.getEmployeeId().toString(), employee.getFirstName(), employee.getLastName(), employee.getEmployeeType().toString(), username);
        }

        System.out.print("\nPress Enter to continue...> ");
        scanner.nextLine();
    }

    public void doUpdateEmployee(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** OAS Admin Panel :: System Admin :: View Employee Details :: Update employee ***\n");
        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            employee.setFirstName(input);
        }

        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            employee.setLastName(input);
        }

        while (true) {
            System.out.print("Select Employee Type (0: No Change, 1: System Admin, 2: Finance, 3: Sales)> ");
            try {
                Integer emplyeeTypeInt = scanner.nextInt();

                if (emplyeeTypeInt >= 1 && emplyeeTypeInt <= 3) {
                    employee.setEmployeeType(EmployeeType.values()[emplyeeTypeInt - 1]);
                    break;
                } else if (emplyeeTypeInt == 0) {
                    break;
                } else {
                    System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        System.out.print("Enter Username (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            employee.setUsername(input);
        }

        employeeControllerRemote.updateEmployee(employee);
        System.out.println("\nEmployee updated successfully!\n");
        System.out.print("\nPress Enter to continue...>");
        scanner.nextLine();
    }

    public void doDeleteEmployee(Employee employee) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** OAS Admin Panel :: System Admin :: View Employee Details :: Delete Employee ***\n");
        System.out.printf("Confirm Delete Employee %s %s (Employee ID: %d) \n(Enter 'Y' to Delete. Enter others if you don't want to delete)> ", employee.getFirstName(), employee.getLastName(), employee.getEmployeeId());
        String input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            employeeControllerRemote.deleteEmployee(employee.getEmployeeId());
            System.out.println("\n[System] Employee " + employee.getFirstName() + " " + employee.getLastName() + " has been successfully deleted!\n");
        } else {
            System.out.println("\033[31m\n[Warning] Employee NOT deleted!\n\033[34m");
        }

        System.out.print("Press Enter to continue...>");
        scanner.nextLine();
    }
}
