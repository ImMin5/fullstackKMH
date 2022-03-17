package day9;

import java.text.SimpleDateFormat;
import java.util.Calendar;

abstract class Airplane {
    Calendar dateDeparture;
    Calendar dateArrival;
    String locationDeparture;
    String locationArriavl;
    boolean isDomestic;

    Airplane(){}

    abstract void getDateDeparture();

    abstract void getDateArrival();

    abstract void setDateDeparture(Calendar dateDeparture);
    abstract void setDateArrival(Calendar dateArrival);
    abstract void isDomestic(String locationDeparture, String locationArriavl);
    abstract String flight();
    /*
    abstract void setLocationDeparture(String locationDeparture);
    abstract void setLocationArriavl(String locationArriavl);
*/

}

class Flight extends Airplane{

    @Override
    String flight() {
        return null;
    }
    @Override
    public void getDateDeparture() {
        System.out.println(dateDeparture.get(Calendar.DAY_OF_MONTH) + "-"+ dateDeparture.get(Calendar.MONTH) + "-" +dateDeparture.get(Calendar.YEAR));
    }
    @Override
    public void getDateArrival() {
        System.out.println(dateArrival.get(Calendar.DAY_OF_MONTH) + "-"+ dateArrival.get(Calendar.MONTH) + "-" +dateArrival.get(Calendar.YEAR));
    }
    @Override
    public void setDateDeparture(Calendar dateDeparture){
        this.dateDeparture = dateDeparture;
    };
    @Override
    public void setDateArrival(Calendar dateArrival){
        this.dateArrival = dateArrival;
    }

    @Override
    void isDomestic(String locationDeparture, String locationArriavl) {
        this.locationDeparture = locationDeparture;
        this.locationArriavl = locationArriavl;

        if("김포인천김해제주울산".contains(locationArriavl) == true && "김포인천김해제주울산".contains(locationDeparture)  == true){
            this.isDomestic = true;
        }
        else
            this.isDomestic = false;

        System.out.println("국적 " + isDomestic);
    }
}
