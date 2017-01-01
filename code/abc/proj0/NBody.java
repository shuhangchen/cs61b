/**
 * Created by mark on 6/18/16.
 */
public class NBody {




    public static double readRadius( String dataFilePath) {
        In readData = new In(dataFilePath);
        readData.readInt();
        return readData.readDouble();
    }

    public static Planet[] readPlanets( String dataFilePath) {
        In readData = new In(dataFilePath);
        int numPlanets = readData.readInt();
        double radius = readData.readDouble();
        Planet[] planets = new Planet[numPlanets];
        for (int i=0; i < numPlanets; i += 1) {
            planets[i] = new Planet(readData.readDouble(),readData.readDouble(),readData.readDouble(),readData.readDouble(),readData.readDouble(),"./images/" + readData.readString() );

        }
        return planets;
    }

    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filePath = args[2];
        Planet[] planets = readPlanets(filePath);
        double radius = readRadius(filePath);
        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];
        StdAudio.play("./audio/2001.mid");
        for (double time = 0 ; time < T ; time += dt) {
            for (int i=0; i < planets.length; i+= 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByX(planets);
            }
            for (int i=0; i < planets.length; i+= 1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.setScale(-radius, radius);
		/* C    lears the drawing window. */
            StdDraw.clear();

            StdDraw.picture(0, 0, "./images/starfield.jpg");

            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show( 10 );
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
