package org.blocktrust.config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicLogger {
  private static final Logger logger = Logger.getLogger(BasicLogger.class.getName());

  public static void warn(String message) {
    logger.log(Level.WARNING, message);
  }
}
