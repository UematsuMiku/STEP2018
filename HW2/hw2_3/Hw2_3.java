import java.util.Hashtable;

public class Hw2_3 {
    //入力は 「キャッシュするページ数(int)  閲覧するページ数(String)(abgdr...など)」
    public static void main(String[] args) {
        Hashtable<Integer,String> priority         //<優先順位，URL>のHashtable
                = new Hashtable<Integer,String>();
        Hashtable<String, String> pages            //<URL,ページのデータ>のHashtable
                = new Hashtable<String, String>();

        int table_size = Integer.parseInt(args[0]);  //キャッシュするページ数
        String input = args[1].toLowerCase();
        char[] ch2 = input.toCharArray();

        int a = (int) ('a');
        String str = null;
        char ch = 'a';
        int chnum_url = 0;

        long begin = System.currentTimeMillis();
        for (int i = 0; i < table_size; i++) {  //ハッシュテーブルの初期値を設定，ここはO(N)
            chnum_url = i + 'a';
            ch = (char) (chnum_url);
            str = String.valueOf(ch);
            priority.put(i,str + ".com");
            pages.put(str + ".com", str.toUpperCase());
        }
        long end = System.currentTimeMillis();
        System.out.printf("time: %.6f sec\n", (end - begin) / 1000.0);

        //System.out.println(priority);
        //System.out.println(pages);


        ////////////////////////////////////////////
        //ここからキャッシュの値を更新する作業///////////
        ////////////////////////////////////////////
        int i = 0;   //iはそれまでにページを閲覧した回数,
        // ハッシュ表priority中でiの値をキーに持つものを順に追い出していく
        int count = table_size;

        long begin2 = System.currentTimeMillis();
        while(i < ch2.length) {
            if (pages.get(ch2[i] + ".com") == null) {   //pagesの中にない（入れ替え）
                pages.remove(priority.get(i));
                pages.put(ch2[i] + ".com", String.valueOf(ch2[i]).toUpperCase());
            }

            priority.remove(i);                         //新しく閲覧したページの優先順位を設定
            priority.put(i + table_size, ch2[i] + ".com");

            i++;
        }

        long end2 = System.currentTimeMillis();
        System.out.printf("time: %.6f sec\n", (end2 - begin2) / 1000.0);
        System.out.println(priority.get(10009));
    }

}