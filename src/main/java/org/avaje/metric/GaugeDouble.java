package org.avaje.metric;

/**
 * A Gauge returning a double value providing the 'source' for a {@link GaugeDoubleMetric}.
 * <p>
 * A Gauge typically doesn't represent an "Event" but the current value of a resource like threads,
 * memory etc.
 * 
 * @see GaugeDoubleMetric
 */
public interface GaugeDouble {

  /**
   * Return the current value.
   */
  public double getValue();

}
