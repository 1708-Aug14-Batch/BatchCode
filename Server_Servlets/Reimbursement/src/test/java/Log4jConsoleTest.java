// https://wiki.base22.com/download/attachments/8061248/Log4jConsoleTest.java?version=1&modificationDate=1221150471000&api=v2

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This is a Java console app that you can execute within your IDE for visual
 * confirmation that the log4j system is appending to console and/or file as
 * specified in log4j config (i.e. resources/log4j.xml). It is also useful for a
 * quick visual test when you change the pattern of log statements via
 * ConversionPattern in log4j.xml.
 * 
 * <p>
 * Right-click Log4jConsoleTest.java and either select Run As > Java Application
 * or press ALT+SHIFT+X,J.
 * </p>
 * 
 * <p>
 * As a result, you should typically see test messages from all log levels
 * (TRACE, DEBUG, INFO, WARN, ERROR, FATAL) both on the console view of your IDE
 * and in any log files created by file appenders defined in log4j.xml.
 * </p>
 * 
 * @author Cody Burleson
 */
public class Log4jConsoleTest {
	
	final static Logger logger = Logger.getLogger(Log4jConsoleTest.class);
	
	static {
		logger.setLevel(Level.ALL);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering main(String[])");
			logger.debug("args: " + args);
		}
		Log4jConsoleTest console = new Log4jConsoleTest();
		console.execute();
		if (logger.isDebugEnabled()) {
			logger.debug("exiting main()");
		}
	}

	public Log4jConsoleTest() {
	}

	public void execute() {
		if (logger.isDebugEnabled()) {
			logger.debug("entering execute()");
		}
		logger.fatal("nothing");
		logger.info("nothing more");
		
		if (logger.isTraceEnabled()) {
			logger.trace("Test: TRACE level message.");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Test: DEBUG level message.");
		}
		if (logger.isInfoEnabled()) {
			logger.info("Test: INFO level message.");
		}
		if (logger.isEnabledFor(Level.WARN)) {
			logger.warn("Test: WARN level message.");
		}
		if (logger.isEnabledFor(Level.ERROR)) {
			logger.error("Test: ERROR level message.");
		}
		if (logger.isEnabledFor(Level.FATAL)) {
			logger.fatal("Test: FATAL level message.");
		}
		
		System.out.println(logger.getLevel());
		logger.info("Logging stuff should go here");
		if (logger.isDebugEnabled()) {
			logger.debug("exiting execute()");
		}
	}

}