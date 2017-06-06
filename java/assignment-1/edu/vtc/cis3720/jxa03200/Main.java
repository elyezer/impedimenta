package edu.vtc.cis3720.jxa03200;

import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/** The main entry point for this application's CLI. */
@Review(name="foo", date="2017-06-06", issue=Issue.STYLE)
@Review(name="bar", date="2017-06-06", issue=Issue.STYLE, changed=true)
public class Main {
    // This docstring is manually wrapped at 72 characters. It'd be nicer to use
    // a text wrapping lib that can inspect the terminal and wrap the text at
    // runtime.
    //
    // JOpt Simple offers a help text facility. It's not used at this time
    // because doing so requires an investment in learning that I'm unable (or
    // at least unwilling) to make at this time.
    private static final String helpMsg = new String(
        "Usage: java edu.vtc.cis3720.jxa03200.Main [options] class [class ...]\n" +
        "\n" +
        "Check whether the methods belonging to the specified classes have been\n" +
        "reviewed. If all discovered methods have had sufficient reviews, return.\n" +
        "Otherwise, state which classes or methods are lacking reviews and return\n" +
        "a non-zero exit code. A review is designated by the @Review annotation.\n" +
        "A method has been 'sufficiently reviewed' if @Reviews are present for\n" +
        "each --issue.\n" +
        "\n" +
        "Options:\n" +
        "  --help\n" +
        "    Show this message and exit.\n" +
        "  --issue\n" +
        "    An issue that reviews should cumulatively check for. Repeatable.\n" +
        "    Default: " + Issue.defaults + "\n" +
        "    Available: " + Arrays.toString(Issue.values()) + "\n" +
        "\n" +
        "Arguments:\n" +
        "  class\n" +
        "    A class name in dotted notation. For example: com.example.Main\n"
    );

    /** Parse CLI arguments, execute business logic, and format output. */
    public static void main(String[] args) {
        // Parse options.
        OptionParser parser = new OptionParser();
        OptionSpec<Void> help = parser.accepts("help");
        OptionSpec<Issue> issue = parser
            .accepts("issue")
            .withRequiredArg()
            .ofType(Issue.class)
            .defaultsTo(Issue.defaults.toArray(new Issue[Issue.defaults.size()]));
        OptionSet options = parser.parse(args);

        // Interpret options.
        if (args.length == 0) {
            System.err.println("ERROR: Too few arguments.");
            System.err.println(helpMsg);
            System.exit(1);
        } else if (options.has(help)) {
            System.out.println(helpMsg);
        } else {
            System.out.println(options.valuesOf(issue));
            System.out.println(options.nonOptionArguments());
        }
    }
}
