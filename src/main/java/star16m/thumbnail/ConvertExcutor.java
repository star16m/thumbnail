package star16m.thumbnail;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ConvertExcutor {
	private CommandLine cmd = null;
	public ConvertExcutor(String[] arguments) throws Exception {
		if (arguments == null || arguments.length <= 0 || arguments.length <= 1) {
			throw new IllegalArgumentException();
		}
		Options options = new Options();
		options.addOption(getOption("t", true, "target image file or directory.", true));
		options.addOption(getOption("s", true, "thumbnail suffix string.", false));
		options.addOption(getOption("p", true, "thumbnail prefix string.", false));
		options.addOption(getOption("w", true, "thumbnail width.", false));
		options.addOption(getOption("h", true, "thumbnail height.", false));
		options.addOption(getOption("o", false, "thumbnail overwrite.", false));
		CommandLineParser parser = new BasicParser();
		cmd = parser.parse(options, arguments);
	}
	public Option getOption(String opt, boolean hasArgument, String description, boolean isRequired) {
		Option option = new Option(opt, hasArgument, description);
		option.setRequired(isRequired);
		return option;
	}
	
	public void execute() throws Exception {
		String fileName = this.cmd.getOptionValue("t");
		File targetFile = new File(fileName);
		if (!targetFile.isAbsolute()) {
			targetFile = new File(System.getProperty("user.dir"), fileName);
		}
		if (!targetFile.exists()) {
			throw new IllegalArgumentException("Not found target file or directory.");
		}
		ConvertThumbnail converter = null;
		ThumbnailRename rename = null;
		int width = -1;
		int height = -1;
		boolean overwrite = false;
		if (this.cmd.hasOption("p") || this.cmd.hasOption("s")) {
			rename = new ThumbnailRename(this.cmd.getOptionValue("p"), this.cmd.getOptionValue("s"));
		}
		if (this.cmd.hasOption("w")) {
			width = Math.max(1, Math.abs(Integer.parseInt(this.cmd.getOptionValue("w"))));
		}
		if (this.cmd.hasOption("h")) {
			height = Math.max(1, Math.abs(Integer.parseInt(this.cmd.getOptionValue("h"))));
		}
		if (this.cmd.hasOption("o")) {
			overwrite = true;
		}
		converter = new ConvertThumbnail(targetFile, rename, width, height, overwrite);
        long startTime = System.nanoTime();
        System.out.println("convert start.. ");
        int convertedNum = converter.convert();
        System.out.println(" Total [" + convertedNum + "] files converted... [" + ((System.nanoTime() - startTime)/1000) + "]");
	}
	private static void usage() {
		System.err.println("usage : -t <target image file or directory> <OPTIONS>");
		System.err.println();
		System.err.println("- OPTIONS -");
		System.err.println("        -p thumbnail prefix string (default:\"\")");
		System.err.println("        -s thumbnail suffix string (default:_thumbnail)");
		System.err.println("        -w image width");
		System.err.println("        -h image height");
		System.err.println();
	}
	
    public static void main(String[] args ) throws Exception {
    	ConvertExcutor executor = null;
    	try {
    		executor = new ConvertExcutor(args);
    	} catch (IllegalArgumentException e) {
    		usage();
    		return;
    	}
    	executor.execute();
    }
}
