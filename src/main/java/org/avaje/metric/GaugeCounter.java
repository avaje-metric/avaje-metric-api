package org.avaje.metric;

/**
 * A Gauge returns a single value (long).
 * <p>
 * A Gauge typically doesn't represent an "Event" but the current value of a
 * resource like threads, memory etc.
 * </p>
 */
public interface GaugeCounter {

  /**
   * Return the current value.
   */
  public long getValue();

}
