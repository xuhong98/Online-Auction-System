package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.EmployeeExistException;
import util.exception.EmployeeNotFoundException;
import util.exception.PasswordChangeException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.helperClass.SecurityHelper;

/**
 *
 * @author mango
 */
@Stateless
@Local(EmployeeControllerLocal.class)
@Remote(EmployeeControllerRemote.class)
public class EmployeeController implements EmployeeControllerRemote, EmployeeControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public Employee createNewEmployee(Employee employee) throws EmployeeExistException, GeneralException {
        try {
            SecurityHelper securityHelper = new SecurityHelper();
            String securedPasswrod = securityHelper.generatePassword(employee.getPassword());

            employee.setPassword(securedPasswrod);

            em.persist(employee);
            em.flush();
            em.refresh(employee);

            return employee;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new EmployeeExistException("Employee with same username already exist");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        em.merge(employee);
    }

    @Override
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :username");
        query.setParameter("username", username);

        try {
            Employee employee = (Employee) query.getSingleResult();
            return employee;

        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
        }
    }

    @Override
    public Employee retrieveEmployeeByEmployeeId(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null) {
            return employee;
        } //didn't find the employee
        else {
            throw new EmployeeNotFoundException("Employee ID " + employeeId + " does not exist");
        }
    }

    //change throw EmployeeNotFoundException to catch here, becaues won't happen in real case, because employee already log in;
    //Avoid that need to catch again in client;
    @Override
    public void changePassword(Long employeeId, String currentPassword, String newPassword) throws PasswordChangeException {
        if (currentPassword.length() > 16 || currentPassword.length() < 6) {
            throw new PasswordChangeException("Password length must be in range [6.16]!");
        }
        try {
            SecurityHelper securityHelper = new SecurityHelper();
            Employee employee = retrieveEmployeeByEmployeeId(employeeId);

            if (securityHelper.verifyPassword(currentPassword, employee.getPassword())) {
                employee.setPassword(securityHelper.generatePassword(newPassword));
            } else {
                throw new PasswordChangeException("Wrong current password!");
            }
        } catch (EmployeeNotFoundException ex) {
            //Won't happen in real case
        }
    }

    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Employee employee = retrieveEmployeeByUsername(username);
            SecurityHelper securityHelper = new SecurityHelper();
            if (securityHelper.verifyPassword(password, employee.getPassword())) {
                return employee;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or Invalid password!");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or Invalid password!");
        }
    }

    @Override
    public void deleteEmployee(Long deleteEmployee) {
        try {
            Employee employee = retrieveEmployeeByEmployeeId(deleteEmployee);
            em.remove(employee);
        } catch (EmployeeNotFoundException ex) {
            //Won't happen
        }
    }

    @Override
    public List<Employee> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        List<Employee> employees = query.getResultList();

        return employees;
    }

}
