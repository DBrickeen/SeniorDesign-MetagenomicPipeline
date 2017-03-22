package metagenomePipeline;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrimmingStage extends MetagenomeStage{
	private static final String CONFIG = "/home/student/SeniorDesign-MetagenomicPipeline/pipeline_config.txt";
	
	private final String prefix = "java -jar -phred33 ";
	private String trimPath;
	private String trimSEDefault;
	private String trimPEDefault;
	private String command;
	
	public TrimmingStage(){
		super();
	}
	
	public TrimmingStage(MetagenomeStage[] nextStage, DatabaseConnection db, String assembler) throws Exception{
		super(nextStage, db);
		
		//get assembler locations and defaults from text file
		Properties config = new Properties();
		InputStream input;
		try{
			input = new FileInputStream(CONFIG);
			config.load(input);
			
			//get properties
			trimPath = config.getProperty(trimPath);
			trimSEDefault = config.getProperty(trimSEDefault);
			trimPEDefault = config.getProperty(trimPEDefault);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
			addSEFilePaths(command, currentJob.input, currentJob.trimmed);
		}
	}

	private void trim(){
		
	}
	
	@Override
	protected void process(){
		trim();
	}
	
	private static String addSEFilePaths(String original, String input, String output){
		
		original = original.replace("input.fq.gz", input);
		original = original.replace("output.fq.gz", output);
		return original;
	}
	
	private String addPEFilePaths(String original, String inputForward, String inputReverse, String outputForwardPaired, 
			String outputForwardUnpaired, String outputReversePaired, String outputReverseUnpaired){
		original = original.replace("input_foward.fq.gz", inputForward);
		original = original.replace("input_reverse.fq.gz", inputReverse);
		original = original.replace("output_forward_paired.fq.gz", outputForwardPaired);
		original = original.replace("output_forward_unpaired.fq.gz", outputForwardUnpaired);
		original = original.replace("output_reverse_paired.fq.gz", outputReversePaired);
		original = original.replace("output_reverse_unpaired.fq.gz", outputReverseUnpaired);
		return original;
	}
}
