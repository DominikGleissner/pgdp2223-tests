package pgdp.teams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;

public class DGTestOptimalTeam {

    // test01 und test02 bis test05 müssen nicht gepassed werden (testen edgecases die nicht relevant sind)
    // quelle: https://zulip.in.tum.de/#narrow/stream/1519-PGdP-W10H02

    @Test
    public void test01_playersNull(){
        try{
            Lineup best = Lineup.computeOptimalLineup(null, 1,1,1);
        }
        catch(Exception e){
            Assertions.fail();
        }
    }

    @Test
    public void test02_playersEmpty(){
        try{
            Lineup best = Lineup.computeOptimalLineup(Set.of(), 1,1,1);
        }
        catch(Exception e){
            Assertions.fail();
        }
    }

    @Test
    public void test03_negtiveAttackers(){
        try{
            Lineup best = Lineup.computeOptimalLineup(Set.of(new Penguin("a",1,2,3)), -1,1,1);
        }
        catch(Exception e){
            Assertions.fail();
        }
    }

    @Test
    public void test04_negtiveDefenders(){
        try{
            Lineup best = Lineup.computeOptimalLineup(Set.of(new Penguin("a",1,2,3)), 1,-1,1);
        }
        catch(Exception e){
            Assertions.fail();
        }
    }

    @Test
    public void test05_negtiveSupporters(){
        try{
            Lineup best = Lineup.computeOptimalLineup(Set.of(new Penguin("a",1,2,3)), 1,1,-1);
        }
        catch(Exception e){
            Assertions.fail();
        }
    }

    @Test
    public void test06_allNumbersZero(){
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(), 0,0,0);
        Assertions.assertEquals(0, lineup.getTeamScore());
        Assertions.assertEquals(0, lineup.getAttackers().size());
        Assertions.assertEquals(0, lineup.getDefenders().size());
        Assertions.assertEquals(0, lineup.getSupporters().size());
    }

    @Test
    public void test07_oneAttacker(){
        Penguin pengu1 = new Penguin("a",1,2,3);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1), 1,0,0);
        Assertions.assertEquals(1, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(0, lineup.getDefenders().size());
        Assertions.assertEquals(0, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
    }

    @Test
    public void test08_oneDefender(){
        Penguin pengu1 = new Penguin("a",1,2,3);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1), 0,1,0);
        Assertions.assertEquals(2, lineup.getTeamScore());
        Assertions.assertEquals(0, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(0, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getDefenders().contains(pengu1));
    }

    @Test
    public void test09_oneSupporter(){
        Penguin pengu1 = new Penguin("a",1,2,3);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1), 0,0,1);
        Assertions.assertEquals(3, lineup.getTeamScore());
        Assertions.assertEquals(0, lineup.getAttackers().size());
        Assertions.assertEquals(0, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getSupporters().contains(pengu1));
    }

    @Test
    public void test10_oneAttackerOneDefenderOneSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1), 1,1,1);
        Assertions.assertEquals(321, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu3));
    }

    @Test
    public void test11_oneAttackerOneDefenderOneSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin.setSynergy(pengu1, pengu2, 10);
        Penguin.setSynergy(pengu1, pengu3, 20);
        Penguin.setSynergy(pengu3, pengu2, 30);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1), 1,1,1);
        Assertions.assertEquals(381, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu3));
    }

    @Test
    public void test12_twoAttackerOneDefenderOneSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("aaaa", 1000, 2000, 3000);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1, pengu4), 2,1,1);
        Assertions.assertEquals(3211, lineup.getTeamScore());
        Assertions.assertEquals(2, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getAttackers().contains(pengu2));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu4));
    }

    @Test
    public void test13_twoAttackerOneDefenderOneSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("aaaa", 1000, 2000, 3000);
        Penguin.setSynergy(pengu1, pengu2, -1000);
        Penguin.setSynergy(pengu1, pengu3, 3000);
        Penguin.setSynergy(pengu3, pengu4, 500);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1, pengu4), 2,1,1);
        Assertions.assertEquals(8621, lineup.getTeamScore());
        Assertions.assertEquals(2, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getAttackers().contains(pengu3));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu4));
    }

    @Test
    public void test14_oneAttackerTwoDefenderOneSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("aaaa", 1000, 2000, 3000);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1, pengu4), 1,2,1);
        Assertions.assertEquals(3221, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(2, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu4));
    }

    @Test
    public void test15_oneAttackerTwoDefenderOneSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("aaaa", 1000, 2000, 3000);
        Penguin.setSynergy(pengu3, pengu2, -1000);
        Penguin.setSynergy(pengu2, pengu1, 3000);
        Penguin.setSynergy(pengu3, pengu4, 500);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1, pengu4), 1,2,1);
        Assertions.assertEquals(8622, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(2, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu3));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu4));
    }

    @Test
    public void test16_oneAttackerOneDefenderTwoSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("aaaa", 1000, 2000, 3000);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1, pengu4), 1,1,2);
        Assertions.assertEquals(3321, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(2, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu4));
    }

    @Test
    public void test17_oneAttackerOneDefenderTwoSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("aaaa", 1000, 2000, 3000);
        Penguin.setSynergy(pengu3, pengu2, -1000);
        Penguin.setSynergy(pengu2, pengu1, 20000);
        Penguin.setSynergy(pengu3, pengu4, -5000);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu3, pengu2, pengu1, pengu4), 1,1,2);
        Assertions.assertEquals(36133, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(2, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu3));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu4));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu2));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu1));
    }

    @Test
    public void test18_twoAttackerTwoDefenderTwoSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("b", 5,6,7);
        Penguin pengu5 = new Penguin("bb", 50,60,70);
        Penguin pengu6 = new Penguin("bbb", 500,600,700);
        Lineup lineup = Lineup.computeOptimalLineup
                (Set.of(pengu3, pengu2, pengu1, pengu4, pengu5, pengu6),
                        2,2,2);
        Assertions.assertEquals(1086, lineup.getTeamScore());
        Assertions.assertEquals(2, lineup.getAttackers().size());
        Assertions.assertEquals(2, lineup.getDefenders().size());
        Assertions.assertEquals(2, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getAttackers().contains(pengu4));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu5));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu6));
    }

    @Test
    public void test19_twoAttackerTwoDefenderTwoSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("b", 5,6,7);
        Penguin pengu5 = new Penguin("bb", 50,60,70);
        Penguin pengu6 = new Penguin("bbb", 500,600,700);
        Penguin.setSynergy(pengu1, pengu6, 3000);
        Penguin.setSynergy(pengu2, pengu4, 4000);
        Penguin.setSynergy(pengu3, pengu5, 5000);
        Penguin.setSynergy(pengu1, pengu2, -100);
        Penguin.setSynergy(pengu2, pengu3, -200);
        Penguin.setSynergy(pengu1, pengu5, -300);
        Penguin.setSynergy(pengu3, pengu6, -400);
        Penguin.setSynergy(pengu3, pengu4, -500);
        Penguin.setSynergy(pengu4, pengu6, -600);
        Lineup lineup = Lineup.computeOptimalLineup
                (Set.of(pengu3, pengu2, pengu1, pengu4, pengu5, pengu6),
                        2,2,2);
        Assertions.assertEquals(22887, lineup.getTeamScore());
        Assertions.assertEquals(2, lineup.getAttackers().size());
        Assertions.assertEquals(2, lineup.getDefenders().size());
        Assertions.assertEquals(2, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu2));
        Assertions.assertTrue(lineup.getAttackers().contains(pengu4));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu6));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu5));
    }

    @Test
    public void test20_tooMuchPenguins_OneAttacker(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 7,8,9);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1, pengu2), 1,0,0);
        Assertions.assertEquals(7, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(0, lineup.getDefenders().size());
        Assertions.assertEquals(0, lineup.getSupporters().size());
    }

    @Test
    public void test21_tooMuchPenguins_OneDefender(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 7,8,9);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1, pengu2), 0,1,0);
        Assertions.assertEquals(8, lineup.getTeamScore());
        Assertions.assertEquals(0, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(0, lineup.getSupporters().size());
    }

    @Test
    public void test22_tooMuchPenguins_OneSupporter(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 7,8,9);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1, pengu2), 0,0,1);
        Assertions.assertEquals(9, lineup.getTeamScore());
        Assertions.assertEquals(0, lineup.getAttackers().size());
        Assertions.assertEquals(0, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
    }

    @Test
    public void test23_tooMuchPenguins_oneAttackerOneDefenderOneSupporter(){
        Penguin pengu1 = new Penguin("a", 11,2,3);
        Penguin pengu2 = new Penguin("aa", 7,8,9);
        Penguin pengu3 = new Penguin("aaa", 10,9,7);
        Penguin pengu4 = new Penguin("b", -1,-2,-3);
        Penguin pengu5 = new Penguin("bb", 4,3,2);
        Penguin pengu6 = new Penguin("bbb", -100,-800,-9);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1, pengu2, pengu3, pengu4, pengu5, pengu6),
                1,1,1);
        Assertions.assertEquals(29, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu2));
    }

    @Test
    public void test23_tooMuchPenguins_twoAttackerTwoDefenderTwoSupporter(){
        Penguin pengu1 = new Penguin("a", 1,2,3);
        Penguin pengu2 = new Penguin("aa", 10,20,30);
        Penguin pengu3 = new Penguin("aaa", 100,200,300);
        Penguin pengu4 = new Penguin("b", 5,6,7);
        Penguin pengu5 = new Penguin("bb", 50,60,70);
        Penguin pengu6 = new Penguin("bbb", 500,600,700);
        Penguin pengu7 = new Penguin("c", -5,-6,-7);
        Penguin pengu8 = new Penguin("cc", -50,-60,-70);
        Penguin pengu9 = new Penguin("ccc", -500,-600,-700);
        Lineup lineup = Lineup.computeOptimalLineup
                (Set.of(pengu3, pengu2, pengu1, pengu4, pengu5, pengu6),
                        2,2,2);
        Assertions.assertEquals(1086, lineup.getTeamScore());
        Assertions.assertEquals(2, lineup.getAttackers().size());
        Assertions.assertEquals(2, lineup.getDefenders().size());
        Assertions.assertEquals(2, lineup.getSupporters().size());
        Assertions.assertTrue(lineup.getAttackers().contains(pengu1));
        Assertions.assertTrue(lineup.getAttackers().contains(pengu4));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu2));
        Assertions.assertTrue(lineup.getDefenders().contains(pengu5));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu3));
        Assertions.assertTrue(lineup.getSupporters().contains(pengu6));
    }

    @Test
    public void test24_oneGoodPenguinOnlyOneTimeTaken(){
        Penguin pengu1 = new Penguin("a",10,10,10);
        Penguin pengu2 = new Penguin("b", 0,0,0);
        Penguin pengu3 = new Penguin("bb", 0,0,0);
        Penguin pengu4 = new Penguin("bbb", 0,0,0);
        Penguin pengu5 = new Penguin("bbbb", 0,0,0);
        Lineup lineup = Lineup.computeOptimalLineup(Set.of(pengu1, pengu2, pengu3, pengu4, pengu5), 1,1,1);
        Assertions.assertEquals(10, lineup.getTeamScore());
        Assertions.assertEquals(1, lineup.getAttackers().size());
        Assertions.assertEquals(1, lineup.getDefenders().size());
        Assertions.assertEquals(1, lineup.getSupporters().size());
        int counter = 0;
        if(lineup.getAttackers().contains(pengu1)) counter++;
        if(lineup.getDefenders().contains(pengu1)) counter++;
        if(lineup.getSupporters().contains(pengu1)) counter++;
        Assertions.assertEquals(1, counter);

    }
}
