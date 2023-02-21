package statistics;

import java.util.Map;

public interface EventsStatistic {
    void incrementEvent(String name);

    double getEventStatisticsByName(String name);

    Map<String, Double> getAllEventsStatistics();

    void printStatistics();
}
