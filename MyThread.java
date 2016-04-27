package pi;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class MyThread implements Callable<Long> {
	private int nameThread;
	private long sideSquare;
	private long pointsOfThread;
	private long numPointsInCircle;
	private boolean quit;

	public MyThread(int nameThread, long sideSquare, long pointsOfThread, boolean quit) {
		this.nameThread = nameThread;
		this.sideSquare = sideSquare;
		this.pointsOfThread = pointsOfThread;
		this.quit = quit;
	}

	public Long call() {
		if (!quit) {
			System.out.println("Thread" + nameThread + " started" + "Points " + pointsOfThread + " !");
		}
		numPointsInCircle = 0;
		for (long i = 0; i < pointsOfThread; i++) {
			long x = randomNumber(0, sideSquare);
			long y = randomNumber(0, sideSquare);
			Point point = new Point(x, y);
			if (isInCircle(sideSquare / 2, point))
				numPointsInCircle++;
		}
		if (!quit) {
			System.out.println(nameThread + " is finishing! Points in circle: " + numPointsInCircle + " !");
		}
		return numPointsInCircle;
	}

	private boolean isInCircle(long radius, Point point) {
		long distance = (radius - point.getX()) * (radius - point.getX())
				+ (radius - point.getY()) * (radius - point.getY());
		if (distance > radius * radius)
			return false;
		else
			return true;
	}

	private long randomNumber(long min, long max) {
		return ThreadLocalRandom.current().nextLong(min, max + 1);
	}
}