public class ArrayListTester {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        ArrayList<String> a1 = new ArrayList<>();
        ArrayList<String> a2 = new ArrayList<>();


        a.addToBack("0");
        a.addToBack("1");
        a.addToBack("2");
        a.addToBack("3");
        a.addToBack("4");
        a.addToBack("5");
        a.addToBack("6");
        a.addToBack("7");
        a.addToBack("8");
        a.addAtIndex(9, "9");
        a.addToBack("10");
        a.addToBack("11");
        a.addToBack("12");
        a.addAtIndex(12, "13");





        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i));
        }
        System.out.println("__________");


        a1.addToFront("3");
        System.out.println(a1.get(0));



    }
}
