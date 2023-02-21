package statistics;

import clock.Clock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class EventsStatisticImpl implements EventsStatistic {
    private final Map<String, Queue<Long>> eventStatistics = new HashMap<>();
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incrementEvent(String name) {
        Instant currentTime = clock.now();

        removeOldEvents(name, currentTime);
        if (!eventStatistics.containsKey(name)) {
            eventStatistics.put(name, new ArrayDeque<>());
        }

        eventStatistics.get(name).add(currentTime.toEpochMilli());
    }

    @Override
    public double getEventStatisticsByName(String name) {
        return getEventStatisticsByName(name, clock.now());
    }

    @Override
    public Map<String, Double> getAllEventsStatistics() {
        Instant currTime = clock.now();
        Map<String, Double> allEventsStatistics = new HashMap<>();

        Set<String> events = Set.copyOf(eventStatistics.keySet());
        for (String name : events) {
            allEventsStatistics.put(name, getEventStatisticsByName(name, currTime));
        }

        return allEventsStatistics;
    }

    @Override
    public void printStatistics() {
        Map<String, Double> allEventsStatistics = getAllEventsStatistics();

        for (Map.Entry<String, Double> entry : allEventsStatistics.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void removeOldEvents(String name, Instant now) {
        Queue<Long> statistics = eventStatistics.get(name);
        if (statistics == null) {
            return;
        }

        long startTime = now.minus(1, ChronoUnit.HOURS).toEpochMilli();
        Long head;
        while ((head = statistics.peek()) != null) {
            if (head <= startTime) {
                statistics.poll();
            } else {
                break;
            }
        }

        if (head == null) {
            eventStatistics.remove(name);
        }
    }

    private double getEventStatisticsByName(String name, Instant currentTime) {
        removeOldEvents(name, currentTime);

        if (!eventStatistics.containsKey(name)) {
            return 0;
        }
        return (double) eventStatistics.get(name).size() / 60;
    }
}
