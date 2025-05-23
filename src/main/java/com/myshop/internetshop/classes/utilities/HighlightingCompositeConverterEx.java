package com.myshop.internetshop.classes.utilities;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

@SuppressWarnings("java:S110")
public class HighlightingCompositeConverterEx
        extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        return switch (level.toInt()) {
            case Level.ERROR_INT ->
                    ANSIConstants.BOLD + ANSIConstants.RED_FG; // same as default color scheme
            case Level.WARN_INT -> ANSIConstants.YELLOW_FG; // same as default color
            // scheme
            case Level.INFO_INT -> ANSIConstants.GREEN_FG; // use CYAN instead of BLUE
            case Level.DEBUG_INT -> ANSIConstants.CYAN_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }

}
