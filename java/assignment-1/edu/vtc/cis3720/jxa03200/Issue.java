package edu.vtc.cis3720.jxa03200;

import java.util.Arrays;
import java.util.List;

/** Types of issues that can be checked for during code reviews. */
public enum Issue {
    /** Is the implementation of some code elegant, robust, evolvable, etc?
     *
     * This type of issue is highly subjective.
     */
    DESIGN,

    /** Does code live up to its requirements?
     *
     * Requirements can come from sources including a requirements document and
     * API documentation. This type of issue is (should be!) highly objective.
     */
    REQUIREMENTS,

    /** Does code abide by style guidelines?
     *
     * This type of issue is (should be!) highly objective.
     */
    STYLE;

    /** Which issues should reviews cumulatively check for, by default?
     *
     * Requirements aren't checked for by default because much code doesn't
     * directly target a requirement.
     */
    public static final List<Issue> defaults = Arrays.asList(
        Issue.DESIGN,
        Issue.STYLE
    );
};
