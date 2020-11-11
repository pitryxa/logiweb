package logiweb.service.calculating;

import logiweb.dto.TruckDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

@Component
public class DriversCalc {
    static final int MINUTES_IN_HOUR = 60;
    static final int SECONDS_IN_MINUTE = 60;
    static final int WORK_HOURS_PER_DAY_FOR_ONE_DRIVER = 8;
    static final int HOURS_IN_DAY = 24;
    static final int HOURS_FOR_LOAD_UNLOAD = 4;
    static final int AVERAGE_SPEED = 60;

    public double getWorkHoursForEveryDriver(TruckDto truck, Route route) {
        final int shiftSize = truck.getShiftSize();
        double timeExecOrderInHours = getExecTimeForOrder(route);
        return getWorkHours(shiftSize, timeExecOrderInHours);
    }

    private double getExecTimeForOrder(Route route) {
        final int NUMBER_WAYPOINTS_WITH_CARGO = route.getWaypoints().size() - 1;

        double currentTime = route.getDistance().doubleValue() / AVERAGE_SPEED;
        final double[] fullTime = {currentTime};

        return route.getWaypoints()
                    .stream()
                    .limit(NUMBER_WAYPOINTS_WITH_CARGO)
                    .map(w -> w.getCargoes()
                               .entrySet()
                               .stream()
                               .map(o -> fullTime[0] = addTimeForLoadUnload(fullTime[0]))
                               .reduce((first, last) -> last)
                               .orElse(currentTime))
                    .reduce((first, last) -> last)
                    .orElse(currentTime);
    }

    private Double addTimeForLoadUnload(double fullTime) {
        return fullTime + HOURS_FOR_LOAD_UNLOAD;
    }

    private double getWorkHours(int shiftSize, double timeExecOrderInHours) {
        if (shiftSize < 3) {
            timeExecOrderInHours = getTimeExecOrderInHoursDependShiftSize(shiftSize, timeExecOrderInHours);
        }

        timeExecOrderInHours = getTimeExecOrderInCurrentMonth(timeExecOrderInHours);

        double workHoursInTimeExecOrder = timeExecOrderInHours;
        if (shiftSize < 3) {
            workHoursInTimeExecOrder = getWorkHoursFromTimeExecOrder(timeExecOrderInHours, shiftSize);
        }
        return workHoursInTimeExecOrder / shiftSize;
    }

    private double getTimeExecOrderInHoursDependShiftSize(int shiftSize, double timeExecOrderInHours) {
        int workHoursPerDay = WORK_HOURS_PER_DAY_FOR_ONE_DRIVER * shiftSize;

        int timeExecOrderInDays = (int) (timeExecOrderInHours / workHoursPerDay);
        double remainderHours = timeExecOrderInHours % workHoursPerDay;

        if (remainderHours >= workHoursPerDay) {
            timeExecOrderInDays++;
            remainderHours -= workHoursPerDay;
        }

        return timeExecOrderInDays * HOURS_IN_DAY + remainderHours;
    }

    private double getTimeExecOrderInCurrentMonth(double timeExecOrderInHours) {
        double timeExecOrderInMinutes = timeExecOrderInHours * MINUTES_IN_HOUR;

        LocalDateTime now = LocalDateTime.now();
        //now = LocalDateTime.of(2020, Month.OCTOBER, 30, 10, 0);

        Month monthBeforeOrder = now.getMonth();
        Month monthAfterOrder = now.plusMinutes((long) (timeExecOrderInMinutes)).getMonth();

        if (!monthBeforeOrder.equals(monthAfterOrder)) {
            LocalDateTime firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth()).with(LocalTime.MIN);
            timeExecOrderInMinutes =
                    (double) Duration.between(now, firstDayOfNextMonth).getSeconds() / SECONDS_IN_MINUTE;
            timeExecOrderInHours = timeExecOrderInMinutes / MINUTES_IN_HOUR;
        }

        return timeExecOrderInHours;
    }

    public double getWorkTime(LocalDateTime timeLastChange) {
        LocalDateTime now = LocalDateTime.now();
        return (double) Duration.between(timeLastChange, now).getSeconds() / (SECONDS_IN_MINUTE * MINUTES_IN_HOUR);

    }

    private double getWorkHoursFromTimeExecOrder(double timeExecOrderInHours, int shiftSize) {
        int workDays = (int) (timeExecOrderInHours / HOURS_IN_DAY);
        double remainderHours = timeExecOrderInHours % HOURS_IN_DAY;
        int workHoursPerDay = WORK_HOURS_PER_DAY_FOR_ONE_DRIVER * shiftSize;

        if (remainderHours >= workHoursPerDay) {
            workDays++;
            remainderHours -= workHoursPerDay;
        }

        return workDays * workHoursPerDay + remainderHours;
    }
}
