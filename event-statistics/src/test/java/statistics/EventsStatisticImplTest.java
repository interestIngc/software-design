package statistics;

import clock.FakeClock;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventsStatisticImplTest {
    private static final String EVENT_1 = "event1";
    private static final String EVENT_2 = "event2";
    private static final Instant NOW = Instant.parse("2022-12-01T11:00:00.00Z");
    FakeClock clock;

    @Before
    public void setUp() {
        clock = new FakeClock(NOW);
    }

    @Test
    public void getEventStatisticsByName_oneIncrement_outputsCorrectStat() {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(30, ChronoUnit.MINUTES));

        double stat = eventsStatistic.getEventStatisticsByName(EVENT_1);

        double expected = (double) 1 / 60;
        assertThat(stat).isEqualTo(expected);
    }

    @Test
    public void getEventStatisticsByName_TimedOutIncrement_outputsZero() {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(70, ChronoUnit.MINUTES));

        double stat = eventsStatistic.getEventStatisticsByName(EVENT_1);

        assertThat(stat).isEqualTo(0);
    }

    @Test
    public void getEventStatisticsByName_multipleIncrements_outputsCorrectStat() {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(30, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(40, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(50, ChronoUnit.MINUTES));

        double stat = eventsStatistic.getEventStatisticsByName(EVENT_1);

        double expected = (double) 3 / 60;
        assertThat(stat).isEqualTo(expected);
    }

    @Test
    public void getEventStatisticsByName_multipleIncrements_filtersOutTimedOutIncrements() {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(30, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(40, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(80, ChronoUnit.MINUTES));

        double stat = eventsStatistic.getEventStatisticsByName(EVENT_1);

        double expected = (double) 2 / 60;
        assertThat(stat).isEqualTo(expected);
    }

    @Test
    public void getAllEventsStatistics_multipleEvents_outputsCorrectStat() {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(10, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_2);
        clock.setTimeMs(NOW.plus(11, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_2);
        clock.setTimeMs(NOW.plus(59, ChronoUnit.MINUTES));

        Map<String, Double> stat = eventsStatistic.getAllEventsStatistics();

        double expected1 = (double) 1 / 60;
        double expected2 = (double) 2 / 60;

        assertThat(stat).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        EVENT_1, expected1,
                        EVENT_2, expected2
                )
        );
    }

    @Test
    public void getAllEventsStatistics_multipleEvents_filtersOutTimedOutIncrements() {
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incrementEvent(EVENT_1);
        clock.setTimeMs(NOW.plus(10, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_2);
        clock.setTimeMs(NOW.plus(11, ChronoUnit.MINUTES));
        eventsStatistic.incrementEvent(EVENT_2);
        clock.setTimeMs(NOW.plus(70, ChronoUnit.MINUTES));

        Map<String, Double> stat = eventsStatistic.getAllEventsStatistics();

        double expected1 = 0;
        double expected2 = (double) 1 / 60;

        assertThat(stat).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        EVENT_1, expected1,
                        EVENT_2, expected2
                )
        );
    }
}
