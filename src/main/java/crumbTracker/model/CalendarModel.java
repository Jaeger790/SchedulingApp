package crumbTracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class CalendarModel {

    public CalendarModel() {
    }

    private static LocalTime localTime = LocalTime.now();
    private static LocalDate localDate = LocalDate.now();


    private static LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    private static LocalDate firstDayOfYear = localDate.with(TemporalAdjusters.firstDayOfYear());
    private static ZoneId localZoneId = ZoneId.systemDefault();
    private static LocalDate firstDayMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
    private static LocalTime monthTimeBegin = Appointment.getOpenBusinessTime().toLocalTime();
    private static LocalDate lastDayMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
    private static LocalTime monthTimeEnd = Appointment.getCloseBusinessTime().toLocalTime();
    private static LocalDateTime monthBegin = LocalDateTime.of(firstDayMonth, monthTimeBegin);
    private static LocalDateTime monthEnd = LocalDateTime.of(lastDayMonth, monthTimeEnd);
    private static DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static LocalDate weekEndDate = LocalDate.now().plusWeeks(1);
    private static LocalDateTime weekEnd = LocalDateTime.of(weekEndDate, LocalTime.MAX);


    /**
     * Getters for time units used in appointment scheduling.
     *
     * @return
     */

    public static DateTimeFormatter getDateDTF() {
        return dateDTF;
    }

    public static ZoneId getLocalZoneId() {
        return localZoneId;
    }

    public static LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public static LocalDate getFirstDayMonth() {
        return firstDayMonth;
    }

    public static LocalTime getMonthTimeBegin() {
        return monthTimeBegin;
    }

    public static LocalDate getLastDayMonth() {
        return lastDayMonth;
    }

    public static LocalTime getMonthTimeEnd() {
        return monthTimeEnd;
    }

    public static LocalDateTime getMonthBegin() {
        return monthBegin;
    }

    public static LocalDateTime getMonthEnd() {
        return monthEnd;
    }

    public static LocalDate getWeekEndDate() {
        return weekEndDate;
    }

    public static LocalDateTime getWeekEnd() {
        return weekEnd;
    }
}
