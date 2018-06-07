import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//入力された文字全てから作られる単語を探す
public class My_search {
    static Map<String, String> map = new HashMap<String, String>();
    public static void main(String[] args) {
        ArrayList list = new ArrayList<String>();
        ArrayList list2 = new ArrayList<String>(); //単語内の文字は、必ず昇順
        list = Write_dic.dic_to_list("/Users/uematsumiku/Documents/workspace/STEP_dev/tool/dic");
        list2 = Make_dic_map.sort_list_cont(list);
        Collections.sort(list2); //書き換えた辞書が完成
        //System.out.println(list2);
        String sorted_input = Sort_str.sort_str1(args[0]);      //入力の文字列をソート
        int id = Collections.binarySearch(list2,sorted_input);  //文字列が辞書内にあるか探索
        String target = map.get(list2.get(id));                 //見つけたら、元の辞書内の単語を出力
        System.out.println(target);
    }

}

//テスト用
 /*list.add("bird");
        list.add("fish");
        list.add("cat");
        list.add("dog");*/