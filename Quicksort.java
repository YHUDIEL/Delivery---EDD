public class Quicksort {
    public void quicksort(int[] nums, int inicio, int fin) {
        if (inicio >= fin) {
            return;
        }
        int pivote = nums[(inicio + fin) / 2];
        int i = inicio;
        int d = fin;

        do {
            while (nums[i] < pivote) {
                i++;
            }
            while (nums[d] > pivote) {
                d--;
            }
            if (i <= d) {
                int temp = nums[i];
                nums[i] = nums[d];
                nums[d] = temp;
                i++;
                d--;
            }
        } while (i <= d);

        if (inicio < d) quicksort(nums, inicio, d);
        if (fin > i) quicksort(nums, i, fin);
    }
}
