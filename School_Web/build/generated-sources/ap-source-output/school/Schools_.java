package school;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-03T21:51:32")
@StaticMetamodel(Schools.class)
public class Schools_ { 

    public static volatile SingularAttribute<Schools, Long> id;
    public static volatile SingularAttribute<Schools, Calendar> openSince;
    public static volatile SingularAttribute<Schools, String> schoolName;
    public static volatile SingularAttribute<Schools, Byte> schoolRating;
    public static volatile SingularAttribute<Schools, Integer> capacity;
    public static volatile SingularAttribute<Schools, Long> locationID;
    public static volatile SingularAttribute<Schools, String> schoolType;

}