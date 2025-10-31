package ttv.poltoraha.pivka.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.Counter;

@Component
public class CustomMetrics {
    private final Counter counter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("custom_metrics");
    }

    public void incrementCounter(){
        counter.increment();
    }

    /*
    Как мне сказали великие нейронные сети, @Timed содержит в себе счетчик вызова функции, помимо времени,
    поэтому отдельно вести его не нужно.

    Собственно примерно так и получилось:

    "name": "author.create.time",
    "description": "Время создания автора",
    "baseUnit": "seconds",
    "measurements": [
        {
            "statistic": "COUNT",
            "value": 6.0
        },
        {
            "statistic": "TOTAL_TIME",
            "value": 0.0301224
        },
        {
            "statistic": "MAX",
            "value": 0.0    <- вот здесь правда хз почему ноль, иногда есть время, а иногда оно просто пропадает. Хз.
        }
    ],

    "name": "author.delete.time",
    "description": "Время удаления автора",
    "baseUnit": "seconds",
    "measurements": [
        {
            "statistic": "COUNT",
            "value": 2.0
        },
        {
            "statistic": "TOTAL_TIME",
            "value": 0.0125332
        },
        {
            "statistic": "MAX",
            "value": 0.0110026
        }
    ],
     */
}
