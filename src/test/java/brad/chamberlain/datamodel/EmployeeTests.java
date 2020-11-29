package brad.chamberlain.datamodel;

import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTests {

    @Test
    public void testDateFormaterYYYYMMDD(){
        Employee e = new Employee();
        e.setPurchaseDate("20201127");
        assertEquals(2020, e.getPurchaseDate().getYear());
        assertEquals(Month.NOVEMBER, e.getPurchaseDate().getMonth());
        assertEquals( 27, e.getPurchaseDate().getDayOfMonth());
    }
}
