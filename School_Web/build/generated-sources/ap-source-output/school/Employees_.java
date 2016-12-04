package school;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import school.Employees;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-03T21:51:32")
@StaticMetamodel(Employees.class)
public class Employees_ { 

    public static volatile SingularAttribute<Employees, Long> id;
    public static volatile SingularAttribute<Employees, Long> positionID;
    public static volatile SingularAttribute<Employees, Employees> boss;
    public static volatile SingularAttribute<Employees, Calendar> hireDate;
    public static volatile SingularAttribute<Employees, Long> schoolID;
    public static volatile SingularAttribute<Employees, Byte> employeeRating;
    public static volatile SingularAttribute<Employees, Double> bonusFee;
    public static volatile SingularAttribute<Employees, String> employeeName;

}