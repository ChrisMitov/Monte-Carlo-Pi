package pi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Pi {

	public static void main(String[] args) {
		int numThread = 0;
		long sideSquare = 0;
		boolean quit = false;

		long timeOfStart = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-s")) {
				sideSquare = new Long(args[i + 1]);
			}
			if (args[i].equals("-t")) {
				numThread = new Integer(args[i + 1]);
			}
			if (args[i].equals("-q")) {
				quit = true;
			}
		}
		if (sideSquare <= 0 || numThread <= 0 || args.length < 4 || args.length > 5) {
			System.out.println("ERROR: Args are not correct!!!");
			return;
		} else {
			ExecutorService executor = Executors.newFixedThreadPool(numThread);
			CompletionService<Long> pool = new ExecutorCompletionService<Long>(executor);
			List<Future<Long>> futures = new ArrayList<Future<Long>>(numThread);
			long numPoints = sideSquare * sideSquare;
			long pointsOfThread = numPoints / numThread;
			for (int t = 0; t < numThread; t++) {
				if (t == numThread - 1) {
					pointsOfThread = numPoints - pointsOfThread * (numThread - 1);
				}
				futures.add(pool.submit(new MyThread(t, sideSquare, pointsOfThread, quit)));
			}

			long pointsInCircle = 0;
			for (Future<Long> future : futures) {
				try {
					pointsInCircle += future.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			double pi = 4 * ((double) pointsInCircle / numPoints);
			long timeOfEnd = Calendar.getInstance().getTimeInMillis();
			if (!quit) {
				System.out.println("Points in square: " + numPoints);
				System.out.println("Points in circle: " + pointsInCircle);
			}
			System.out
					.println("Total execution time for current run (millis): " + (timeOfEnd - timeOfStart) + " millis");
			System.out.println("Calculate Pi: " + pi);
			executor.shutdown();
		}
	}
}