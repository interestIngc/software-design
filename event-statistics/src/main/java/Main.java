import clock.SystemClock;
import statistics.EventsStatistic;
import statistics.EventsStatisticImpl;

public class Main {
    public static void main(String[] args) {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(new SystemClock());

        eventsStatistic.incrementEvent("event1");
        eventsStatistic.incrementEvent("event2");
        eventsStatistic.incrementEvent("event1");

        eventsStatistic.printStatistics();
    }
}
