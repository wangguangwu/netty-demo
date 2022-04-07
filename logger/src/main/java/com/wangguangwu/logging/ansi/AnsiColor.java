package com.wangguangwu.logging.ansi;


/**
 * {@link AnsiElement Ansi} colors.
 *
 * @author Phillip Webb
 * @author Geoffrey Chandler
 * @since 1.3.0
 */
public enum AnsiColor implements AnsiElement {

    /**
     * DEFAULT
     */
    DEFAULT("39"),

    /**
     * BLACK
     */
    BLACK("30"),

    /**
     * RED
     */
    RED("31"),

    /**
     * GREEN
     */
    GREEN("32"),

    /**
     * YELLOW
     */
    YELLOW("33"),

    /**
     * BLUE
     */
    BLUE("34"),

    /**
     * MAGENTA
     */
    MAGENTA("35"),

    /**
     * CYAN
     */
    CYAN("36"),

    /**
     * WHITE
     */
    WHITE("37"),

    /**
     * BRIGHT_BLACK
     */
    BRIGHT_BLACK("90"),

    /**
     * BRIGHT_RED
     */
    BRIGHT_RED("91"),

    /**
     * BRIGHT_GREEN
     */
    BRIGHT_GREEN("92"),

    /**
     * BRIGHT_YELLOW
     */
    BRIGHT_YELLOW("93"),

    /**
     * BRIGHT_BLUE
     */
    BRIGHT_BLUE("94"),

    /**
     * BRIGHT_MAGENTA
     */
    BRIGHT_MAGENTA("95"),

    /**
     * BRIGHT_CYAN
     */
    BRIGHT_CYAN("96"),

    /**
     * BRIGHT_WHITE
     */
    BRIGHT_WHITE("97");

    private final String code;

    /**
     *
     * @param code code
     */
    AnsiColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
