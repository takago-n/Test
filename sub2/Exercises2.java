package com.alphacmc.alphatraining.sub2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.alphacmc.alphatraining.util.DateUtils;

public class Exercises2 {

    private static final String IN_FILE = "C:\\\\Users\\\\takago-n\\\\Documents\\\\test.csv"; //読み込み元のCSVファイル
    private static final String OUT_FILE1 = "C:\\\\Users\\\\takago-n\\\\Documents\\\\test2.csv"; //正社員を書き出すCSVファイル
    private static final String OUT_FILE2 = "C:\\\\Users\\\\takago-n\\\\Documents\\\\test3.txt"; //契約社員を書き出すTXTファイル

    public static void main(String[] args) {
        Exercises2 ex = new Exercises2();
        ex.execMain();
    }

    public void execMain() {
        // ファイル定義
        BufferedReader bReader = null;
        PrintWriter pWriter1 = null;
        PrintWriter pWriter2 = null; //初期値の設定

        try {
            // ファイルオープン
            bReader = new BufferedReader(new InputStreamReader(new FileInputStream(IN_FILE), "MS932"));
            pWriter1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUT_FILE1),"UTF-8")));
            pWriter2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUT_FILE2), "MS932")));
            // 入力エリア
            String line = bReader.readLine();
            while ((line = bReader.readLine()) != null) { //テキストを1行単位で読み、nullを返す(=読み終わる)まで
                // 項目分割
                String[] item = line.split(","); //カンマごとに項目を区切り配列に入れる
                for (int i = 0; i < item.length; i++) {
                    item[i] = item[i].trim();
                }
                // 社員コード
                String sCode = item[0].trim();
                // 氏名
                String name = item[1].trim();
                // 生年月日
                String[] birthday = item[2].trim().split("/");
                // 元号表記
                String eraYmd = DateUtils.convertToEraDateString(
                        DateUtils.getDateType(Integer.parseInt(birthday[0]),
                                Integer.parseInt(birthday[1]),
                                Integer.parseInt(birthday[2])));
                // 電話番号
                String telNo = item[3].trim().replaceAll("-", "");
                // 郵便番号
                String postalNo = item[4].trim();
                if (item[4].indexOf("-") < 0) {
                    postalNo = postalNo.substring(0,3) + "-" + postalNo.substring(3,7);
                }
                // 住所
                String address = item[5].trim();



                // 正社員、契約社員別にファイル出力
                if ("N".equals(sCode.substring(0, 1))) {
                    // 正社員レコード出力
                    pWriter1.print(sCode);
                    pWriter1.print(",");
                    pWriter1.print(name);
                    pWriter1.print(",");
                    pWriter1.print(eraYmd);
                    pWriter1.print(",");
                    pWriter1.print(telNo);
                    pWriter1.print(",");
                    pWriter1.print(postalNo);
                    pWriter1.print(",");
                    pWriter1.println(address);
                } else {
                    // 契約社員レコード出力
                    pWriter2.print(sCode);
                    String s1 = String.format("%-11s", name);
                    pWriter2.print(s1.replaceAll(" ", "　")); //全角スペースに変換して出力
                    pWriter2.print(eraYmd);
                    pWriter2.format("%-14s",telNo);
                    pWriter2.print(postalNo);
                    String s2 = String.format("%-40s",address);
                    pWriter2.print(s2.replaceAll(" ", "　"));
                    pWriter2.println("");
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bReader.close();
                pWriter1.close();
                pWriter2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
       }

    }

}