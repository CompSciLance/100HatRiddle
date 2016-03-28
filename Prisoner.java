
public class Prisoner {
	public static Color protocol;
	public static Color secondary;
	private Color hat;
	private Prisoner behind = null;
	private Prisoner ahead = null;
	private boolean alive = true;
	private boolean initialCallMade = false;
	private boolean initialCallIsEven = false;
	private boolean currentProtocolIsEven = true;

	public Prisoner(Prisoner prev) {
		this();
		behind = prev;
	}

	public Prisoner() {
		if((Math.floor(Math.random()*100))%2==0)
			hat = Color.Blue;
		else
			hat = Color.Red;
	}

	public void setAhead(Prisoner next) {
		ahead = next;
	}

	public Prisoner getPrevious() {
		return behind;
	}

	public Prisoner getNext() {
		return ahead;
	}

	public Color getHat() {
		return hat;
	}

	public boolean isAlive() {
		return alive;
	}

	public void hearGuess(Color color) {
		if(!initialCallMade) {
			initialCallIsEven = (color == protocol);
			initialCallMade = true;
			return;
		}
		if (color == protocol)
			currentProtocolIsEven = !currentProtocolIsEven;
	}

	public Color guessHat() {
		//look forward
		int protocolHats = observeForwardHatColors();

		if(!initialCallMade) {
			//return odd/even color
			if(protocolHats%2==0) {
				hat = protocol;
				//debug print
				//System.out.println("hat: " + hat + "  call: " + protocol + "  protocolHats: "+protocolHats);
				return protocol;
			}
			else {
				hat = secondary;
				//debug print line
				//System.out.println("hat: " + hat + "  call: " + secondary + "  protocolHats: "+protocolHats);
				return secondary;
			}
		}

		int totalHatsAhead = countForwardHats();

		Color retVal;
		if(totalHatsAhead > 0) {

			if(initialCallIsEven & currentProtocolIsEven & (protocolHats%2==0))
				retVal = secondary;
			else if (initialCallIsEven & !currentProtocolIsEven & (protocolHats%2!=0))
				retVal = secondary;
			else if (!initialCallIsEven & currentProtocolIsEven & (protocolHats%2!=0))
				retVal = secondary;
			else if (!initialCallIsEven & !currentProtocolIsEven & (protocolHats%2==0))
				retVal = secondary;
			else
				retVal = protocol;
		}
		else {
			if(initialCallIsEven == currentProtocolIsEven)
				retVal = secondary;
			else
				retVal = protocol;
		}
		//Debug print line
		//System.out.print("hat: " + hat + "  guess: " + retVal+ "  initial call: "+initialCallIsEven+"  current: "+
		//		currentProtocolIsEven + "  forwardHats: " + totalHatsAhead + "  protocolHats: "+protocolHats);

		return retVal;
	}

	public void execute() {
		alive = false;
	}

	private int observeForwardHatColors() {
		if(ahead == null)
			return 0;
		int numProtocolHats = 0;
		Prisoner next = ahead;
		while(next != null) {
			if(next.getHat()==Prisoner.protocol)
				numProtocolHats++;
			next = next.getNext();
		}
		return numProtocolHats;
	}

	private int countForwardHats() {
		if(ahead == null)
			return 0;
		int numHats = 0;
		Prisoner next = ahead;
		while(next != null) {
			numHats++;
			next = next.getNext();
		}
		return numHats;
	}

}
