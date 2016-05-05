package Evaluator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RecommendationEvaluator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new RecommendationEvaluator().test2();
	/*	DataModel model = null;
		try {
			model = new FileDataModel(new File("CourseData.csv"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserSimilarity similarity = null;
		try {
			similarity = new PearsonCorrelationSimilarity(model);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.75,
				similarity, model);
		 UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(
				model, neighborhood, similarity);
		List<RecommendedItem> recommendations = null;
		try {
			recommendations = recommender.recommend(9980698,
					8);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}
		
		RecommenderBuilder builder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model)
					throws TasteException {
				UserSimilarity similarity = new PearsonCorrelationSimilarity(
						model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
						similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood,
						similarity);
			}
		};

		RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();

		
		 IRStatistics stats = null;
		try {
			stats = evaluator.evaluate(builder, null, model, null,
			  5, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, .85);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  // on average, about P % of recommendations are good
		  System.out.println("PRECISION: On Avarege, about " +
		  stats.getPrecision()*100.0 + "% of recommendations are good" );
		  
		  // %R of good recommenations are amont those recommended
		  System.out.println("RECALL: " + stats.getRecall()*100.0 +
		  "% of good recommenations are among those recommended");
		 
*/
	}
	
	public void test()
	{
		
		// TODO Auto-generated method stub
		DataModel model = null;
		try {
			model = new FileDataModel(new File("CourseData.csv"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RecommenderBuilder builder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model)
					throws TasteException {
				UserSimilarity similarity = new PearsonCorrelationSimilarity(
						model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood,
						similarity);
			}
		};
		

		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		try {
			double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
			System.out.println(result);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* IRStatistics stats = null;
			try {
				stats = evaluator.evaluate(builder, null, model, null,
				  5, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, .85);
			} catch (TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  */
			  // on average, about P % of recommendations are good
			/*  System.out.println("PRECISION: On Avarege, about " +
			  result.getPrecision()*100.0 + "% of recommendations are good" );
			  
			  // %R of good recommenations are amont those recommended
			  System.out.println("RECALL: " + result.getRecall()*100.0 +
			  "% of good recommenations are among those recommended");*/
			 
		
	}
	
	public void test2()
	{
		DataModel model = null;
 		try {
 			model = new FileDataModel(new File("CourseData.csv"));
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
 		
 		
 		RecommenderBuilder builder = new RecommenderBuilder() {
 			  public Recommender buildRecommender(DataModel model) throws TasteException {
 				  UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
 				  UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
 					return new GenericUserBasedRecommender(model, neighborhood, similarity);
 			  }
 			};
 		
 			
 			RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
 		    IRStatistics stats = null;
			try {
				stats = evaluator.evaluate(builder, null, 	model, 	null, 	1,  GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1);
			} catch (TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
 			// on average, about P % of recommendations are good
 			System.out.println("PRECISION: On Avarege, about " + stats.getPrecision()*100.0 + "% of recommendations are good" );
 			
 			// %R of good recommenations are amont those recommended
 			System.out.println("RECALL: " + stats.getRecall()*100.0 + "% of good recommenations are among those recommended");
 
 			
 			
 	
	}
}
