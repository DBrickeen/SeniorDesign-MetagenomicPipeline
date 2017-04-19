package metagenomePipeline;
import pipeline.*;

public class MetagenomePipeline {
	public static void main(String[] args) throws Exception{
		DatabaseConnection db;
		Pipeline<MetagenomeJob> pipeline;
		MetagenomeStage[] stages;
		JobGetter jobGetter;
		
		//pipeline stages
		TrimmingStage trimming;
		AssemblerStage assemblerIdba, assemblerMegahit, assemblerSpades;
		//TODO: other stages
		
//		//connect to database
//		db = new DatabaseConnection();
		db = null;
		
		//connect stages and put in array
		assemblerIdba = new AssemblerStage(new MetagenomeStage[]{}, db, "IDBA");
		assemblerMegahit = new AssemblerStage(new MetagenomeStage[]{}, db, "MEGAHIT");
		assemblerSpades = new AssemblerStage(new MetagenomeStage[]{}, db, "SPADES");
		trimming = new TrimmingStage(new MetagenomeStage[]{assemblerIdba, assemblerMegahit, assemblerSpades}, db);
		
		stages = new MetagenomeStage[]{trimming, assemblerIdba, assemblerMegahit, assemblerSpades};
		
//		//put stages in pipeline
//		pipeline = new Pipeline<MetagenomeJob>(stages, trimming);
//		
//		//start job getter thread
//		jobGetter = new JobGetter(pipeline, db);
//		jobGetter.start();
//		
//		//run pipeline
//		pipeline.runPipeline();
		
		//test job
		MetagenomeJob job = new MetagenomeJob("testID");
		job.pairedEnd = false;
		job.inputForward = "/home/student/SeniorDesign-MetagenomicPipeline/TestData/simulatedReads1.fq";
		//job.inputReverse = "D:/GoogleDrive/UCF/SeniorDesign/Project/TestData/Simulated/References1/simulatedReads2.fq";
		job.trimmedSE =  "/home/student/Testing/trimmedSE.fq";
		job.trimmedForwardPaired = "/home/student/Testing/trimmedFP.fq";
		job.trimmedForwardUnpaired = "/home/student/Testing/trimmedFU.fq";
		job.trimmedReversePaired = "/home/student/Testing/trimmedRP.fq";
		job.trimmedReverseUnpaired = "/home/student/Testing/trimmedRU.fq";

		trimming.addJob(job);
		trimming.nextJob();
		trimming.process();
		
		assemblerMegahit.addJob(job);
		assemblerMegahit.nextJob();
		assemblerMegahit.process();
	}
}
