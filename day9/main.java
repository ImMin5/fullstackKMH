package day9;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        Tiger tiger = new Tiger(1);
        tiger.setLocation("land");
        tiger.setName("타이거");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

        Airplane airplane = new Flight();

        Calendar calDep = Calendar.getInstance();
        Calendar calAri = Calendar.getInstance();
        calDep.set(2022,2,1);
        calAri.set(2022,2,2);
        airplane.setDateDeparture(calDep);
        airplane.setDateArrival(calAri);

        System.out.println(airplane.dateArrival.getTime()+ " " + airplane.dateDeparture.getTime());

        airplane.getDateDeparture();
        airplane.getDateArrival();

        airplane.isDomestic("김포","도쿄");
        airplane.isDomestic("김포","제주");






    }

}
