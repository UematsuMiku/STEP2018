import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class Make_dic_map {
    //HashMapを作る
    static Map<String, String> map = new HashMap<String, String>();
    public static void main(String args[]) {
        ArrayList list = new ArrayList<String>();
        ArrayList list2 = new ArrayList<String>();
        list = Write_dic.dic_to_list("/Users/uematsumiku/Documents/workspace/STEP_dev/tool/dic");
        //list2 = sort_list_cont(list);
        //Collections.sort(list2);
        //Write_dic.list_to_dic(list2);
        //System.out.println(map.get("aaaaabbcdrr"));  //Mapからの取り出しは定数オーダー


    }
    public static ArrayList<String> sort_list_cont(ArrayList<String> list) {
        int num = 0;
        ArrayList<String> list_new = new ArrayList<String>();
        while(num < list.size()) {
            map.put(Sort_str.sort_str1(list.get(num)),list.get(num));
            list_new.add(Sort_str.sort_str1(list.get(num)));
            num++;
        }
        return  list_new;
    }
}
