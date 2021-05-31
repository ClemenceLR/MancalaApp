package ensi.fr.mancala.model;

public class Check {
    public static void setCellAvailable(Board b){
        for(Cell c : b.holes){
            if(c.getNbSeeds() == 0){
                c.setAvailable(false);
            }else{ //TODO vérifier si nécessaire + cas possibles (ex l'adversaire n'a plus de graines)
                c.setAvailable(true);
            }
        }
    }


    public static boolean isEndedGame(Game g){
        //TODO good luck have fun
        return false;
    }

}
