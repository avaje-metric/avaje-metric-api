package org.avaje.metric;

/**
 * Collects and provides statics for a timed event.
 * <p>
 * Typically collects execution statistics for a method. TimedMetric has the ability to keep
 * separate statistics for success and error execution. It is generally useful to know which methods
 * are invoking errors and differences in execution time of errors versus successful execution.
 * <p>
 * <em>Example:</em> Using {@link TimedMetric#addEventSince(boolean, long)}.
 * 
 * <pre>
 * <code>
 *  
 *  // Declare the metric (typically as a static field)
 *  
 *  static final TimedMetric timedUserLogin = MetricManager.getTimedMetric(MyService.class, "performLogin");
 *  ...
 *  
 *  public void performLogin() {
 *    
 *    long startNanos = System.nanoTime();
 *    
 *    try {
 *      ...
 *    
 *    
 *    } finally {
 *      // Add the event to the success statistics
 *      timedUserLogin.addEventSince(true, startNanos);
 *    }
 *  }
 *  
 * </code>
 * </pre>
 * <p>
 * Instead of programmatically adding TimedMetric code these can be added using enhancement. Classes
 * that are annotated with <code>@Timed</code> on the class or method can have the appropriate code
 * added via enhancement. Also note that the enhancement in addition can be applied to classes
 * annotated with <code>@Singleton</code>, JAX-RS annotations like <code>@Path</code> and Spring
 * stereotype annotations like <code>@Component</code>, <code>@Service</code> etc.
 * <p>
 * The enhancement will add instructions such that for method execution that throw exceptions the
 * timing is put into the error statistics. This can provide quick insight into where errors are
 * occurring, how frequently and execution time of errors.
 * 
 * <p>
 * <em>Example:</em> <code>@Timed</code> annotation.
 * 
 * <pre>
 * <code>
 *  ...
 *  {@literal @}Timed
 *  public void performLogin() {
 *    ...
 *  }
 *  
 * </code>
 * </pre>
 * 
 * <p>
 * <em>Example:</em> Alternative using TimedMetric. This will have a slight extra cost compared with
 * {@link TimedMetric#addEventSince(boolean, long)} as TimedEvent is instantiated and must be later
 * garbage collected - using #{@link TimedMetric#addEventSince(boolean, long)} avoid that extra
 * object creation.
 * 
 * <pre>
 * <code>
 *  TimedMetric metric = MetricManager.getTimedMetric(MyService.class, "sayHello");
 *  ...
 *  
 *  TimedEvent timedEvent = metric.startEvent();
 *  try {
 *    ...
 *  
 *  } finally {
 *    // Add the event to the success statistics
 *    timedEvent.endWithSuccess();
 *  }
 *  
 * </code>
 * </pre>
 */
public interface TimedMetric extends Metric {

  /**
   * Return the success statistics.
   */
  public ValueStatistics getCollectedSuccessStatistics();

  /**
   * Return the error statistics.
   */
  public ValueStatistics getCollectedErrorStatistics();

  /**
   * Return the current success statistics choosing is the underlying statistics should be reset.
   */
  public ValueStatistics getSuccessStatistics(boolean reset);

  /**
   * Return the error success statistics choosing is the underlying statistics should be reset.
   */
  public ValueStatistics getErrorStatistics(boolean reset);

  /**
   * Start an event.
   * <p>
   * At the completion of the event {@link TimedEvent#endWithSuccess()},
   * {@link TimedEvent#endWithError()} or {@link TimedEvent#end(boolean)} are called to record the
   * event duration and success or otherwise.
   * </p>
   */
  public TimedEvent startEvent();

  /**
   * Add an event based on a startNanos (determined by {@link System#nanoTime()}).
   * <p>
   * Success and failure statistics are kept separately.
   * <p>
   * This is an alternative to using {@link #startEvent()}.
   */
  public void addEventSince(boolean success, long startNanos);

  /**
   * Add an event duration in nanoseconds noting if it was a success or failure result.
   * <p>
   * Success and failure statistics are kept separately.
   * <p>
   * This is an alternative to using {@link #startEvent()}.
   */
  public void addEventDuration(boolean success, long durationNanos);

  /**
   * Add an event duration with opCode indicating success or failure. This is intended for use by
   * enhanced code and not general use.
   */
  public void operationEnd(int opCode, long startNanos);

}