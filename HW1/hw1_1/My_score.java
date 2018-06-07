import java.util.Arrays;


//スコアを計算する関数など
public class My_score {
    public static void main(String args[]) {
        char[] chars = args[0].toCharArray();
        int leng = args[0].length();
        int binary_leng = (int) Math.pow(2, leng);
        //System.out.println(binary_leng);
        String[] bin = new String[binary_leng];
        String[] kouho = new String[binary_leng];
        bin = my_binary_arr(args[0]);
        /*for (int i = 0; i < binary_leng; i++) {
            System.out.println(bin[i]);
            //System.out.println(i);
        }*/

        //アルファベットのパターンの候補を出力
        /*kouho = my_kouho_str(bin,chars);
        for(int i = 0;i<binary_leng;i++){
            System.out.println(kouho[i]);
        }*/

        //System.out.println(get_score(args[0]));  //スコア計算のテスト(quiz)

        String[][] kouho_score = new String[binary_leng][2];
        kouho_score = my_pattern_score(args[0]);
        //文字のパターンと点数の２次元配列の出力のテスト→成功！
        for(int i = 0;i<binary_leng;i++) {
            for(int j = 0;j<2;j++) {
                 System.out.print(kouho_score[i][j]+" ");
            }
            System.out.println();
        }


    }

    //[アルファベットのパターン][点数]の配列を作る→大きい順に並べ替える
    public static String[][] my_pattern_score(String str){
        char[] chars = str.toCharArray();
        int str_leng =str.length();
        int binary_leng = (int) Math.pow(2, str_leng);
        String[] bin = new String[binary_leng];
        String[] pattern = new String [binary_leng];
        String[][] ret = new String[binary_leng*2][2]; //返り値
        int score = 0;

        bin = my_binary_arr(str);
        pattern = my_kouho_str(bin,chars);
        for(int i = 0;i<binary_leng;i++) { //binary_leng...文字のパターン数 ３文字なら８パターン
            ret[i][0] = pattern[i];
            ret[i][1] = String.valueOf(get_score(pattern[i]));
        }

        //qsort(ret,0,binary_leng-1);
        String[] ret1 = new String[2];
        String[] ret2 = new String[2];
        String[] keep = new String[2];
        int count3 = 0;


        //ここから並べ替え
        //int para = (int)(binary_leng*0.8);
        for(int i = binary_leng-1;i > binary_leng;i--) { //i<binary_leng-1
            for(int j = binary_leng-2;j>binary_leng;j--) { //j<binary_leng

                ret1[0] = ret[i][0];
                ret1[1] = ret[i][1];
                ret2[0] = ret[j][0];
                ret2[1] = ret[j][1];


                int judge = my_compareTo(ret1,ret2);
                if(judge == 1) {
                } else {

                    //ret2の方が大きいため上下を入れ替え
                    keep = ret[i];
                    ret[i] = ret[j];
                    ret[j] = keep;

                }


            }
        }


        return ret;  //並べ替え終わった２次元配列

    }

    //Comparatorを作る str1 >= strs2のとき1,strs1<strs2のとき0
    public static int my_compareTo(String[] strs1,String[] strs2) { //ここを実装する
        int int1 = Integer.parseInt(strs1[1]);
        int int2 = Integer.parseInt(strs2[1]);
        if(int1 >= int2) {
            return 1;
        } else {
            return 0;
        }
    }


    //文字の採用不採用を表す２進数の配列（桁数は文字数に等しい）
    public static String[] my_binary_arr(String str) {
        char[] chars = str.toCharArray();
        /*/Quが含まれるかどうか判定
        boolean ex_Q = false;
        for(int i = 0;i<chars.length;i++) {
            if(chars[i] == 'q') {
                ex_Q = true;
                break;
            }
        }*/


        int in_size = chars.length; //入力されたアルファベットの文字数
        int binary_size = (int) Math.pow(2, in_size);
        String[] my_bin = new String[binary_size];
        for (int i = 0; i < binary_size; i++) {
            my_bin[i] = Integer.toBinaryString(i);
            if (my_bin[i].length() < in_size) {
                int zero_num = in_size - my_bin[i].length();
                for (int j = 0; j < zero_num; j++) { //ゼロ埋め
                    my_bin[i] = "0" + my_bin[i];
                }
            }
            //System.out.println(2^in_size);

        }
        return my_bin;
    }
    //文字の採用不採用を表す２進数から、文字列を作る（返り値の大きさは2^入力）
    public static String[] my_kouho_str(String[] bin_arr,char[] chars ) {
        int in_leng = bin_arr[0].length();
        char[] bin_char = new char[in_leng];
        String[] kouho_str = new String[bin_arr.length];  //２進数のパターン数と同じ
        for(int i = 0;i<bin_arr.length;i++) { //２進数のパターン数だけ繰り返す
            bin_char = bin_arr[i].toCharArray();
            for(int j=in_leng-1;j >= 0;j--) {   //入力の文字数だけ繰り返す
                if(bin_char[j] == '1' && kouho_str[i] != null) {
                    kouho_str[i] = chars[j] + kouho_str[i]; //今できているアルファベットの前に新しいアルファベットをつける
                } else if (bin_char[j]=='1'){
                    kouho_str[i] = String.valueOf(chars[j]);
                } else if (kouho_str[i] == null){
                    kouho_str[i] = "";
                }
            }
        }
        return kouho_str;
    }
    //入力されたアルファベットの文字列からスコアを計算
    public static int get_score(String str) {
        char[] point_3 = {'j','k','q','x','z'};
        char[] point_2 = {'c','f','h','l','m','p','v','w','v'};
        char[] chars = new char[str.length()];
        chars = str.toCharArray();
        int score = 0;
        char c;
        //System.out.println(Arrays.binarySearch(point_3,'q'));
        for(int i = 0;i<chars.length;i++) {
            if (Arrays.binarySearch(point_3,chars[i]) > 0 ) {
                score = score + 3;
                //System.out.println(point_3[2]);
                if (chars[i] == 'q') {
                    i++;
                }
            } else if ( Arrays.binarySearch(point_2,chars[i]) > 0){
                score = score + 2;
            } else {
                score++;
            }
        }
        score = score + 1;
        score = (int)Math.pow(score,2);
        return score;
    }
    public static void qsort(String[][] a,int left,int right) {
        if (left>=right) {
            return;
        }
        int p = Integer.parseInt(a[(left+right)/2][1]);
        int l = left;
        int r = right;
        String tmp;
        while(l<=r){
          while(Integer.parseInt(a[l][1]) < p){
            l++;
          }
          while(Integer.parseInt(a[l][1]) > p) {
              r--;
          }
            if (l<=r) {
                tmp = a[l][1];
                a[l][1] = a[r][1];
                a[r][1] = tmp;
                l++;
                r--;
            }
        }
        qsort(a,left,r);
        qsort(a,l,right);

    }
}
