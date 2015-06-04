package org.avaje.metric;

/**
 * Statistics provided by the {@link CounterMetric}.
 * 
 * @see CounterMetric#getStatistics(boolean)
 */
public interface CounterStatistics {

  /**
   * Return the time the counter started statistics collection.
   */
  long getStartTime();

  /**
   * Return the count of values collected.
   */
  long getCount();
}