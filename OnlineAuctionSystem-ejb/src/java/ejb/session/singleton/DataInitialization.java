
package ejb.session.singleton;

import ejb.session.stateless.EmployeeControllerLocal;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.EmployeeType;
import util.exception.EmployeeExistException;
import util.exception.EmployeeNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
@Singleton
@Startup
@LocalBean
public class DataInitialization {

    @EJB
    private EmployeeControllerLocal employeeControllerLocal;
    
    public DataInitialization() {
    }
    
    
    @PostConstruct
    public void postConstruct(){
        try{
            employeeControllerLocal.retrieveEmployeeByUsername("admin");
        } catch (EmployeeNotFoundException ex) {
            initializaData();
        }
    }
    
    private void initializaData(){
        
        Employee newEmployee = new Employee("Firstname", "Surname", "admin", "password", EmployeeType.SYSTEMADMIN);
        
        try {
            employeeControllerLocal.createNewEmployee(newEmployee);
        } catch (EmployeeExistException ex) {
            System.err.println("***Employee Already Exists: same username***");
        } catch (GeneralException ex) {
            System.err.println("***Unexpected Error!***");
        }
    }
}
