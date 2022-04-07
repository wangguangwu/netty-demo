package com.wangguangwu.logging.ansi;


/**
 * An ANSI encodeAble element.
 *
 * @author Phillip Webb
 * @since 1.0.0
 */
public interface AnsiElement {

    /**
     * return the ANSI escape code.
     *
     * @return the ANSI escape code
     */
    @Override
    String toString();

}
