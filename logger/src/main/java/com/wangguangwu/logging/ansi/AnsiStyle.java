package com.wangguangwu.logging.ansi;


/**
 * {@link AnsiElement Ansi} styles.
 *
 * @author Phillip Webb
 * @since 1.3.0
 */
@SuppressWarnings("unused")
public enum AnsiStyle implements AnsiElement {

    /**
     * NORMAL
     */
    NORMAL("0"),

    /**
     * BOLD
     */
    BOLD("1"),

    /**
     * FAINT
     */
    FAINT("2"),

    /**
     * ITALIC
     */
    ITALIC("3"),

    /**
     * UNDERLINE
     */
    UNDERLINE("4");

    /**
     * code
     */
    private final String code;

    /**
     * construction method
     *
     * @param code code
     */
    AnsiStyle(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
