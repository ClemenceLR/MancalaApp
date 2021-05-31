package ensi.fr.mancala;

public class MancalaApp {
    public static void main(String[] args) {
        int[] t = new int[12];
        int k = 0;
        for(int j : t){
            j += k;
            k++;
        }
        for(int i=0;i<t.length; i++){
            System.out.println(t[i]);
        }

    }

}
