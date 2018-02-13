package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import util.exception.EmployeeExistException;
import util.exception.EmployeeNotFoundException;
import util.exception.PasswordChangeException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author mango
 */
public interface EmployeeControllerLocal {

    Employee createNewEmployee(Employee employee) throws EmployeeExistException, GeneralException;

    void updateEmployee(Employee employee);

    Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;

    Employee retrieveEmployeeByEmployeeId(Long employeeId) throws EmployeeNotFoundException;

    void changePassword(Long employeeId, String currentPassword, String newPassword) throws EmployeeNotFoundException, PasswordChangeException;

    public Employee employeeLogin(String parameter, String parameter1) throws InvalidLoginCredentialException;

    public void deleteEmployee(Long deleteEmployee);

    public List<Employee> retrieveAllEmployees();

}
