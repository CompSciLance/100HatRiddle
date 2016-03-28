import java.util.ArrayList;

public class ExecutionLine {

	private ArrayList<Prisoner> prisoners;

	public ExecutionLine(int numPrisoners) {
		prisoners = new ArrayList<Prisoner>();
		Prisoner prisoner = new Prisoner();
		prisoners.add(prisoner);
		for (int i=1;i<numPrisoners;i++) {
			Prisoner newPrisoner = new Prisoner(prisoner);
			prisoner.setAhead(newPrisoner);
			prisoners.add(newPrisoner);
			prisoner = newPrisoner;
		}
		Prisoner.protocol = Color.Blue;
		Prisoner.secondary = Color.Red;
	}

	public void execute() {
		boolean noneExecuted = true;
		System.out.println("The executioner has started.");
		for (int i=0;i<prisoners.size();i++) {
			Prisoner prisoner = prisoners.get(i);
			Color color = prisoner.guessHat();
			if(color != prisoner.getHat()) {
				prisoner.execute();
				System.out.println("Prisoner "+(i+1)+", with a "+prisoners.get(i).getHat()+" hat, was executed.");
				noneExecuted=false;
			}
			//NLogN for now
			for(int j=i;j<prisoners.size();j++) {
				prisoners.get(j).hearGuess(color);
			}
		}
		if(noneExecuted)
			System.out.println("No prisoners were executed!");
		System.out.println("The executioner is finished.");
	}

	public void printActual() {
		int ctr = 0;
		for (int i=0;i<prisoners.size();i++) {
			if (ctr==9) {
				System.out.println();
				ctr=0;
			}
			System.out.print(prisoners.get(i).getHat()+"  ");
			ctr++;
		}
	}

}
