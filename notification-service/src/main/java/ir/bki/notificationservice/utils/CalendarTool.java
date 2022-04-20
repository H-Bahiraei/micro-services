package ir.bki.notificationservice.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Title: Calender Conversion class Description: Convert Iranian (Jalali),
 * Julian, and Gregorian dates to each other Public Methods Summary:
 * ----------------------- JavaSource_Calendar(); JavaSource_Calendar(int year,
 * int month, int day); int getIranianYear(); int getIranianMonth(); int
 * getIranianDay(); int getGregorianYear(); int getGregorianMonth(); int
 * getGregorianDay(); int getJulianYear(); int getJulianMonth(); int
 * getJulianDay(); String getIranianDate(); String getGregorianDate(); String
 * getJulianDate(); String getWeekDayStr(); String toString(); int
 * getDayOfWeek(); void nextDay(); void nextDay(int days); void previousDay();
 * void previousDay(int days); void setIranianDate(int year, int month, int
 * day); void setGregorianDate(int year, int month, int day); void
 * setJulianDate(int year, int month, int day);
 */
public class CalendarTool {

    private static CalendarTool _Instance;
    private int irYear; // Year part of a Iranian date
    private int irMonth; // Month part of a Iranian date
    private int irDay; // Day part of a Iranian date
    private int gYear; // Year part of a Gregorian date
    private int gMonth; // Month part of a Gregorian date
    private int gDay; // Day part of a Gregorian date
    private int juYear; // Year part of a Julian date
    private int juMonth; // Month part of a Julian date
    private int juDay; // Day part of a Julian date
    private int leap; // Number of years since the last leap year (0 to 4)
    private int JDN; // Julian Day Number
    private int march; // The march day of Farvardin the first (First day of jaYear)

    /**
     * JavaSource_Calendar: The default constructor uses the current Gregorian
     * date to initialize the other private memebers of the class (Iranian and
     * Julian dates).
     */
    public CalendarTool() {
        Calendar calendar = new GregorianCalendar();
        setGregorianDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * JavaSource_Calendar: This constructor receives a Gregorian date and
     * initializes the other private members of the class accordingly.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    public CalendarTool(int year, int month, int day) {
        setGregorianDate(year, month, day);
    }

    public static CalendarTool get_Instance() {
        if (_Instance == null) _Instance = new CalendarTool();
        return _Instance;
    }

    private void dsad() {
        setGregorianDate(1390, 2, 1);
    }

    /**
     * getDayOfWeek: Returns the week day number. Monday=0..Sunday=6;
     *
     * @return int
     */
    public int getDayOfWeek() {
        return (JDN % 7);
    }

    public int getDayOfWeekIranian() {
        return (getDayOfWeek() + 2) % 7;
    }

    /**
     * getGregorianDate: Returns a string version of Gregorian date
     *
     * @return String
     */
    public String getGregorianDate() {
        return (gYear + "/" + gMonth + "/" + gDay);
    }

    /**
     * getGregorianDay: Returns the 'day' part of the Gregorian date.
     *
     * @return int
     */
    public int getGregorianDay() {
        return gDay;
    }

    /**
     * getGregorianMonth: Returns the 'month' part of the Gregorian date.
     *
     * @return int
     */
    public int getGregorianMonth() {
        return gMonth;
    }

    /**
     * getGregorianYear: Returns the 'year' part of the Gregorian date.
     *
     * @return int
     */
    public int getGregorianYear() {
        return gYear;
    }

    /**
     * getIranianDate: Returns a string version of Iranian date
     *
     * @return String
     */
    public String getIranianDate() {
        return getIranianDate("/");
    }

    public String getIranianDate(String separator) {
        return (padd(irYear + "", 4) + separator + padd(irMonth + "", 2) + separator + padd(irDay + "", 2));
    }

    public String getIranianDate10Char() {
        return (padd(irYear + "", 4) + "/" + padd(irMonth + "", 2) + "/" + padd(irDay + "", 2));
    }

    public String getIranianDate8Char() {//13940502
        return (padd(irYear + "", 4) + padd(irMonth + "", 2) + padd(irDay + "", 2));
    }

    public String getIranianDate6Char() {//940502
        String y = (irYear + "").substring(2, 4);
        return (y + padd(irMonth + "", 2) + padd(irDay + "", 2));
    }

    public String getIranianDateElastic() {//1394.05.02
        return getIranianDateElastic(".");
//        return (irYear +  "." +padd(irMonth+"",2)  +  "." +padd(irDay+"",2));
    }

    public String getIranianMonthElastic() {//1394.05 //z-darvishian 980520
        return (irYear + "." + padd(irMonth + "", 2));
    }

    //added by -darvishian on 1397/11/21
    public String getIranianYearElastic() {//1394.05.02
        return (irYear + "");
    }

    public String getIranianDateElastic(String seperator) {//1394.05.02
        return (irYear + seperator + padd(irMonth + "", 2) + seperator + padd(irDay + "", 2));
    }

    /**
     * getIranianDay: Returns the 'day' part of the Iranian date.
     *
     * @return int
     */
    public int getIranianDay() {
        return irDay;
    }

    /**
     * getIranianMonth: Returns the 'month' part of the Iranian date.
     *
     * @return int
     */
    public int getIranianMonth() {
        return irMonth;
    }

    /**
     * getIranianYear: Returns the 'year' part of the Iranian date.
     *
     * @return int
     */
    public int getIranianYear() {
        return irYear;
    }

    /**
     * getJulianDate: Returns a string version of Julian date
     *
     * @return String
     */
    public String getJulianDate() {
        return (juYear + "/" + juMonth + "/" + juDay);
    }

    /**
     * getJulianDay() Returns the 'day' part of the Julian date.
     *
     * @return int
     */
    public int getJulianDay() {
        return juDay;
    }

    /**
     * getJulianMonth: Returns the 'month' part of the Julian date.
     *
     * @return int
     */
    public int getJulianMonth() {
        return juMonth;
    }

    /**
     * getJulianYear: Returns the 'year' part of the Julian date.
     *
     * @return int
     */
    public int getJulianYear() {
        return juYear;
    }

    /**
     * getWeekDayStr: Returns the week day name.
     *
     * @return String
     */
    public String getWeekDayStr() {
        String weekDayStr[] = {
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"};

//        String weekDayStr[] = {
//            "دوشنبه",
//            "سه شنبه",
//            "چهارشنبه",
//            "پنجشنبه",
//            "آدینه",
//            "شنبه",
//            "یکشنبه"};
        return (weekDayStr[getDayOfWeek()]);
    }

    /**
     * gergorianDateToJDN: Calculates the julian day number (JDN) from Gregorian
     * calendar dates. This integer number corresponds to the noon of the date
     * (i.e. 12 hours of Universal Time). This method was tested to be good
     * (valid) since 1 March, -100100 (of both calendars) up to a few millions
     * (10^6) years into the future. The algorithm is based on D.A.Hatcher,
     * Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by K.M. Borkowski,
     * Post.Astron. 25(1987), 275-279.
     *
     * @param year  int
     * @param month int
     * @param day   int
     * @return int
     */
    private int gregorianDateToJDN(int year, int month, int day) {
        int jdn = (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408;
        jdn = jdn - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
        return (jdn);
    }

    /**
     * IranianCalendar: This method determines if the Iranian (Jalali) year is
     * leap (366-day long) or is the common year (365 days), and finds the day
     * in March (Gregorian Calendar)of the first day of the Iranian year
     * ('irYear').Iranian year (irYear) ranges from (-61 to 3177).This method
     * will set the following private data members as follows: leap: Number of
     * years since the last leap year (0 to 4) Gy: Gregorian year of the
     * begining of Iranian year march: The March day of Farvardin the 1st (first
     * day of jaYear)
     */
    private void IranianCalendar() {
        // Iranian years starting the 33-year rule
        int Breaks[]
                = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
                1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        int jm, N, leapJ, leapG, jp, j, jump;
        gYear = irYear + 621;
        leapJ = -14;
        jp = Breaks[0];
        // Find the limiting years for the Iranian year 'irYear'
        j = 1;
        do {
            jm = Breaks[j];
            jump = jm - jp;
            if (irYear >= jm) {
                leapJ += (jump / 33 * 8 + (jump % 33) / 4);
                jp = jm;
            }
            j++;
        } while ((j < 20) && (irYear >= jm));
        N = irYear - jp;
        // Find the number of leap years from AD 621 to the begining of the current
        // Iranian year in the Iranian (Jalali) calendar
        leapJ += (N / 33 * 8 + ((N % 33) + 3) / 4);
        if (((jump % 33) == 4) && ((jump - N) == 4)) {
            leapJ++;
        }
        // And the same in the Gregorian date of Farvardin the first
        leapG = gYear / 4 - ((gYear / 100 + 1) * 3 / 4) - 150;
        march = 20 + leapJ - leapG;
        // Find how many years have passed since the last leap year
        if ((jump - N) < 6) {
            N = N - jump + ((jump + 4) / 33 * 33);
        }
        leap = (((N + 1) % 33) - 1) % 4;
        if (leap == -1) {
            leap = 4;
        }
    }

    /**
     * IranianDateToJDN: Converts a date of the Iranian calendar to the Julian
     * Day Number. It first invokes the 'IranianCalender' private method to
     * convertED2Object5111 the Iranian date to Gregorian date and then returns the Julian
     * Day Number based on the Gregorian date. The Iranian date is obtained from
     * 'irYear'(1-3100),'irMonth'(1-12) and 'irDay'(1-29/31).
     *
     * @return long (Julian Day Number)
     */
    private int IranianDateToJDN() {
        IranianCalendar();
        return (gregorianDateToJDN(gYear, 3, march) + (irMonth - 1) * 31 - irMonth / 7 * (irMonth - 7) + irDay - 1);
    }

    /**
     * IsLeap: This method determines if the Iranian (Jalali) year is leap
     * (366-day long) or is the common year (365 days), and finds the day in
     * March (Gregorian Calendar)of the first day of the Iranian year
     * ('irYear').Iranian year (irYear) ranges from (-61 to 3177).This method
     * will set the following private data members as follows: leap: Number of
     * years since the last leap year (0 to 4) Gy: Gregorian year of the
     * begining of Iranian year march: The March day of Farvardin the 1st (first
     * day of jaYear)
     */
    public boolean IsLeap(int irYear1) {
        // Iranian years starting the 33-year rule
        int Breaks[]
                = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
                1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        int jm, N, leapJ, leapG, jp, j, jump;
        gYear = irYear1 + 621;
        leapJ = -14;
        jp = Breaks[0];
        // Find the limiting years for the Iranian year 'irYear'
        j = 1;
        do {
            jm = Breaks[j];
            jump = jm - jp;
            if (irYear1 >= jm) {
                leapJ += (jump / 33 * 8 + (jump % 33) / 4);
                jp = jm;
            }
            j++;
        } while ((j < 20) && (irYear1 >= jm));
        N = irYear1 - jp;
        // Find the number of leap years from AD 621 to the begining of the current
        // Iranian year in the Iranian (Jalali) calendar
        leapJ += (N / 33 * 8 + ((N % 33) + 3) / 4);
        if (((jump % 33) == 4) && ((jump - N) == 4)) {
            leapJ++;
        }
        // And the same in the Gregorian date of Farvardin the first
        leapG = gYear / 4 - ((gYear / 100 + 1) * 3 / 4) - 150;
        march = 20 + leapJ - leapG;
        // Find how many years have passed since the last leap year
        if ((jump - N) < 6) {
            N = N - jump + ((jump + 4) / 33 * 33);
        }
        leap = (((N + 1) % 33) - 1) % 4;
        if (leap == -1) {
            leap = 4;
        }
        if (leap == 4 || leap == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * JDNToGregorian: Calculates Gregorian calendar dates from the julian day
     * number (JDN) for the period since JDN=-34839655 (i.e. the year -100100 of
     * both calendars) to some millions (10^6) years ahead of the present. The
     * algorithm is based on D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55
     * slightly modified by K.M. Borkowski, Post.Astron. 25(1987), 275-279).
     */
    private void JDNToGregorian() {
        int j = 4 * JDN + 139361631;
        j = j + (((((4 * JDN + 183187720) / 146097) * 3) / 4) * 4 - 3908);
        int i = ((j % 1461) / 4) * 5 + 308;
        gDay = (i % 153) / 5 + 1;
        gMonth = ((i / 153) % 12) + 1;
        gYear = j / 1461 - 100100 + (8 - gMonth) / 6;
    }

    /**
     * JDNToIranian: Converts the current value of 'JDN' Julian Day Number to a
     * date in the Iranian calendar. The caller should make sure that the
     * current value of 'JDN' is set correctly. This method first converts the
     * JDN to Gregorian calendar and then to Iranian calendar.
     */
    private void JDNToIranian() {
        JDNToGregorian();
        irYear = gYear - 621;
        IranianCalendar(); // This invocation will update 'leap' and 'march'
        int JDN1F = gregorianDateToJDN(gYear, 3, march);
        int k = JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                irMonth = 1 + k / 31;
                irDay = (k % 31) + 1;
                return;
            } else {
                k -= 186;
            }
        } else {
            irYear--;
            k += 179;
            if (leap == 1) {
                k++;
            }
        }
        irMonth = 7 + k / 30;
        irDay = (k % 30) + 1;
    }

    /**
     * JDNToJulian: Calculates Julian calendar dates from the julian day number
     * (JDN) for the period since JDN=-34839655 (i.e. the year -100100 of both
     * calendars) to some millions (10^6) years ahead of the present. The
     * algorithm is based on D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55
     * slightly modified by K.M. Borkowski, Post.Astron. 25(1987), 275-279).
     */
    private void JDNToJulian() {
        int j = 4 * JDN + 139361631;
        int i = ((j % 1461) / 4) * 5 + 308;
        juDay = (i % 153) / 5 + 1;
        juMonth = ((i / 153) % 12) + 1;
        juYear = j / 1461 - 100100 + (8 - juMonth) / 6;
    }

    /**
     * julianDateToJDN: Calculates the julian day number (JDN) from Julian
     * calendar dates. This integer number corresponds to the noon of the date
     * (i.e. 12 hours of Universal Time). This method was tested to be good
     * (valid) since 1 March, -100100 (of both calendars) up to a few millions
     * (10^6) years into the future. The algorithm is based on D.A.Hatcher,
     * Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by K.M. Borkowski,
     * Post.Astron. 25(1987), 275-279.
     *
     * @param year  int
     * @param month int
     * @param day   int
     * @return int
     */
    private int julianDateToJDN(int year, int month, int day) {
        return (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408;
    }

    /**
     * nextDay: Go to next julian day number (JDN) and adjusts the other dates.
     */
    public void nextDay() {
        JDN++;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * nextDay: Overload the nextDay() method to accept the number of days to go
     * ahead and adjusts the other dates accordingly.
     *
     * @param days int
     */
    public void nextDay(int days) {
        JDN += days;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    public String padd(String msg, int length) {
        String str = "";
        for (int i = 1; i <= length - msg.length(); i++) {
            str += "0";
        }
        str += msg;
        return str;
    }

    /**
     * previousDay: Go to previous julian day number (JDN) and adjusts the otehr
     * dates.
     */
    public void previousDay() {
        JDN--;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * previousDay: Overload the previousDay() method to accept the number of
     * days to go backward and adjusts the other dates accordingly.
     *
     * @param days int
     */
    public void previousDay(int days) {
        JDN -= days;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * setGregorianDate: Sets the date according to the Gregorian calendar and
     * adjusts the other dates.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    public void setGregorianDate(int year, int month, int day) {
        gYear = year;
        gMonth = month;
        gDay = day;
        JDN = gregorianDateToJDN(year, month, day);
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * setIranianDate: Sets the date according to the Iranian calendar and
     * adjusts the other dates.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    public void setIranianDate(int year, int month, int day) {
        irYear = year;
        irMonth = month;
        irDay = day;
        JDN = IranianDateToJDN();
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * setJulianDate: Sets the date according to the Julian calendar and adjusts
     * the other dates.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    public void setJulianDate(int year, int month, int day) {
        juYear = year;
        juMonth = month;
        juDay = day;
        JDN = julianDateToJDN(year, month, day);
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * toString: Overrides the default toString() method to return all dates.
     *
     * @return String
     */
    public String toString() {
        return (getWeekDayStr()
                + ", Gregorian:[" + getGregorianDate()
                + "], Julian:[" + getJulianDate()
                + "], Iranian:[" + getIranianDate() + "]");
    }

    public Calendar getYesterday() {
        Calendar today = getToday();
        int dayOfMonth = GregorianCalendar.getInstance().get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
        Calendar yesterday = getToday();
        yesterday.set(Calendar.DAY_OF_MONTH, dayOfMonth - 1);
        return yesterday;
    }

    public Calendar getToday() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
        int hour = calendar.get(Calendar.HOUR);        // 12 hour clock
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        Calendar today = new GregorianCalendar();
        today.set(Calendar.YEAR, year);
        today.set(Calendar.MONTH, month); // 11 = december
        today.set(Calendar.DAY_OF_MONTH, dayOfMonth); // christmas eve
        today.set(Calendar.HOUR, hourOfDay);
        today.set(Calendar.MINUTE, minute);
        today.set(Calendar.SECOND, second);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }
    // End of Class 'JavaSource_Calendar


}



