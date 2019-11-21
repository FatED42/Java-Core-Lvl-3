import java.util.Arrays;

public class ConcatenateFrom4 {
    public int[] ConcatenateFrom4(int[] arr) {
        int[] arrNew = null;
        boolean found = false;
        for (int i = arr.length - 1; i > 0 ; i--) {
            if (arr[i] == 4) {
                arrNew = new int[arr.length - i - 1];
                System.arraycopy(arr, i + 1, arrNew, 0, arr.length - i - 1);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Array need to have 4");
        }
        return arrNew;

    }

    public static void main(String[] args) {
        int[] arr = {1,2,4,4,2,3,4,1,7};
        ConcatenateFrom4 method1 = new ConcatenateFrom4();
        System.out.println(Arrays.toString(method1.ConcatenateFrom4(arr)));
    }
}
