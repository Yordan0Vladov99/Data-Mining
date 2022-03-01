package main;

import java.util.Arrays;

public class Politician {
    private enum Party{
        republican,
        democrat,
    }
    private Party party;
    private char[] votes;

    public Politician(String[] politician){
        if(politician[0].equals("republican")){
            party = Party.republican;
        }
        else {
            party = Party.democrat;
        }

        votes = new char[16];
        for (int i = 0; i < 16; i++) {
            votes[i] = politician[i+1].charAt(0);
        }
    }

    public Politician(Politician politician){
        this.party = politician.party;
        this.votes = new char[16];
        System.arraycopy(politician.votes,0,this.votes,0,politician.votes.length);
    }
    public String getParty(){
        if(party == Party.republican){
            return "republican";
        }
        return "democrat";
    }
    public char getVote(int index){
        if(index>=0 && index<=15){
            return votes[index];
        }
        return '0';
    }

    public int getLength(){
        return this.votes.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(party == Party.republican){
            stringBuilder.append("republican,");
        }
        else {
            stringBuilder.append("democrat,");
        }
        for (int i = 0; i < votes.length; i++) {
            if(votes[i]=='y'){
                stringBuilder.append('y');
            }
            else if (votes[i] == 'n'){
                stringBuilder.append('n');
            }
            else {
                stringBuilder.append('?');
            }

            if(i != votes.length-1){
                stringBuilder.append(',');
            }
        }
        return stringBuilder.toString();
    }
}
