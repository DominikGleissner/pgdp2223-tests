package pgdp.messenger;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class W05H03TestDG {

    @BeforeEach
    public void resetStaticUserIDandTopicID() throws NoSuchFieldException, IllegalAccessException {
        Field field = PinguTalk.class.getDeclaredField("userID");
        field.setAccessible(true);
        field.set(null, 0);
        Field field1 = PinguTalk.class.getDeclaredField("topicID");
        field1.setAccessible(true);
        field1.set(null, 0);
    }


    // TESTS FÜR PINGUTALK


    @Test
    @DisplayName("Test pinguTalk Konstruktor mit einigen Edgecases")
    public void Test01(){
        PinguTalk pinguTalk = new PinguTalk(10,1);
        Assertions.assertEquals(1, pinguTalk.getTopics().length, "1,10 failed");
        pinguTalk = new PinguTalk(1,10);
        Assertions.assertEquals(1, pinguTalk.getMembers().getLength(),"10,1 failed");

        pinguTalk = new PinguTalk(10,0);
        Assertions.assertEquals(1, pinguTalk.getTopics().length, "0,10 failed");
        pinguTalk = new PinguTalk(0,10);
        Assertions.assertEquals(1, pinguTalk.getMembers().getLength(),"10,0 failed");

        pinguTalk = new PinguTalk(10,10);
        Assertions.assertEquals(10, pinguTalk.getTopics().length, "10,10 failed");
        pinguTalk = new PinguTalk(10,10);
        Assertions.assertEquals(10, pinguTalk.getMembers().getLength(),"10,10 failed");

        pinguTalk = new PinguTalk(-1,-1);
        Assertions.assertEquals(1, pinguTalk.getTopics().length, "-1,-1 failed");
        pinguTalk = new PinguTalk(-1,-1);
        Assertions.assertEquals(1, pinguTalk.getMembers().getLength(),"-1,-1 failed");
    }

    @Test
    @DisplayName("Test addMember pinguTalk 1 Array muss nicht vergrößert werden")
    public void Test02(){
        PinguTalk pinguTalk = new PinguTalk(10,10);
        User supervisor = new User(1000, "chef", null);
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[0], "null vor input failed");
        pinguTalk.addMember("member1", null);
        Assertions.assertEquals("member1", pinguTalk.getMembers().getUsers()[0].getName(), "ein input name failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[0].getSupervisor(), "ein input supervisor failed");
        Assertions.assertEquals(0, pinguTalk.getMembers().getUsers()[0].getId(), "ein input id failed");
        Assertions.assertEquals(10, pinguTalk.getMembers().getLength(), "ein input länge failed");
        pinguTalk.addMember("member2", supervisor);
        Assertions.assertEquals("member2", pinguTalk.getMembers().getUsers()[1].getName(), "zwei inputs name failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[1].getSupervisor(), "zwei inputs supervisor failed");
        Assertions.assertEquals(1, pinguTalk.getMembers().getUsers()[1].getId(), "zwei input id failed");
        Assertions.assertEquals(10, pinguTalk.getMembers().getLength(), "zwei input länge failed");
    }

    @Test
    @DisplayName("Test addMember pinguTalk 2 Array muss mehrfach vergrößert werden")
    public void Test03(){
        PinguTalk pinguTalk = new PinguTalk(1,10);
        User supervisor = new User(1000, "chef", null);
        pinguTalk.addMember("member1", null);
        Assertions.assertEquals("member1", pinguTalk.getMembers().getUsers()[0].getName(), "ein input name failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[0].getSupervisor(), "ein input supervisor failed");
        Assertions.assertEquals(0, pinguTalk.getMembers().getUsers()[0].getId(), "ein input id failed");
        Assertions.assertEquals(1, pinguTalk.getMembers().getLength(), "ein input länge failed");
        pinguTalk.addMember("member2", supervisor);
        Assertions.assertEquals("member2", pinguTalk.getMembers().getUsers()[1].getName(), "zwei inputs name failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[1].getSupervisor(), "zwei inputs supervisor failed");
        Assertions.assertEquals(1, pinguTalk.getMembers().getUsers()[1].getId(), "zwei input id failed");
        Assertions.assertEquals(2, pinguTalk.getMembers().getLength(), "zwei input länge failed");
        pinguTalk.addMember("member3", supervisor);
        Assertions.assertEquals("member3", pinguTalk.getMembers().getUsers()[2].getName(), "drei inputs name failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[2].getSupervisor(), "drei inputs supervisor failed");
        Assertions.assertEquals(2, pinguTalk.getMembers().getUsers()[2].getId(), "drei input id failed");
        Assertions.assertEquals(4, pinguTalk.getMembers().getLength(), "drei input länge failed");
        pinguTalk.addMember("member4", null);
        Assertions.assertEquals("member4", pinguTalk.getMembers().getUsers()[3].getName(), "vier inputs name failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[3].getSupervisor(), "vier inputs supervisor failed");
        Assertions.assertEquals(3, pinguTalk.getMembers().getUsers()[3].getId(), "vier input id failed");
        Assertions.assertEquals(4,pinguTalk.getMembers().getLength(), "vier input länge failed");
    }

    @Test
    @DisplayName("Test deleteMember pinguTalk ausführlich mit vielen inserts und deletes")
    public void Test04(){
        PinguTalk pinguTalk = new PinguTalk(1,10);
        User supervisor = new User(1000, "chef", null);
        pinguTalk.addMember("member1", null); // id = 0
        pinguTalk.addMember("member2", null);
        pinguTalk.addMember("member3", supervisor);
        pinguTalk.addMember("member4", null);
        pinguTalk.addMember("member5", supervisor);
        pinguTalk.addMember("member6", supervisor); // id = 5
        // noch ein addmember test
        Assertions.assertEquals("member6", pinguTalk.getMembers().getUsers()[5].getName(), "sechs inputs name failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[5].getSupervisor(), "sechs inputs supervisor failed");
        Assertions.assertEquals(5, pinguTalk.getMembers().getUsers()[5].getId(), "sechs input id failed");
        Assertions.assertEquals(8,pinguTalk.getMembers().getLength(), "sechs input länge failed");
        // beginne deleten
        User removedUser = pinguTalk.deleteMember(5);
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[5], "ein remove hat nicht removed failed");
        Assertions.assertEquals("member6", removedUser.getName(), "member6 name removed failed");
        Assertions.assertEquals("member5", pinguTalk.getMembers().getUsers()[4].getName(), "soll andere nicht verändert haben failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[4].getSupervisor(), "soll andere nicht verändert haben failed");
        Assertions.assertEquals(4, pinguTalk.getMembers().getUsers()[4].getId(), "soll andere nicht verändert haben failed");
        Assertions.assertEquals(8,pinguTalk.getMembers().getLength(), "soll andere nicht verändert haben failed");
        removedUser = pinguTalk.deleteMember(5);
        Assertions.assertEquals(null, removedUser, "falscher output bei nicht vorhandenem user");
        Assertions.assertEquals("member5", pinguTalk.getMembers().getUsers()[4].getName(), "soll andere nicht verändert haben failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[4].getSupervisor(), "soll andere nicht verändert haben failed");
        Assertions.assertEquals(4, pinguTalk.getMembers().getUsers()[4].getId(), "soll andere nicht verändert haben failed");
        Assertions.assertEquals(8,pinguTalk.getMembers().getLength(), "soll andere nicht verändert haben failed");
        removedUser = pinguTalk.deleteMember(4);
        removedUser = pinguTalk.deleteMember(3);
        Assertions.assertEquals("member4", removedUser.getName(), "member3 removed name failed");
        Assertions.assertEquals("member1", pinguTalk.getMembers().getUsers()[0].getName(), "member 1 nach delete failed");
        Assertions.assertEquals("member2", pinguTalk.getMembers().getUsers()[1].getName(), "member 2 nach delete failed");
        Assertions.assertEquals("member3", pinguTalk.getMembers().getUsers()[2].getName(), "member 3 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[3], "member 4 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[4], "member 5 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[5], "member 6 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[6], "member 7 nach delete failed");
        pinguTalk.addMember("member7", supervisor);
        Assertions.assertEquals("member7", pinguTalk.getMembers().getUsers()[3].getName(), "7. nach delete name failed");
        Assertions.assertEquals(supervisor, pinguTalk.getMembers().getUsers()[3].getSupervisor(), "7. nach delete supervisor failed");
        Assertions.assertEquals(6, pinguTalk.getMembers().getUsers()[3].getId(), "7. nach delete id failed");
        Assertions.assertEquals(8,pinguTalk.getMembers().getLength(), "7. nach delete länge failed");
        removedUser = pinguTalk.deleteMember(6);
        removedUser = pinguTalk.deleteMember(1000);
        Assertions.assertEquals(null, removedUser, "nicht vorhandener member delete failed");
        removedUser = pinguTalk.deleteMember(-1000);
        Assertions.assertEquals(null, removedUser, "nicht vorhandener member delete failed");
        removedUser = pinguTalk.deleteMember(2);
        removedUser = pinguTalk.deleteMember(1);
        removedUser = pinguTalk.deleteMember(0);
        Assertions.assertEquals("member1", removedUser.getName(), "member1 removed name failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[0], "member 1 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[1], "member 2 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[2], "member 3 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[3], "member 4 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[4], "member 5 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[5], "member 6 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[6], "member 7 nach delete failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[7], "member 8 nach delete failed");
        pinguTalk.addMember("member8", null);
        Assertions.assertEquals("member8", pinguTalk.getMembers().getUsers()[0].getName(), "8. nach delete name failed");
        Assertions.assertEquals(null, pinguTalk.getMembers().getUsers()[0].getSupervisor(), "8. nach delete supervisor failed");
        Assertions.assertEquals(7, pinguTalk.getMembers().getUsers()[0].getId(), "8. nach delete id failed");
        Assertions.assertEquals(8,pinguTalk.getMembers().getLength(), "8. nach delete länge failed");
    }

    @Test
    @DisplayName("Test createNewMember pinguTalk 1 ein insert für Array Länge 1")
    public void Test05(){
        PinguTalk pinguTalk = new PinguTalk(1,1);
        Topic insertedTopic = pinguTalk.createNewTopic("topic1");
        Assertions.assertEquals("topic1", insertedTopic.getName());
        Assertions.assertEquals(0, insertedTopic.getId(), "falsche id nach 1 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[0], "topic nach 1 topic create failed");
        Assertions.assertEquals(1, pinguTalk.getTopics().length, "länge nach 1 topic failed");

        // array voll und inserte nochmal => gibt null
        insertedTopic = pinguTalk.createNewTopic("topic2");
        Assertions.assertEquals(null, insertedTopic);
        Assertions.assertEquals(1, pinguTalk.getTopics().length, "länge nach 2 topic failed");
        Assertions.assertEquals("topic1", pinguTalk.getTopics()[0].getName(), "topic 1 soll nicht geändert werden");
    }

    @Test
    @DisplayName("Test createNewMember pinguTalk 2 mehrere inserts in genug großes Array")
    public void Test06(){
        PinguTalk pinguTalk = new PinguTalk(1,10);
        Topic insertedTopic = pinguTalk.createNewTopic("topic1");
        Assertions.assertEquals("topic1", insertedTopic.getName());
        Assertions.assertEquals(0, insertedTopic.getId(), "falsche id nach 1 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[0], "topic nach 1 topic create failed");
        Assertions.assertEquals(10, pinguTalk.getTopics().length, "länge nach 1 topic failed");

        insertedTopic = pinguTalk.createNewTopic("topic2");
        Assertions.assertEquals("topic2", insertedTopic.getName());
        Assertions.assertEquals(1, insertedTopic.getId(), "falsche id nach 2 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[1], "topic nach 2 topic create failed");
        Assertions.assertEquals(10, pinguTalk.getTopics().length, "länge nach 2 topic failed");

        insertedTopic = pinguTalk.createNewTopic("topic3");
        Assertions.assertEquals("topic3", insertedTopic.getName());
        Assertions.assertEquals(2, insertedTopic.getId(), "falsche id nach 3 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[2], "topic nach 3 topic create failed");
        Assertions.assertEquals(10, pinguTalk.getTopics().length, "länge nach 3 topic failed");

        insertedTopic = pinguTalk.createNewTopic("topic4");
        Assertions.assertEquals("topic4", insertedTopic.getName());
        Assertions.assertEquals(3, insertedTopic.getId(), "falsche id nach 4 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[3], "topic nach 4 topic create failed");
        Assertions.assertEquals(10, pinguTalk.getTopics().length, "länge nach 4 topic failed");
    }

    @Test
    @DisplayName("Test deleteTopic pinguTalk ausführlich mit vielen inserts und deletes")
    public void Test07(){
        PinguTalk pinguTalk = new PinguTalk(10,3);
        Topic removedTopic = pinguTalk.deleteTopic(10);
        Assertions.assertEquals(null, removedTopic, "topic sollte null sein");
        Topic insertedTopic = pinguTalk.createNewTopic("topic1");
        insertedTopic = pinguTalk.createNewTopic("topic2");
        insertedTopic = pinguTalk.createNewTopic("topic3");
        // test nochmal createNewTopic
        Assertions.assertEquals("topic3", insertedTopic.getName());
        Assertions.assertEquals(2, insertedTopic.getId(), "falsche id nach 3 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[2], "topic nach 3 topic create failed");
        Assertions.assertEquals(3, pinguTalk.getTopics().length, "länge nach 3 topic failed");
        // delete
        removedTopic = pinguTalk.deleteTopic(0);
        Assertions.assertEquals("topic1", removedTopic.getName(), "delete topic1 output failed");
        Assertions.assertEquals(null, pinguTalk.getTopics()[0], "delete topic1 null im array failed");
        // inserte auf index 0
        insertedTopic = pinguTalk.createNewTopic("topic4");
        Assertions.assertEquals("topic4", insertedTopic.getName());
        Assertions.assertEquals(3, insertedTopic.getId(), "falsche id nach 4 create");
        Assertions.assertEquals(insertedTopic, pinguTalk.getTopics()[0], "topic nach 4 topic create failed");
        Assertions.assertEquals(3, pinguTalk.getTopics().length, "länge nach 4 topic failed");
        // remove id 0 => null
        removedTopic = pinguTalk.deleteTopic(0);
        Assertions.assertEquals(null, removedTopic, "topic sollte null sein");
        // remove alle
        removedTopic = pinguTalk.deleteTopic(1); // topic2
        removedTopic = pinguTalk.deleteTopic(3); // topic4
        removedTopic = pinguTalk.deleteTopic(2); // topic3
        Assertions.assertEquals("topic3", removedTopic.getName(), "delete topic3 output failed");
        Assertions.assertEquals(null, pinguTalk.getTopics()[2], "delete topic3 null im array failed");
        Assertions.assertEquals(null, pinguTalk.getTopics()[0], "delete topic4 null im array failed");
        Assertions.assertEquals(null, pinguTalk.getTopics()[1], "delete topic2 null im array failed");
        // inserte wieder
        insertedTopic = pinguTalk.createNewTopic("topic5");
        insertedTopic = pinguTalk.createNewTopic("topic6");
        Assertions.assertEquals(4, pinguTalk.getTopics()[0].getId(), "id nach insert topic5 failed");
        Assertions.assertEquals("topic5", pinguTalk.getTopics()[0].getName(), "name nach insert topic5 failed");
        Assertions.assertEquals(5, pinguTalk.getTopics()[1].getId(), "id nach insert topic6 failed");
        Assertions.assertEquals("topic6", pinguTalk.getTopics()[1].getName(), "name nach insert topic6 failed");
        // delete topic6
        removedTopic = pinguTalk.deleteTopic(5);
        Assertions.assertEquals(null, pinguTalk.getTopics()[1], "delete topic6 null im array failed");
        Assertions.assertEquals("topic6", removedTopic.getName(), "falscher name removed");
        // inserte noch einmal
        insertedTopic = pinguTalk.createNewTopic("topic7");
        Assertions.assertEquals("topic7", pinguTalk.getTopics()[1].getName(), "insert topic7 name failed");
        Assertions.assertEquals(6, pinguTalk.getTopics()[1].getId(), "insert topic 7 id failed");
    }


    // TESTS FÜR LIST


    @Test
    @DisplayName("Test getByID List 1 mit 3 inserts")
    public void Test08(){
        List list = new List();

        Message insertMessage = new Message(0, LocalDateTime.of(2019, 03, 28, 14, 33, 48, 640000),
                new User(0, "person00", null), "message01");
        list.add(insertMessage);
        Assertions.assertEquals(insertMessage, list.getByID(0), "get list message 0 after 1 insert failed");
        Assertions.assertEquals(null, list.getByID(1), "get list message not in list after 1 insert failed");

        insertMessage = new Message(1, LocalDateTime.of(2019, 03, 29, 14, 33, 48, 640000),
                new User(1, "person01", null), "message02");
        list.add(insertMessage);
        Assertions.assertEquals("message01", list.getByID(0).getContent(), "get list message 0 after 2 insert failed");
        Assertions.assertEquals("message02", list.getByID(1).getContent(), "get list message 1 after 2 insert failed");
        Assertions.assertEquals(null, list.getByID(2), "get list message not in list after 2 insert failed");
        Assertions.assertEquals(null, list.getByID(-1), "get list message not in list after 2 insert failed");
        Assertions.assertEquals(null, list.getByID(1000), "get list message not in list after 2 insert failed");

        insertMessage = new Message(2, LocalDateTime.of(2019, 03, 30, 14, 33, 48, 640000),
                new User(2, "person02", null), "message03");
        list.add(insertMessage);
        Assertions.assertEquals("message01", list.getByID(0).getContent(), "get list message 0 after 3 insert failed");
        Assertions.assertEquals("message02", list.getByID(1).getContent(), "get list message 1 after 3 insert failed");
        Assertions.assertEquals("message03", list.getByID(2).getContent(), "get list message 2 after 3 insert failed");
        Assertions.assertEquals(null, list.getByID(3), "get list message not in list after 3 insert failed");
        Assertions.assertEquals(null, list.getByID(-1), "get list message not in list after 3 insert failed");
        Assertions.assertEquals(null, list.getByID(1000), "get list message not in list after 3 insert failed");
    }

    @Test
    @DisplayName("Test getByID List 2 mit insert gleicher ID")
    public void Test09(){
        // gibt nur erste message mit dieser id aus
        List list = new List();

        Message insertMessage = new Message(0, LocalDateTime.of(2019, 03, 28, 14, 33, 48, 640000),
                new User(0, "person00", null), "message01");
        list.add(insertMessage);
        Assertions.assertEquals(insertMessage, list.getByID(0), "get list message 0 after 1 insert failed");
        Assertions.assertEquals(null, list.getByID(1), "get list message not in list after 1 insert failed");

        insertMessage = new Message(0, LocalDateTime.of(2019, 03, 29, 14, 33, 48, 640000),
                new User(1, "person01", null), "message02");
        list.add(insertMessage);
        Assertions.assertEquals("message01", list.getByID(0).getContent(), "get list message 0 after 2 insert failed");
        Assertions.assertEquals(null, list.getByID(1), "get list message not in the list failed");
        Assertions.assertEquals(null, list.getByID(2), "get list message not in list after 2 insert failed");
        Assertions.assertEquals(null, list.getByID(-1), "get list message not in list after 2 insert failed");
        Assertions.assertEquals(null, list.getByID(1000), "get list message not in list after 2 insert failed");
    }

    @Test
    @DisplayName("Test getByID List 3 mit leerer list")
    public void Test10(){
        List list = new List();

        Assertions.assertEquals(null, list.getByID(0), "empty list failed");
        Assertions.assertEquals(null, list.getByID(-1), "empty list failed");
        Assertions.assertEquals(null, list.getByID(1), "empty list failed");
        Assertions.assertEquals(null, list.getByID(100), "empty list failed");

    }

    @Test
    @DisplayName("Test megaMerge List 1 einfache tests gleichlanger lists")
    public void Test11(){
        List[] lists = new List[5];
        Message insertMessage;
        for(int i = 0; i < lists.length; i++){
            lists[i] = new List();
            for(int j = 0; j < 2; j++){
                insertMessage = new Message(j, LocalDateTime.of(2010+i, 03, 10+j, 14, 33, 48, 640000),
                        new User(j, "person0"+j, null), "message0"+j);
                lists[i].add(insertMessage);
            }
        }
        Assertions.assertEquals(lists[0].getByIndex(0), List.megaMerge(lists).getByIndex(0));
        Assertions.assertEquals(lists[0].getByIndex(1), List.megaMerge(lists).getByIndex(1));
        Assertions.assertEquals(lists[1].getByIndex(0), List.megaMerge(lists).getByIndex(2));
        Assertions.assertEquals(lists[1].getByIndex(1), List.megaMerge(lists).getByIndex(3));
        Assertions.assertEquals(lists[2].getByIndex(0), List.megaMerge(lists).getByIndex(4));
        Assertions.assertEquals(lists[2].getByIndex(1), List.megaMerge(lists).getByIndex(5));
        Assertions.assertEquals(lists[3].getByIndex(0), List.megaMerge(lists).getByIndex(6));
        Assertions.assertEquals(lists[3].getByIndex(1), List.megaMerge(lists).getByIndex(7));
        Assertions.assertEquals(lists[4].getByIndex(0), List.megaMerge(lists).getByIndex(8));
        Assertions.assertEquals(lists[4].getByIndex(1), List.megaMerge(lists).getByIndex(9));

        lists = new List[5];
        for(int i = 0; i < lists.length; i++){
            lists[i] = new List();
            for(int j = 0; j < 2; j++){
                insertMessage = new Message(j, LocalDateTime.of(2010+j, 03, 10+i, 14, 33, 48, 640000),
                        new User(j, "person0"+j, null), "message0"+j);
                lists[i].add(insertMessage);
            }
        }
        Assertions.assertEquals(lists[0].getByIndex(0), List.megaMerge(lists).getByIndex(0));
        Assertions.assertEquals(lists[1].getByIndex(0), List.megaMerge(lists).getByIndex(1));
        Assertions.assertEquals(lists[2].getByIndex(0), List.megaMerge(lists).getByIndex(2));
        Assertions.assertEquals(lists[3].getByIndex(0), List.megaMerge(lists).getByIndex(3));
        Assertions.assertEquals(lists[4].getByIndex(0), List.megaMerge(lists).getByIndex(4));
        Assertions.assertEquals(lists[0].getByIndex(1), List.megaMerge(lists).getByIndex(5));
        Assertions.assertEquals(lists[1].getByIndex(1), List.megaMerge(lists).getByIndex(6));
        Assertions.assertEquals(lists[2].getByIndex(1), List.megaMerge(lists).getByIndex(7));
        Assertions.assertEquals(lists[3].getByIndex(1), List.megaMerge(lists).getByIndex(8));
        Assertions.assertEquals(lists[4].getByIndex(1), List.megaMerge(lists).getByIndex(9));

        lists = new List[2];
        for(int i = 0; i < lists.length; i++){
            lists[i] = new List();
            for(int j = 0; j < 1; j++){
                insertMessage = new Message(j, LocalDateTime.of(2010+j, 03, 10+i, 14, 33, 48, 640000),
                        new User(j, "person0"+j, null), "message0"+j);
                lists[i].add(insertMessage);
            }
        }
        Assertions.assertEquals(lists[0].getByIndex(0), List.megaMerge(lists).getByIndex(0));
        Assertions.assertEquals(lists[1].getByIndex(0), List.megaMerge(lists).getByIndex(1));
    }

    @Test
    @DisplayName("Test megaMerge List 2 verschieden lange lists")
    public void Test12(){
        List[] lists = new List[3];
        Message insertMessage;
        for(int i = 0; i < lists.length; i++){
            lists[i] = new List();
            for(int j = 0; j < 2+i; j++){
                insertMessage = new Message(j, LocalDateTime.of(2010+i, 03, 10+j, 14, 33, 48, 640000),
                        new User(j, "person0"+j, null), "message0"+j);
                lists[i].add(insertMessage);
            }
        }
        Assertions.assertEquals(lists[0].getByIndex(0), List.megaMerge(lists).getByIndex(0));
        Assertions.assertEquals(lists[0].getByIndex(1), List.megaMerge(lists).getByIndex(1));
        Assertions.assertEquals(lists[1].getByIndex(0), List.megaMerge(lists).getByIndex(2));
        Assertions.assertEquals(lists[1].getByIndex(1), List.megaMerge(lists).getByIndex(3));
        Assertions.assertEquals(lists[1].getByIndex(2), List.megaMerge(lists).getByIndex(4));
        Assertions.assertEquals(lists[2].getByIndex(0), List.megaMerge(lists).getByIndex(5));
        Assertions.assertEquals(lists[2].getByIndex(1), List.megaMerge(lists).getByIndex(6));
        Assertions.assertEquals(lists[2].getByIndex(2), List.megaMerge(lists).getByIndex(7));
        Assertions.assertEquals(lists[2].getByIndex(3), List.megaMerge(lists).getByIndex(8));


        lists = new List[3];
        for(int i = 0; i < lists.length; i++){
            lists[i] = new List();
            for(int j = 0; j < 2+i; j++){
                insertMessage = new Message(j, LocalDateTime.of(2010+j, 03, 10+i, 14, 33, 48, 640000),
                        new User(j, "person0"+j, null), "message0"+j);
                lists[i].add(insertMessage);
            }
        }
        Assertions.assertEquals(lists[0].getByIndex(0), List.megaMerge(lists).getByIndex(0));
        Assertions.assertEquals(lists[1].getByIndex(0), List.megaMerge(lists).getByIndex(1));
        Assertions.assertEquals(lists[2].getByIndex(0), List.megaMerge(lists).getByIndex(2));
        Assertions.assertEquals(lists[0].getByIndex(1), List.megaMerge(lists).getByIndex(3));
        Assertions.assertEquals(lists[1].getByIndex(1), List.megaMerge(lists).getByIndex(4));
        Assertions.assertEquals(lists[2].getByIndex(1), List.megaMerge(lists).getByIndex(5));
        Assertions.assertEquals(lists[1].getByIndex(2), List.megaMerge(lists).getByIndex(6));
        Assertions.assertEquals(lists[2].getByIndex(2), List.megaMerge(lists).getByIndex(7));
        Assertions.assertEquals(lists[2].getByIndex(3), List.megaMerge(lists).getByIndex(8));


        lists = new List[3];
        for(int i = 0; i < lists.length; i++){
            lists[i] = new List();
            for(int j = 0; j < 2+i; j++){
                insertMessage = new Message(j, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                        new User(j, "person0"+j, null), "message0"+j);
                lists[i].add(insertMessage);
            }
        }
        List mergedList = List.megaMerge(lists);
        for(int i = 0; i < mergedList.size()-1; i++){
            Assertions.assertEquals(false, mergedList.getByIndex(i).getTimestamp().isBefore(mergedList.getByIndex(i+1).getTimestamp()), "Test gleiche zeiten failed");
        }
    }

    @Test
    @DisplayName("Test megaMerge List 3 leere Parameter, einzelne Lists null")
    public void Test13(){
        Assertions.assertEquals(0, List.megaMerge().size(), "keine parameter failed");
        Assertions.assertEquals(0, List.megaMerge(null).size(), "null test failed");


        List[] lists = new List[3];
        lists[0] = null;
        lists[1] = null;
        lists[2] = null;
        Assertions.assertEquals(0, List.megaMerge(lists).size(), "alle parameter null failed");


        lists[0] = new List();
        lists[1] = new List();
        lists[2] = new List();
        Assertions.assertEquals(true, List.megaMerge(lists).isEmpty(), "leere listen parameter failed");
        Assertions.assertEquals(0, List.megaMerge(lists).size(), "leere listen parameter failed");


        Message insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[1].add(insertMessage1);
        Message insertMessage2 = new Message(1, LocalDateTime.of(2011, 03, 10, 14, 33, 48, 640000),
                new User(1, "person02", null), "message02");
        lists[2].add(insertMessage2);
        List mergedList = List.megaMerge(lists);
        //System.out.println(List.megaMerge(lists).toString());
        Assertions.assertEquals(insertMessage1, mergedList.getByID(0), "erster Parameter leer failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByID(1), "erster Parameter leer failed");
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "erster Parameter leer failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByIndex(1), "erster Parameter leer failed");
        Assertions.assertEquals(2, mergedList.size(), "erster Parameter leer failed");


        lists[0] = new List();
        lists[1] = new List();
        lists[2] = new List();
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[0].add(insertMessage1);
        insertMessage2 = new Message(1, LocalDateTime.of(2011, 03, 10, 14, 33, 48, 640000),
                new User(1, "person02", null), "message02");
        lists[2].add(insertMessage2);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "zweiter Parameter leer failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByIndex(1), "zweiter Parameter leer failed");
        Assertions.assertEquals(2, mergedList.size(), "zweiter Parameter leer failed");


        lists[0] = new List();
        lists[1] = new List();
        lists[2] = new List();
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[0].add(insertMessage1);
        insertMessage2 = new Message(1, LocalDateTime.of(2011, 03, 10, 14, 33, 48, 640000),
                new User(1, "person02", null), "message02");
        lists[1].add(insertMessage2);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "dritter Parameter leer failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByIndex(1), "dritter Parameter leer failed");
        Assertions.assertEquals(2, mergedList.size(), "dritter Parameter leer failed");


        lists[0] = null;
        lists[1] = new List();
        lists[2] = new List();
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[1].add(insertMessage1);
        insertMessage2 = new Message(1, LocalDateTime.of(2011, 03, 10, 14, 33, 48, 640000),
                new User(1, "person02", null), "message02");
        lists[2].add(insertMessage2);
        mergedList = List.megaMerge(lists);
        //System.out.println(List.megaMerge(lists).toString());
        Assertions.assertEquals(insertMessage1, mergedList.getByID(0), "erster Parameter null failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByID(1), "erster Parameter null failed");
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "erster Parameter null failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByIndex(1), "erster Parameter null failed");
        Assertions.assertEquals(2, mergedList.size(), "erster Parameter null failed");


        lists[0] = new List();
        lists[1] = null;
        lists[2] = new List();
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[0].add(insertMessage1);
        insertMessage2 = new Message(1, LocalDateTime.of(2011, 03, 10, 14, 33, 48, 640000),
                new User(1, "person02", null), "message02");
        lists[2].add(insertMessage2);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "zweiter Parameter null failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByIndex(1), "zweiter Parameter null failed");
        Assertions.assertEquals(2, mergedList.size(), "zweiter Parameter null failed");


        lists[0] = new List();
        lists[1] = new List();
        lists[2] = null;
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[0].add(insertMessage1);
        insertMessage2 = new Message(1, LocalDateTime.of(2011, 03, 10, 14, 33, 48, 640000),
                new User(1, "person02", null), "message02");
        lists[1].add(insertMessage2);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "dritter Parameter null failed");
        Assertions.assertEquals(insertMessage2, mergedList.getByIndex(1), "dritter Parameter null failed");
        Assertions.assertEquals(2, mergedList.size(), "dritter Parameter null failed");


        lists[0] = new List();
        lists[1] = null;
        lists[2] = null;
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[0].add(insertMessage1);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "dritter Parameter null failed");
        Assertions.assertEquals(1, mergedList.size(), "dritter Parameter null failed");


        lists[0] = null;
        lists[1] = new List();
        lists[2] = null;
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[1].add(insertMessage1);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "dritter Parameter null failed");
        Assertions.assertEquals(1, mergedList.size(), "dritter Parameter null failed");


        lists[0] = null;
        lists[1] = null;
        lists[2] = new List();
        insertMessage1 = new Message(0, LocalDateTime.of(2010, 03, 10, 14, 33, 48, 640000),
                new User(0, "person01", null), "message01");
        lists[2].add(insertMessage1);
        mergedList = List.megaMerge(lists);
        Assertions.assertEquals(insertMessage1, mergedList.getByIndex(0), "dritter Parameter null failed");
        Assertions.assertEquals(1, mergedList.size(), "dritter Parameter null failed");
    }

    @Test
    @DisplayName("Test filterDays List 1 normale werte")
    public void Test14(){
        List list = new List();
        Message insertMessage;
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        LocalDateTime start = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        LocalDateTime end = LocalDateTime.of(2017, 03, 10, 14, 33, 48, 640000);
        List filteredList = list.filterDays(start, end);
        Assertions.assertEquals(4, filteredList.size(), "size test failed");
        Assertions.assertEquals("message03", filteredList.getByIndex(0).getContent(), "message at 0 failed");
        Assertions.assertEquals("message04", filteredList.getByIndex(1).getContent(), "message at 1 failed");
        Assertions.assertEquals("message05", filteredList.getByIndex(2).getContent(), "message at 2 failed");
        Assertions.assertEquals("message06", filteredList.getByIndex(3).getContent(), "message at 3 failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");


        list = new List();
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        start = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        end = LocalDateTime.of(2014, 03, 10, 14, 33, 48, 640000);
        filteredList = list.filterDays(start, end);
        Assertions.assertEquals(1, filteredList.size(), "size test failed");
        Assertions.assertEquals("message03", filteredList.getByIndex(0).getContent(), "message at 0 failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");

    }

    @Test
    @DisplayName("Test filterDays List 2 edgecases")
    public void Test15(){
        List list = new List();
        Message insertMessage;
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        LocalDateTime start = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        LocalDateTime end = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        List filteredList = list.filterDays(start, end);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");


        list = new List();
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        start = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        end = LocalDateTime.of(2012, 03, 10, 14, 33, 48, 640000);
        filteredList = list.filterDays(start, end);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");


        list = new List();
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        start = null;
        end = LocalDateTime.of(2012, 03, 10, 14, 33, 48, 640000);
        filteredList = list.filterDays(start, end);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");


        list = new List();
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        start = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        end = null;
        filteredList = list.filterDays(start, end);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");


        list = new List();
        for(int i = 0; i < 10; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(i, "person0"+i, null), "message0"+i);
            list.add(insertMessage);
        }
        start = null;
        end = null;
        filteredList = list.filterDays(start, end);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");


        list = new List();
        start = LocalDateTime.of(2013, 03, 10, 14, 33, 48, 640000);
        end = LocalDateTime.of(2016, 03, 10, 14, 33, 48, 640000);
        filteredList = list.filterDays(start, end);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals(0, list.size(), "size test failed");
    }

    @Test
    @DisplayName("Test filterUser List 1 normale Werte")
    public void Test16(){
        List list = new List();
        Message insertMessage;
        User myUser = new User(100, "boss", null);
        for(int i = 0; i < 15; i++){
            if(i % 3 == 0){
                insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                        myUser, "message0"+i);
                list.add(insertMessage);
            }
            else{
                insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                        new User(i, "person0"+i, null), "message0"+i);
                list.add(insertMessage);
            }
        }
        List filteredList = list.filterUser(myUser);
        Assertions.assertEquals(5, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", filteredList.getByIndex(0).getContent(), "message at 0 failed");
        Assertions.assertEquals("message03", filteredList.getByIndex(1).getContent(), "message at 1 failed");
        Assertions.assertEquals("message06", filteredList.getByIndex(2).getContent(), "message at 2 failed");
        Assertions.assertEquals("message09", filteredList.getByIndex(3).getContent(), "message at 3 failed");
        Assertions.assertEquals("message012", filteredList.getByIndex(4).getContent(), "message at 4 failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message010", list.getByIndex(10).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message011", list.getByIndex(11).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message012", list.getByIndex(12).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message013", list.getByIndex(13).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message014", list.getByIndex(14).getContent(), "list nicht verändert failed");

        filteredList = list.filterUser(new User(500, "kerl", null));
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message010", list.getByIndex(10).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message011", list.getByIndex(11).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message012", list.getByIndex(12).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message013", list.getByIndex(13).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message014", list.getByIndex(14).getContent(), "list nicht verändert failed");
    }

    @Test
    @DisplayName("Test filterUser List 2 edgecses")
    public void Test17(){
        List list = new List();
        Message insertMessage;
        User myUser = new User(100, "boss", null);
        for(int i = 0; i < 15; i++){
            if(i % 3 == 0){
                insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                        myUser, "message0"+i);
                list.add(insertMessage);
            }
            else{
                insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                        new User(i, "person0"+i, null), "message0"+i);
                list.add(insertMessage);
            }
        }
        List filteredList = list.filterUser(null);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals("message00", list.getByIndex(0).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message01", list.getByIndex(1).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message02", list.getByIndex(2).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message03", list.getByIndex(3).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message04", list.getByIndex(4).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message05", list.getByIndex(5).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message06", list.getByIndex(6).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message07", list.getByIndex(7).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message08", list.getByIndex(8).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message09", list.getByIndex(9).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message010", list.getByIndex(10).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message011", list.getByIndex(11).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message012", list.getByIndex(12).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message013", list.getByIndex(13).getContent(), "list nicht verändert failed");
        Assertions.assertEquals("message014", list.getByIndex(14).getContent(), "list nicht verändert failed");


        list = new List();
        myUser = new User(100, "boss", null);
        filteredList = list.filterUser(myUser);
        Assertions.assertEquals(0, filteredList.size(), "size test failed");
        Assertions.assertEquals(0, list.size(), "size test failed");
    }

    @Test
    @DisplayName("Test toString List")
    public void Test18(){
        List list = new List();
        Assertions.assertEquals("", list.toString(), "leere liste failed");


        Message insertMessage;
        String expected = "";
        for(int i = 0; i < 5; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(0,"user0"+i, null), "message0"+i);
            list.add(insertMessage);
            expected = expected+insertMessage.toString()+"\n";
        }
        Assertions.assertEquals(expected, list.toString(), "tostring output failed");

        list = new List();
        expected = "";
        for(int i = 0; i < 100; i++){
            insertMessage = new Message(i, LocalDateTime.of(2010+i, 03, 10, 14, 33, 48, 640000),
                    new User(0,"user0"+i, null), "message0"+i);
            list.add(insertMessage);
            expected = expected+insertMessage.toString()+"\n";
        }
        Assertions.assertEquals(expected, list.toString(), "tostring output failed");

    }


    // TESTS FÜR USERARRAY
    // size() wird nicht extra getestet, sondern in allen Tests hier mitgetestet

    @Test
    @DisplayName("Test Konstruktor UserArray")
    public void Test19(){
        UserArray array = new UserArray(10);
        Assertions.assertEquals(10, array.getLength());
        for(int i = 0; i < 10; i++){
            Assertions.assertEquals(null, array.getUsers()[i]);
        }
        Assertions.assertEquals(0, array.size());

        array = new UserArray(2);
        Assertions.assertEquals(2, array.getLength());
        Assertions.assertEquals(0, array.size());

        array = new UserArray(1);
        Assertions.assertEquals(1, array.getLength());
        Assertions.assertEquals(0, array.size());

        array = new UserArray(0);
        Assertions.assertEquals(1, array.getLength());
        Assertions.assertEquals(0, array.size());

        array = new UserArray(-1);
        Assertions.assertEquals(1, array.getLength());
        Assertions.assertEquals(0, array.size());
    }

    // addMember und deleteMember bereits in PinguTalk Tests dafür getestet!
}
