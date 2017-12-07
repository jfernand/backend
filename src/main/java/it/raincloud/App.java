package it.raincloud;

import org.apache.commons.cli.*;

import java.util.Scanner;

import static java.lang.System.exit;

public class App {

  private static class AppOptions {
    int width;
    int height;
    float density;
  }

  public static void main(String... args) {
    App app = new App();
    try {
      app.run(parseArgs(args));
    } catch(ParseException pe) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("backend", getOptions());
      exit(1);
    }
  }

  // Parses arguments and return an AppOptions
  private static AppOptions parseArgs(String... args) throws ParseException {
    CommandLineParser parser = new DefaultParser();
    CommandLine cl = parser.parse(getOptions(), args);
    String[] size = cl.getOptionValues("s");
    String density = cl.getOptionValue("d");
    try {
      AppOptions options = new AppOptions();
      // applies density if not determined
      options.density = density == null ? .5f : Float.parseFloat(density);
      options.width = Integer.parseInt(size[0]);
      options.height = Integer.parseInt(size[1]);
      return options;
    } catch(IllegalArgumentException e) {
      // Rethrows as parse exception, which shows the help
      throw new ParseException("Invalid Arguments");
    }
  }

  // Begins interactive session, by seeding the board and starting the interactive loop
  private void run(AppOptions options) {
    Board board = new NaiveBoard(new Renderer(), options.width, options.height);
    board.seed(options.density);
    interactiveLoop(board);
  }

  private void interactiveLoop(Board board) {
    System.out.println(board.render());
    if (board.aliveCount() == 0) {
      System.out.println("Everything died :(");
      exit(0);
    }
    System.out.println("Press enter to compute the next iteration..");
    // Waits for enter
    Scanner console = new Scanner(System.in);
    console.nextLine();
    // Recurses
    interactiveLoop(board.next());
  }

  // cli args definition
  private static Options getOptions() {
    Options options = new Options();

    Option size = Option.builder("s")
      .hasArgs()
      .type(Integer.class)
      .numberOfArgs(2)
      .valueSeparator('x')
      .required()
      .longOpt("size")
      .build();

    Option density = Option.builder("d")
      .hasArg()
      .required(false)
      .longOpt("density")
      .build();

    options.addOption(size);
    options.addOption(density);
    return options;
  }
}
