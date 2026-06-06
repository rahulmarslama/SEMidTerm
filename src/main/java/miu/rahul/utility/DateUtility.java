package miu.rahul.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;

public class DateUtility {

    public static boolean isSameWeek(LocalDateTime date1, LocalDateTime date2) {
        int week1 = date1.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int week2 = date2.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int weekBasedYear1 = date1.get(IsoFields.WEEK_BASED_YEAR);
        int weekBasedYear2 = date2.get(IsoFields.WEEK_BASED_YEAR);

        return week1 == week2 && weekBasedYear1 == weekBasedYear2;
    }

    public static boolean isInNextQuarter(LocalDate now, LocalDate targetDate) {
        int currentQuarter = (now.getMonthValue() - 1) / 3 + 1;
        int targetQuarter = (targetDate.getMonthValue() - 1) / 3 + 1;
        
        int nextQuarter = currentQuarter == 4 ? 1 : currentQuarter + 1;
        int nextQuarterYear = currentQuarter == 4 ? now.getYear() + 1 : now.getYear();
        
        return targetQuarter == nextQuarter && targetDate.getYear() == nextQuarterYear;
    }
}
