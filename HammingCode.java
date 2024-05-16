import java.util.Scanner;

public class HammingCode {
    
    // Function to calculate the Hamming code for given data
    public static String encodeHamming(String data) {
        int[] dataBits = new int[data.length()];
        for (int i = 0; i < data.length(); i++) {
            dataBits[i] = data.charAt(i) - '0';
        }

        // Determine the number of parity bits needed
        int m = dataBits.length;
        int r = 1;
        while (Math.pow(2, r) < m + r + 1) {
            r++;
        }

        // Initialize an array to store the hamming code
        int[] hammingCode = new int[m + r + 1];
        
        // Fill the data bits into the appropriate positions
        int j = 0;
        for (int i = 1; i < hammingCode.length; i++) {
            if ((i & (i - 1)) != 0) {
                hammingCode[i] = dataBits[j++];
            }
        }

        // Calculate the parity bits
        for (int i = 0; i < r; i++) {
            int pos = (int) Math.pow(2, i);
            for (int k = 1; k < hammingCode.length; k++) {
                if (((k >> i) & 1) == 1) {
                    if (k != pos) {
                        hammingCode[pos] ^= hammingCode[k];
                    }
                }
            }
        }

        // Convert the hamming code array to string
        StringBuilder encodedData = new StringBuilder();
        for (int i = 1; i < hammingCode.length; i++) {
            encodedData.append(hammingCode[i]);
        }

        return encodedData.toString();
    }

    // Function to decode and correct the hamming code
    public static String decodeHamming(String hammingCode) {
        int n = hammingCode.length();
        int[] hammingBits = new int[n + 1];
        for (int i = 0; i < n; i++) {
            hammingBits[i + 1] = hammingCode.charAt(i) - '0';
        }

        // Determine the number of parity bits
        int r = 0;
        while (Math.pow(2, r) < n + 1) {
            r++;
        }

        // Check for errors and find the error position
        int errorPos = 0;
        for (int i = 0; i < r; i++) {
            int pos = (int) Math.pow(2, i);
            int value = 0;
            for (int k = 1; k < hammingBits.length; k++) {
                if (((k >> i) & 1) == 1) {
                    value ^= hammingBits[k];
                }
            }
            if (value != 0) {
                errorPos += pos;
            }
        }

        // Correct the error if found
        if (errorPos != 0) {
            System.out.println("Error detected at position: " + errorPos);
            hammingBits[errorPos] ^= 1;
        } else {
            System.out.println("No error detected.");
        }

        // Extract the original data bits
        StringBuilder originalData = new StringBuilder();
        for (int i = 1; i < hammingBits.length; i++) {
            if ((i & (i - 1)) != 0) {
                originalData.append(hammingBits[i]);
            }
        }

        return originalData.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a binary string of 7 or 8 bits: ");
        String data = scanner.nextLine();


        // Encode the data
        String encodedData = encodeHamming(data);
        System.out.println("Encoded Hamming code: " + encodedData);

        // Introduce an error manually for testing
        System.out.println("\nIntroduce an error in the encoded data.");
        System.out.println("For example, flip a bit from 0 to 1 or 1 to 0.");
        System.out.print("Enter corrupted binary string: ");
        String corruptedData = scanner.nextLine();

        // Decode and correct the data
        String correctedData = decodeHamming(corruptedData);
        System.out.println("Corrected data: " + correctedData);

        scanner.close();
    }
}
