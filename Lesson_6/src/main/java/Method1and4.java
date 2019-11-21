public class Method1and4 {
    public boolean Method1And4(int[] arr) {
        boolean found1 = false;
        boolean found4 = false;
        boolean result = true;
        for (int i = 0; i < arr.length ; i++) {
            if (arr[i] == 1) {
                found1 = true;
            }
            if (arr[i] == 4) {
                found4 = true;
            }
        }

        if (!found1 || !found4) {
            result = false;
        }

        return result;
    }
}
