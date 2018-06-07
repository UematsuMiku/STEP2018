import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Write_dic {
    public static void main(String args[]) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        //String[] arr = new String[100000];
        //String [] arr2 = new String[100000];
        //String[] arr3 = new String[100000];
        list = dic_to_list("/Users/uematsumiku/Documents/workspace/STEP_dev/tool/dic");
        //arr2 = Sort_str.sort_arr_cont(arr);
        list2 = Sort_str.sort_list_cont(list);
        Collections.sort(list2);
        list_to_dic(list2);
       // Arrays.sort(arr2);
        //arr_to_dic(arr2);


    }
    public static ArrayList<String> dic_to_list(String filepath) {
        try {
            File file = new File(filepath);
            //ファイルが存在するか確認する
            if (file.exists()) {
                ArrayList<String> list = new ArrayList<String>();
                //int num = 0;
                //FileReaderクラスのオブジェクトを生成する
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                //ファイルを1行ずつ読み込む
                String data;
                while ((data = bufferedReader.readLine()) != null) {

                        list.add(data.toLowerCase()); //全て小文字に変換

                }

                //ファイルクローズ
                fileReader.close();
                System.out.println("read success");
                return list;

            } else {
                System.out.print("ファイルは存在しません");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static void list_to_dic(ArrayList<String> list) {
        try {
            File file = new File("/Users/uematsumiku/Documents/workspace/STEP_dev/tool/dic_sorted");
            //ファイルが存在するか確認する
            if (file.exists()) {
                FileWriter filewriter = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(filewriter);
                int num = 0;
                while (num < list.size()) {
                    bw.write(list.get(num));
                    num++;
                    bw.newLine();
                }
                bw.close();
                System.out.println("write success");
            } else {
            System.out.print("ファイルは存在しません");

        }
    } catch (IOException e) {
        e.printStackTrace();

    }
        }
    }




