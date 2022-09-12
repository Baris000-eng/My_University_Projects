import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Math;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	public static ArrayList<Tribe> tribes;
	public static PriorityQueue<Tribe> prioTribes;
	public static PriorityQueue<Tribe> populoTribes;
	public static int delegatesToWin = 0;
	public static int populationVotesNeeded = 0;
	public static int total = 0;
	public static String test;
	public static int delegateCount=0;
	public static int populoDelegateCount=0;
	public static int populoPopuloCount=0;
	public static int delegatesRemaining=0;
  public static int currentDelegates = 0;
 
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter test name: ");
		test = scanner.nextLine();
		scanner.close();
		double start = System.nanoTime();
		createLists();
		System.out.println("Total: " + total);
		delegatesToWin = minToWin(delegateCount);
		tribes = new ArrayList<Tribe>();

		while (delegatesToWin > currentDelegates) {
			Tribe currentPrioTribe = prioTribes.poll();
			//System.out.println(currentPrioTribe.getName() + " " + currentPrioTribe.getElectoralVotes() + " " + currentPrioTribe.getPopulationVotes() + " " + currentPrioTribe.getElectorValue());
			delegatesRemaining= delegatesToWin - currentDelegates;

			if( delegatesRemaining < currentPrioTribe.getElectoralVotes()){
				Tribe currentPopuloTribe = currentPrioTribe;
        ArrayList<Tribe> tmp = new ArrayList<Tribe>();
				while (delegatesRemaining > populoDelegateCount){
					currentPopuloTribe = populoTribes.poll();
					populoDelegateCount += currentPopuloTribe.getElectoralVotes();
					populoPopuloCount += currentPopuloTribe.getPopVotesNeeded();
          tmp.add(currentPopuloTribe);
				}

				if (populoPopuloCount < currentPrioTribe.getPopVotesNeeded()){
					for(int i = 0; i < tmp.size(); i++){
					  currentDelegates += tmp.get(i).getElectoralVotes();
            populationVotesNeeded += tmp.get(i).getPopVotesNeeded();
					  prioTribes.remove(tmp.get(i));
            tribes.add(tmp.get(i));
            //System.out.println("*From Population List* \n" + tmp.get(i).getName() +" " + tmp.get(i).getElectoralVotes() + " " + tmp.get(i).getPopulationVotes() + " " + tmp.get(i).getElectorValue());
          }
				} else {
					currentDelegates += currentPrioTribe.getElectoralVotes();
					tribes.add(currentPrioTribe);
					populationVotesNeeded += currentPrioTribe.getPopVotesNeeded();
					populoTribes.remove(currentPrioTribe);
				}
			} else {
				currentDelegates += currentPrioTribe.getElectoralVotes();
				tribes.add(currentPrioTribe);
				populationVotesNeeded += currentPrioTribe.getPopVotesNeeded();
				populoTribes.remove(currentPrioTribe);
			}
		}
		printStats();
		double end = System.nanoTime();
		double execution = end - start;
		double power= Math.pow(10.0,9);
		execution=execution/power;
		System.out.println("The Execution Time: " + execution + " seconds");
    System.out.println("-----------------------------------------------------------------");
		printSelected();
	}

	public static int minToWin(int voters) {
		if (voters % 2 == 0) {
			return (voters / 2) + 1;
		} else {
			return (voters + 1) / 2;
		}
	}
  
	public static ArrayList <Tribe> getTribes() {
		ArrayList <Tribe> tribes = new ArrayList <Tribe> ();
		try (BufferedReader br = new BufferedReader(new FileReader(test))) {
			String line;
			line = br.readLine();
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(";");
				Tribe tribe = new Tribe(values[0], Integer.parseInt(values[2]), Integer.parseInt(values[3]));
				tribes.add(tribe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tribes;
	}

	public static void createLists() {
		tribes = getTribes();
    
		prioTribes = new PriorityQueue<Tribe> (tribes.size(), new ElectorComparator());
		populoTribes = new PriorityQueue<Tribe> (tribes.size(), new PopulationComparator());

		for(int i = 0; i < tribes.size(); i++){
			prioTribes.add(tribes.get(i));
			populoTribes.add(tribes.get(i));
			delegateCount += tribes.get(i).getElectoralVotes();
			total += tribes.get(i).getPopulationVotes();
		}
		System.out.println("Tribe count:" + tribes.size());
	}

	public static void printStats(){
		System.out.println("Our delegates: " + currentDelegates);
		System.out.println("Total delegates: " + delegateCount);
		System.out.println("Delegates to win: " + delegatesToWin);
		System.out.println("Min population votes needed: " + populationVotesNeeded);
	}

	public static void printSelected() {
		System.out.println("Selected Tribes:");
		tribes.forEach(t -> {
			System.out.println(t.getName() + " " + t.getElectoralVotes() + " " + t.getPopulationVotes() + " " + t.getElectorValue());			
		});
	}
}