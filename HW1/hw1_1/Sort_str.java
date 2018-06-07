import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class Sort_str {
    public static void main(String args[]){
        /*String in = args[0];
        String out = sort_str1(in);
        System.out.println(out);*/
        /*String[] a = {"dog","cat","bird","fish"};
        String[] b = new String[4];
        String[] c = new String[4];
        b = sort_arr_cont(a);*/
        ArrayList list = new ArrayList<String>();
        ArrayList list2 = new ArrayList<String>();
        list.add("bird");
        list.add("fish");
        list.add("cat");
        list.add("dog");
        list2 = sort_list_cont(list);


        for(int i = 0;i<list2.size();i++) {

            System.out.println(list2.get(i));

        }
        System.out.println();

        //Arrays.sort(b);
        Collections.sort(list2);
        for(int i = 0;i<list2.size();i++) {
            System.out.println(list2.get(i));
        }
        System.out.println();

    }
    public static String sort_str1(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String newstr = new String(chars);
        return newstr;
    }
    public static String[] sort_arr_cont(String[] str) {
        int num = 0;
        String[] str_new = new String[str.length];
        while(num < 4) {
        str_new[num] = sort_str1(str[num]);
        num++;
        }
        return  str_new;
    }
    public static ArrayList<String> sort_list_cont(ArrayList<String> list) {
        int num = 0;
        ArrayList<String> list_new = new ArrayList<String>();
        while(num < list.size()) {
            list_new.add(Sort_str.sort_str1(list.get(num)));
            num++;
        }
        return  list_new;
    }


}
