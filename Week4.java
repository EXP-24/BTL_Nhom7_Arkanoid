public class Week4 {
    public static int max2Int(int a, int b) {
        // Tìm giá trị lớn nhất của hai số nguyên
        return (a > b) ? a : b;
    }

    public static int minArray(int[] array) {
        // Tìm giá trị nhỏ nhất của một mảng số nguyên
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Mảng không được rỗng");
        }
        int min = array[0];
        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    public static String calculateBMI(double weight, double height){
        // weight: kg, height: m
        if (height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Cân nặng và chiều cao phải lớn hơn 0");
        }
        double bmi = weight / (height * height);
        if (bmi < 18.5) {
            return "Thiếu cân";
        } else if (bmi < 23) {
            return "Bình thường";
        } else if (bmi < 25) {
            return "Thừa cân";
        } else {
            return "Béo phì";
        }
    }
}
