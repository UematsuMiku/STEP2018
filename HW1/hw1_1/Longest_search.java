import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


//メインとなるクラス.高いスコアの単語を探す
public class Longest_search {

    static Map<String, String> map = new HashMap<String, String>(); //元の辞書と新しい辞書をつなぐmap
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //探索のための辞書を作成
        ArrayList list = new ArrayList<String>();  //元の辞書
        ArrayList list2 = new ArrayList<String>(); //単語内の文字は、必ず昇順
        list = Write_dic.dic_to_list("/Users/uematsumiku/Documents/workspace/STEP_dev/tool/dic");
        list2 = Make_dic_map.sort_list_cont(list); //新しい辞書
        Collections.sort(list2); //書き換えた辞書が完成
        //

        //スコアの高い組み合わせから調べる
        char[] chars = args[0].toCharArray();        //入力された文字列をchar型に変換
        int leng = args[0].length();                 //入力された文字列の長さ
        int binary_leng = (int) Math.pow(2, leng);   //入力された文字からできるパターン数,3文字なら2^3=8
        String[] bin = new String[binary_leng];      //パターンを２進数で表した配列
        String[] kouho = new String[binary_leng];    //パターンをアルファベットで表した配列
        bin = My_score.my_binary_arr(args[0]);
        kouho = My_score.my_kouho_str(bin, chars);
        //kouhoに全ての並び方のパターンを格納

        /*/並び方のパターンとスコアの２次元配列
          kouho_score[][0] = 並び方のパターン
          kouho_score[][1] = スコア*/
        String[][] kouho_score = new String[binary_leng][2];

        kouho_score = My_score.my_pattern_score(args[0]); //スコアの順に入力からできるパターンを並べる ここ（ソート）が遅い
        long m = System.currentTimeMillis();
        System.out.println((m - start) + "ms sorted");

        for (int i = 0; i < binary_leng; i++) {
            //System.out.println(kouho_score[i][0] + " " + kouho_score[i][1]);
            kouho_score[i][0] = Sort_str.sort_str1(kouho_score[i][0]); //検索用に、１単語内でソート dog>dgo
            //System.out.println(kouho_score[i][0] + " " + kouho_score[i][1]);
            //System.out.println(Make_dic_map.map.get(kouho_score[i][0]));
        }


        int count = 0;
        int count2 = 0;
        String key;
        String keep = null;
        int score;
        for (int i = binary_leng - 1; count < 15 && i >= 0 && count2 < 10; i--) {
            key = kouho_score[i][0];  //検索用の単語
            int id = Collections.binarySearch(list2, key);  //文字列が作り変えた辞書内にあるか探索
            if (id > 0) {
                String target = Make_dic_map.map.get(kouho_score[i][0]);                //見つけたら、元の辞書内の単語を出力
                //System.out.println(target + " " + kouho_score[i][1]);
                score = Integer.parseInt(kouho_score[i][1]);
                if (score > 80) {
                    keep = kouho_score[i][0];
                    if (score > 99) {
                        count++;
                        System.out.println(target + " " + kouho_score[i][1]);

                    }
                }

            }

        }
        System.out.println("keep " + keep);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }

}
