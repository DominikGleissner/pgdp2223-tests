package pgdp.teams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;


public class DGTestScores {

    @Test
    public void test01_EmptyTeam(){
        Lineup lineup = new Lineup(Set.of(), Set.of(), Set.of());
        Assertions.assertEquals(0, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(0, lineup.getTeamScore());
    }

    @Test
    public void test02_OnePinguinAttacker(){
        Lineup lineup = new Lineup(Set.of(new Penguin("dg", 9,8,7)), Set.of(), Set.of());
        Assertions.assertEquals(9, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(9, lineup.getTeamScore());
    }

    @Test
    public void test03_OnePinguinDefender(){
        Lineup lineup = new Lineup(Set.of(), Set.of(new Penguin("dg", 9,8,7)), Set.of());
        Assertions.assertEquals(8, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(8, lineup.getTeamScore());
    }

    @Test
    public void test04_OnePinguinSupporter(){
        Lineup lineup = new Lineup(Set.of(), Set.of(), Set.of(new Penguin("dg", 9,8,7)));
        Assertions.assertEquals(7, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(7, lineup.getTeamScore());
    }

    @Test
    public void test05_TwoPinguinAttackerWithoutSynergy(){
        Lineup lineup = new Lineup(Set.of(new Penguin("dg", 9,8,7), new Penguin("dgg", 90,80,70)),
                Set.of(),
                Set.of());
        Assertions.assertEquals(99, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(99, lineup.getTeamScore());
    }

    @Test
    public void test06_TwoPinguinDefenderWithoutSynergy(){
        Lineup lineup = new Lineup(Set.of(),
                Set.of(new Penguin("dg", 9,8,7), new Penguin("dgg", 90, 80, 70)),
                Set.of());
        Assertions.assertEquals(88, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(88, lineup.getTeamScore());
    }

    @Test
    public void test07_OnePinguinSupporterWithoutSynergy(){
        Lineup lineup = new Lineup(Set.of(),
                Set.of(),
                Set.of(new Penguin("dg", 9,8,7), new Penguin("dgg", 90, 80, 70)));
        Assertions.assertEquals(77, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(77, lineup.getTeamScore());
    }

    @Test
    public void test08_TwoPinguinAttackerWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin.setSynergy(pengu1, pengu2, 100);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu2),
                Set.of(),
                Set.of());
        Assertions.assertEquals(99, lineup.getTeamSkill());
        Assertions.assertEquals(200, lineup.getTeamSynergy());
        Assertions.assertEquals(299, lineup.getTeamScore());
    }

    @Test
    public void test09_TwoPinguinDefenderWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin.setSynergy(pengu1, pengu2, 100);
        Lineup lineup = new Lineup(Set.of(),
                Set.of(pengu1, pengu2),
                Set.of());
        Assertions.assertEquals(88, lineup.getTeamSkill());
        Assertions.assertEquals(200, lineup.getTeamSynergy());
        Assertions.assertEquals(288, lineup.getTeamScore());
    }

    @Test
    public void test10_TwoPinguinSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin.setSynergy(pengu1, pengu2, 100);
        Lineup lineup = new Lineup(Set.of(),
                Set.of(),
                Set.of(pengu1, pengu2));
        Assertions.assertEquals(77, lineup.getTeamSkill());
        Assertions.assertEquals(200, lineup.getTeamSynergy());
        Assertions.assertEquals(277, lineup.getTeamScore());
    }

    @Test
    public void test11_TwoPinguinAttackerOneDefenderWithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu2),
                Set.of(pengu3),
                Set.of());
        Assertions.assertEquals(899, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(899, lineup.getTeamScore());
    }

    @Test
    public void test12_TwoPinguinAttackerOneSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu2),
                Set.of(),
                Set.of(pengu3));
        Assertions.assertEquals(799, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(799, lineup.getTeamScore());
    }

    @Test
    public void test13_TwoPinguinDefenderOneAttackerWithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Lineup lineup = new Lineup(Set.of(pengu3),
                Set.of(pengu1, pengu2),
                Set.of());
        Assertions.assertEquals(988, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(988, lineup.getTeamScore());
    }

    @Test
    public void test14_TwoPinguinDefenderOneSupporterWithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Lineup lineup = new Lineup(Set.of(),
                Set.of(pengu1, pengu2),
                Set.of(pengu3));
        Assertions.assertEquals(788, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(788, lineup.getTeamScore());
    }

    @Test
    public void test15_TwoPinguinSupporterOneAttackerWithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Lineup lineup = new Lineup(Set.of(pengu3),
                Set.of(),
                Set.of(pengu1, pengu2));
        Assertions.assertEquals(977, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(977, lineup.getTeamScore());
    }

    @Test
    public void test16_TwoPinguinSupporterOneDefenderWithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Lineup lineup = new Lineup(Set.of(),
                Set.of(pengu3),
                Set.of(pengu1, pengu2));
        Assertions.assertEquals(877, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(877, lineup.getTeamScore());
    }

    @Test
    public void test17_TwoPinguinAttackerOneDefenderWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin.setSynergy(pengu1, pengu2, 1000);
        Penguin.setSynergy(pengu1, pengu3, 2000);
        Penguin.setSynergy(pengu2, pengu3, 3000);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu2),
                Set.of(pengu3),
                Set.of());
        Assertions.assertEquals(899, lineup.getTeamSkill());
        Assertions.assertEquals(7000, lineup.getTeamSynergy());
        Assertions.assertEquals(7899, lineup.getTeamScore());
    }

    @Test
    public void test18_TwoPinguinAttackerOneSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin.setSynergy(pengu1, pengu2, 1000);
        Penguin.setSynergy(pengu1, pengu3, 2000);
        Penguin.setSynergy(pengu2, pengu3, 3000);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu2),
                Set.of(),
                Set.of(pengu3));
        Assertions.assertEquals(799, lineup.getTeamSkill());
        Assertions.assertEquals(7000, lineup.getTeamSynergy());
        Assertions.assertEquals(7799, lineup.getTeamScore());
    }

    @Test
    public void test19_TwoPinguinDefenderOneAttackerWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin.setSynergy(pengu1, pengu2, 1000);
        Penguin.setSynergy(pengu1, pengu3, 2000);
        Penguin.setSynergy(pengu2, pengu3, 3000);
        Lineup lineup = new Lineup(Set.of(pengu1),
                Set.of(pengu3, pengu2),
                Set.of());
        Assertions.assertEquals(889, lineup.getTeamSkill());
        Assertions.assertEquals(9000, lineup.getTeamSynergy());
        Assertions.assertEquals(9889, lineup.getTeamScore());
    }

    @Test
    public void test20_TwoPinguinDefenderOneSupporterWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin.setSynergy(pengu1, pengu2, 1000);
        Penguin.setSynergy(pengu1, pengu3, 2000);
        Penguin.setSynergy(pengu2, pengu3, 3000);
        Lineup lineup = new Lineup(Set.of(),
                Set.of(pengu3, pengu2),
                Set.of(pengu1));
        Assertions.assertEquals(887, lineup.getTeamSkill());
        Assertions.assertEquals(9000, lineup.getTeamSynergy());
        Assertions.assertEquals(9887, lineup.getTeamScore());
    }

    @Test
    public void test21_TwoPinguinSupporterOneAttackerWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin.setSynergy(pengu1, pengu2, 1000);
        Penguin.setSynergy(pengu1, pengu3, 2000);
        Penguin.setSynergy(pengu2, pengu3, 3000);
        Lineup lineup = new Lineup(Set.of(pengu1),
                Set.of(),
                Set.of(pengu3, pengu2));
        Assertions.assertEquals(779, lineup.getTeamSkill());
        Assertions.assertEquals(9000, lineup.getTeamSynergy());
        Assertions.assertEquals(9779, lineup.getTeamScore());
    }

    @Test
    public void test22_TwoPinguinSupporterOneDefenderWithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin.setSynergy(pengu1, pengu2, 1000);
        Penguin.setSynergy(pengu1, pengu3, 2000);
        Penguin.setSynergy(pengu2, pengu3, 3000);
        Lineup lineup = new Lineup(Set.of(),
                Set.of(pengu1),
                Set.of(pengu3, pengu2));
        Assertions.assertEquals(778, lineup.getTeamSkill());
        Assertions.assertEquals(9000, lineup.getTeamSynergy());
        Assertions.assertEquals(9778, lineup.getTeamScore());
    }

    @Test
    public void test23_BiggerExample1NegativeValues(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin pengu4 = new Penguin("ddg", -9, -8, -7);
        Penguin pengu5 = new Penguin("dddg", -90, -80, -70);
        Penguin pengu6 = new Penguin("ddddg", -900, -800, -700);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu4),
                Set.of(pengu2, pengu5),
                Set.of(pengu3, pengu6));
        Assertions.assertEquals(0, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(0, lineup.getTeamScore());
    }

    @Test
    public void test24_BiggerExample1NegativeSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin pengu4 = new Penguin("ddg", -9, -8, -7);
        Penguin pengu5 = new Penguin("dddg", -90, -80, -70);
        Penguin pengu6 = new Penguin("ddddg", -900, -800, -700);
        Penguin.setSynergy(pengu1, pengu2, -1);
        Penguin.setSynergy(pengu1, pengu3, -1);
        Penguin.setSynergy(pengu2, pengu3, -1);
        Penguin.setSynergy(pengu1, pengu4, -1);
        Penguin.setSynergy(pengu1, pengu5, -1);
        Penguin.setSynergy(pengu2, pengu6, -1);
        Penguin.setSynergy(pengu6, pengu5, -1);
        Penguin.setSynergy(pengu5, pengu3, -1);
        Penguin.setSynergy(pengu4, pengu3, -1);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu4),
                Set.of(pengu2, pengu5),
                Set.of(pengu3, pengu6));
        Assertions.assertEquals(0, lineup.getTeamSkill());
        Assertions.assertEquals(-10, lineup.getTeamSynergy());
        Assertions.assertEquals(-10, lineup.getTeamScore());
    }

    @Test
    public void test25_BiggerExample2WithoutSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin pengu4 = new Penguin("ddg", 1, 2, 3);
        Penguin pengu5 = new Penguin("dddg", 10, 20, 30);
        Penguin pengu6 = new Penguin("ddddg", 100, 200, 300);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu4),
                Set.of(pengu2, pengu5),
                Set.of(pengu3, pengu6));
        Assertions.assertEquals(1110, lineup.getTeamSkill());
        Assertions.assertEquals(0, lineup.getTeamSynergy());
        Assertions.assertEquals(1110, lineup.getTeamScore());
    }

    @Test
    public void test26_BiggerExample2WithSynergy(){
        Penguin pengu1 = new Penguin("dg", 9,8,7);
        Penguin pengu2 = new Penguin("dgg", 90,80,70);
        Penguin pengu3 = new Penguin("dggg", 900,800,700);
        Penguin pengu4 = new Penguin("ddg", 1, 2, 3);
        Penguin pengu5 = new Penguin("dddg", 10, 20, 30);
        Penguin pengu6 = new Penguin("ddddg", 100, 200, 300);
        Penguin.setSynergy(pengu1, pengu2, 1);
        Penguin.setSynergy(pengu1, pengu3, 2);
        Penguin.setSynergy(pengu2, pengu3, 3);
        Penguin.setSynergy(pengu1, pengu4, 4);
        Penguin.setSynergy(pengu1, pengu5, 5);
        Penguin.setSynergy(pengu2, pengu6, 6);
        Penguin.setSynergy(pengu6, pengu5, 7);
        Penguin.setSynergy(pengu5, pengu3, 8);
        Penguin.setSynergy(pengu4, pengu3, 9);
        Lineup lineup = new Lineup(Set.of(pengu1, pengu4),
                Set.of(pengu2, pengu5),
                Set.of(pengu3, pengu6));
        Assertions.assertEquals(1110, lineup.getTeamSkill());
        Assertions.assertEquals(49, lineup.getTeamSynergy());
        Assertions.assertEquals(1159, lineup.getTeamScore());
    }

}
