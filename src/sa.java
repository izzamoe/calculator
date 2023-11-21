public class sa {
    public static void main(String[] args) {
        // Contoh penggunaan aplikasi
        Calculate calculator = new Calculate();
        String inputExpression = "10a-11";
        String result = calculator.Evaluation(inputExpression, true);
        System.out.println("Hasil evaluasi: " + result);
    }
}
