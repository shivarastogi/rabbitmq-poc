import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

public class TestCases {

    @Test
    public void first()
    {
        String[] arr = {"a", "b", "c"};

        StringJoiner word = new StringJoiner("-");
        for (int i = 0; i < arr.length; i++) {
            word.add(arr[i]);
        }
        System.out.println(word.toString());
    }

    public static void main(String[] arr){
        int startIndex = 1;
        //StringBuilder word = new StringBuilder(arr[startIndex]);
        StringJoiner word = new StringJoiner("-");
        for (int i = startIndex; i < arr.length; i++) {
            word.add(arr[i]);
            //word.append("-").append(arr[i]);
        }
        System.out.println(word.toString());
    }
}
